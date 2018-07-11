package com.example.floralboutique.ui.admin.promotiondetail;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.floralboutique.R;
import com.example.floralboutique.util.AppComponents;
import com.example.floralboutique.util.AppFormatter;
import com.example.floralboutique.util.ViewBuilder;

import java.util.Calendar;
import java.util.Date;

public class AdminPromotionDetailActivity extends AppCompatActivity {
    public static final String PROMOTION_ID_EXTRA = "Promotion.Id";
    public static final String PROMOTION_START_DATE_EXTRA = "Promotion.StartDate";
    public static final String PROMOTION_END_DATE_EXTRA = "Promotion.EndDate";
    public static final String PROMOTION_DISCOUNT_PERCENT_EXTRA = "Promotion.DiscountPercent";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_promotion_detail);

        // get args
        final int id = getIntent().getIntExtra(PROMOTION_ID_EXTRA, 0);
        final float discountPercent = getIntent().getFloatExtra(PROMOTION_DISCOUNT_PERCENT_EXTRA, 1.f);
        final Date startDate = (Date) getIntent().getSerializableExtra(PROMOTION_START_DATE_EXTRA);
        final Date endDate = (Date) getIntent().getSerializableExtra(PROMOTION_END_DATE_EXTRA);

        // get view model
        AdminPromotionDetailViewModelFactory factory = AppComponents.getAdminPromotionDetailViewModelFactory(getApplicationContext(), id);
        AdminPromotionDetailViewModel model = ViewModelProviders.of(this, factory).get(AdminPromotionDetailViewModel.class);

        // setup toolbar
        final String title = id == 0 ? "New Promotion" : "Promotion " + AppFormatter.formatId(id);
        ViewBuilder.buildToolbar(this, title);

        // setup recycler view
        CheckedFlowerAdapter adapter = new CheckedFlowerAdapter();
        RecyclerView recyclerView = ViewBuilder.buildRecyclerView(this, adapter);

        // setup other UI
        EditText editStartDate = findViewById(R.id.edit_start_date);
        EditText editEndDate = findViewById(R.id.edit_end_date);
        EditText editDiscountPrice = findViewById(R.id.edit_discount_percent);
        Button buttonSave = findViewById(R.id.button_save);
        TextView notice = findViewById(R.id.notice);

        if (id != 0) {
            editStartDate.setText(AppFormatter.formatPromotionDate(startDate));
            editEndDate.setText(AppFormatter.formatPromotionDate(endDate));
            editDiscountPrice.setText(AppFormatter.formatPercentOff(discountPercent));
        }

        // events
        editStartDate.setOnClickListener(v -> displayDatePicker(editStartDate));
        editEndDate.setOnClickListener(v -> displayDatePicker(editEndDate));
        buttonSave.setOnClickListener(v -> model.save(this, editStartDate, editEndDate, editDiscountPrice));
        editDiscountPrice.addTextChangedListener(new DiscountPercentageWatcher());

        // load date
        model.getAllFlowersChecked(adapter, notice);
    }


    private void displayDatePicker(@NonNull EditText editDate) {
        Calendar calendar = Calendar.getInstance();

        new DatePickerDialog(this,
                (v, y, m, d) -> onDateSet(editDate, y, m, d),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    private void onDateSet(@NonNull EditText editDate, int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        editDate.setText(AppFormatter.formatPromotionDate(calendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
