package com.example.floralboutique.ui.member.flowerdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.floralboutique.R;
import com.example.floralboutique.util.AppComponents;
import com.example.floralboutique.util.AppExecutors;
import com.example.floralboutique.util.AppFormatter;
import com.example.floralboutique.util.BitmapUtil;
import com.example.floralboutique.util.QuantityWatcher;
import com.example.floralboutique.util.ViewBuilder;

import java.util.List;

public class MemberFlowerDetailActivity extends AppCompatActivity {
    public static final String FLOWER_NAME_EXTRA = "FLOWER_NAME";
    private final AppExecutors executors_ = AppExecutors.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_flower_detail);

        String flower = getIntent().getStringExtra(FLOWER_NAME_EXTRA);

        // initialize the view model
        MemberFlowerDetailViewModelFactory factory = AppComponents.getShopFlowerDetailViewModelFactory(getApplicationContext(), flower);
        MemberFlowerDetailViewModel model = ViewModelProviders.of(this, factory).get(MemberFlowerDetailViewModel.class);

        // setup toolbar
        ViewBuilder.buildToolbar(this, flower);

        // get the views
        TextView textFlower = findViewById(R.id.text_flower);
        TextView textPrice = findViewById(R.id.text_price);
        TextView textDiscountPrice = findViewById(R.id.text_discount_price);
        TextView textDescription = findViewById(R.id.text_description);
        EditText editQuantity = findViewById(R.id.edit_quantity);
        ImageView imageFlower = findViewById(R.id.image_flower);
        ImageView imagePlus = findViewById(R.id.image_plus);
        ImageView imageMinus = findViewById(R.id.image_minus);

        imagePlus.setOnClickListener(v -> {
            int quantity = 0;
            try {
                quantity = Integer.parseInt(editQuantity.getText().toString()) + 1;
            } catch (Exception ex) {
                // do nothing
            }
            model.updateQuantity(quantity);
        });

        imageMinus.setOnClickListener(v -> {
            int quantity = 0;
            try {
                quantity = Integer.parseInt(editQuantity.getText().toString());
            } catch (Exception ex) {
                // do nothing
            }

            if (quantity > 0) {
                --quantity;
            }

            model.updateQuantity(quantity);
        });

        editQuantity.setOnKeyListener((view, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    model.updateQuantity(Integer.parseInt(editQuantity.getText().toString()));
                    return true;
                }
            }
            return false;
        });

        editQuantity.addTextChangedListener(new QuantityWatcher());

        // observe
        model.getFlowerDetails().observe(this,
                details -> onFlowerChanged(textFlower, textDescription, textPrice, textDiscountPrice,
                        editQuantity, imageFlower, details));
    }

    public void onFlowerChanged(TextView textFlower, TextView textDescription,
                                TextView textPrice, TextView textDiscountPrice,
                                EditText editQuantity, ImageView imageFlower,
                                List<MemberFlowerDetailItemModel> details) {
        if (details == null || details.size() == 0) {
            textFlower.setText("");
            editQuantity.setText("0");
            textDescription.setText("0");
            imageFlower.setImageBitmap(null);
            return;
        }

        MemberFlowerDetailItemModel theFlower = null;
        if (details.size() > 1) {
            for (MemberFlowerDetailItemModel detail : details) {
                if (theFlower != null) {
                    detail = new MemberFlowerDetailItemModel(detail.name,
                            detail.description,
                            detail.image,
                            detail.price,
                            detail.discountPercent * theFlower.discountPercent,
                            detail.quantity);
                }
                theFlower = detail;
            }
        } else {
            theFlower = details.get(0);
        }

        final MemberFlowerDetailItemModel flower = theFlower;        // to shut the compiler up
        textFlower.setText(flower.name);
        textDescription.setText(flower.description);
        textPrice.setText(AppFormatter.formatPrice(flower.price));

        if (flower.discountPercent != 1) {
            textDiscountPrice.setText(AppFormatter.formatDiscountPrice(flower.price, flower.discountPercent));
            textPrice.setPaintFlags(textPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            textDiscountPrice.setVisibility(View.VISIBLE);
        } else {
            textDiscountPrice.setVisibility(View.GONE);
            textPrice.setPaintFlags(textPrice.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        editQuantity.setText(AppFormatter.formatQuantity(flower.quantity));
        BitmapUtil.load(imageFlower, flower.image);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
