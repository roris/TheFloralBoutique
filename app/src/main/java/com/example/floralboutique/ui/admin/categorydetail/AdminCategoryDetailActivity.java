package com.example.floralboutique.ui.admin.categorydetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.floralboutique.util.ViewBuilder;
import com.example.floralboutique.R;
import com.example.floralboutique.util.AppComponents;
import com.example.floralboutique.util.BitmapUtil;

public class AdminCategoryDetailActivity extends AppCompatActivity {
    public static final String CATEGORY_EXTRA = "Category.Name";
    public static final String IMAGE_EXTRA = "Category.Image";
    private static final int GALLERY_REQUEST = 1;
    private ImageView imageThumbnail_;
    private Bitmap bitmap_ = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category_detail);

        // get args
        final String category = getIntent().getStringExtra(CATEGORY_EXTRA);
        final String imagePath = getIntent().getStringExtra(IMAGE_EXTRA);

        // get view model
        AminCategoryDetailViewModelFactory factory = AppComponents.getAdminCategoryDetailViewModelFactory(getApplicationContext());
        AdminCategoryDetailViewModel model = ViewModelProviders.of(this, factory).get(AdminCategoryDetailViewModel.class);

        // setup toolbar
        final String title = category.isEmpty() ? "New Category" : "Manage " + category;
        ViewBuilder.buildToolbar(this, title);

        // setup widgets
        EditText editCategory = findViewById(R.id.edit_category);
        Button buttonSave = findViewById(R.id.button_save);
        imageThumbnail_ = findViewById(R.id.image_thumbnail);
        editCategory.setEnabled(category.isEmpty());

        if (!imagePath.isEmpty()) {
            bitmap_ = BitmapUtil.load(imagePath);
            imageThumbnail_.setImageBitmap(bitmap_);
            editCategory.setText(category);
        }

        // events
        imageThumbnail_.setOnClickListener(v -> onImageThumbnailClick());
        buttonSave.setOnClickListener(v -> onButtonSaveClick(model, editCategory));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
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

    private void onImageThumbnailClick() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    private void onButtonSaveClick(@NonNull AdminCategoryDetailViewModel model, @NonNull EditText editCategory) {
        boolean valid = true;
        String category = editCategory.getText().toString().trim();

        if (category.isEmpty()) {
            editCategory.setError("please enter a valid category");
            valid = false;
        }

        if (bitmap_ == null) {
            Toast.makeText(this, "select an image", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (valid) {
            String imageDest = getFilesDir() + category.toLowerCase().replace(' ', '_') + ".png";
            model.save(category, bitmap_, imageDest);
            finish();
        }
    }
}
