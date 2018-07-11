package com.example.floralboutique.ui.admin.flowerdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.floralboutique.util.AppComponents;
import com.example.floralboutique.util.AppExecutors;
import com.example.floralboutique.util.BitmapUtil;
import com.example.floralboutique.util.ViewBuilder;
import com.example.floralboutique.R;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class AdminFlowerDetailActivity extends AppCompatActivity {
    public static final String FLOWER_EXTRA = "Flower.Name";
    public static final String DESCRIPTION_EXTRA = "Flower.Description";
    public static final String PRICE_EXTRA = "Flower.Price";
    public static final String IMAGE_EXTRA = "Flower.Image";
    public static final int GALLERY_REQUEST = 1;
    private AppExecutors executors_ = AppExecutors.getInstance();
    private List<CheckedCategory> categories_;
    private ImageView imageThumbnail_;
    private Bitmap bitmap_ = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_flower_detail);

        // read extra params
        final String flower = getIntent().getStringExtra(FLOWER_EXTRA);
        final String image = getIntent().getStringExtra(IMAGE_EXTRA);
        final String description = getIntent().getStringExtra(DESCRIPTION_EXTRA);
        final float price = getIntent().getFloatExtra(PRICE_EXTRA, 0);

        // setup view model
        AdminFlowerDetailViewModelFactory factory = AppComponents.getManageFlowerDetailViewModelFactory(this, flower);
        AdminFlowerDetailViewModel model = ViewModelProviders.of(this, factory).get(AdminFlowerDetailViewModel.class);

        // setup recycler view
        AdminFlowerDetailAdapter adapter = new AdminFlowerDetailAdapter();
        RecyclerView recyclerView = ViewBuilder.buildRecyclerView(this, adapter);

        // setup toolbar
        final String title = flower.isEmpty() ? "Add Flower" : flower;
        ViewBuilder.buildToolbar(this, title);

        // setup other widgets
        EditText editName = findViewById(R.id.edit_name);
        EditText editDescription = findViewById(R.id.edit_description);
        EditText editPrice = findViewById(R.id.edit_price);
        Button buttonSave = findViewById(R.id.button_save);
        imageThumbnail_ = findViewById(R.id.image_thumbnail);
        editName.setEnabled(flower.isEmpty());
        editName.setText(flower);
        editDescription.setText(description);
        if (price != 0) {
            editPrice.setText(String.format("%f", price));
        }
        if (!(image == null || image.isEmpty())) {
            bitmap_ = BitmapUtil.load(image);
            imageThumbnail_.setImageBitmap(bitmap_);
        }

        TextView notice = findViewById(R.id.notice);

        // setup event handlers
        buttonSave.setOnClickListener(
                v -> onSavePressed(model, editName, editDescription, editPrice, categories_));

        imageThumbnail_.setOnClickListener(v -> onImageThumbnailClick());

        // load categories in a worker thread, without observing.
        executors_.diskIo.execute(() -> {
            List<CheckedCategory> categories = model.getCategories();
            // update UI in the main thread
            executors_.mainThread.execute(() -> {
                if (categories == null || categories.size() == 0) {
                    adapter.clearItems();
                    categories_ = categories;
                    notice.setVisibility(View.VISIBLE);
                } else {

                    TreeMap<String, CheckedCategory> map = new TreeMap<>();
                    for (CheckedCategory category : categories) {
                        CheckedCategory mapped = map.get(category.category);
                        if (mapped == null) {
                            mapped = category;
                        } else {
                            mapped = new CheckedCategory(mapped.category, mapped.checked || category.checked);
                        }
                        map.put(category.category, mapped);
                    }
                    categories_ = new ArrayList<>(map.values());
                    notice.setVisibility(View.GONE);
                    adapter.swapItems(categories_);
                }
            });
        });
    }

    public void onSavePressed(AdminFlowerDetailViewModel model,
                              EditText editName,
                              EditText editDescription,
                              EditText editPrice,
                              List<CheckedCategory> categories) {
        String name = editName.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String strPrice = editPrice.getText().toString().trim();
        float price = 0;
        boolean valid = true;

        if (name.isEmpty()) {
            editName.setError("enter a name");
            valid = false;
        }

        if (description.isEmpty()) {
            editDescription.setError("enter a description");
            valid = false;
        }

        if (strPrice.length() == 0) {
            editPrice.setError("enter a price");
            valid = false;
        } else {
            try {
                price = Float.parseFloat(strPrice);
            } catch (ParseException ex) {
                //
            }

            if (price == 0) {
                editPrice.setError("enter a valid price");
                valid = false;
            }
        }

        if (bitmap_ == null) {
            Toast.makeText(this, "Select an image", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (!valid) {
            return;
        }

        String imageDest = getFilesDir() + name.toLowerCase().replace(' ', '_') + ".png";
        model.save(name, description, price, imageDest, bitmap_, categories);
        finish();
    }

    public void onImageThumbnailClick() {
        // select an image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            String projection[] = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            String path = cursor.getString(columnIndex);
            cursor.close();

            // do a synchronous load
            bitmap_ = BitmapUtil.load(path);
            imageThumbnail_.setImageBitmap(bitmap_);
        }
    }
}
