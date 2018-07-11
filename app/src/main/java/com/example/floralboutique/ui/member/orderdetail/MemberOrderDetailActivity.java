package com.example.floralboutique.ui.member.orderdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.floralboutique.R;
import com.example.floralboutique.ui.member.orderlist.MemberOrderListItemModel;
import com.example.floralboutique.util.AppComponents;
import com.example.floralboutique.util.AppExecutors;
import com.example.floralboutique.util.AppFormatter;
import com.example.floralboutique.util.ViewBuilder;

import java.util.List;

public class MemberOrderDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static String ORDER_ID_EXTRA = "ORDER_ID";
    public static String ORDER_DATE_EXTRA = "ORDER_DATE";
    public static String ORDER_STATUS_EXTRA = "ORDER_STATUS_EXTRA";
    private final AppExecutors executors_ = AppExecutors.getInstance();
    private String selectedStatus_;
    private List<ListViewFlowerOrder> flowers_;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_order_detail);

        int orderId = getIntent().getIntExtra(ORDER_ID_EXTRA, 0);
        String date = getIntent().getStringExtra(ORDER_DATE_EXTRA);
        String status = getIntent().getStringExtra(ORDER_STATUS_EXTRA);
        selectedStatus_ = status;

        // load view model
        MemberOrderDetailViewModelFactory factory = AppComponents.getMemberOrderDetailViewModelFactory(this, orderId);
        MemberOrderDetailViewModel model = ViewModelProviders.of(this, factory).get(MemberOrderDetailViewModel.class);

        // setup toolbar
        ViewBuilder.buildToolbar(this, AppFormatter.formatId(orderId));

        // Setup the recycler view
        MemberOrderDetailAdapter listAdapter = new MemberOrderDetailAdapter();
        RecyclerView recyclerView = ViewBuilder.buildRecyclerView(getWindow().getDecorView(), listAdapter);

        // get other widgets
        TextView textDatetime = findViewById(R.id.text_datetime);
        TextView textTotal = findViewById(R.id.text_total);
        TextView notice = findViewById(R.id.notice);
        Button buttonSave = findViewById(R.id.button_save);
        textDatetime.setText(date);

        // setup the spinner
        Spinner spinnerStatus = findViewById(R.id.spinner_status);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (status.equals("Canceled")) {
            spinnerAdapter.add("Canceled");
            spinnerStatus.setEnabled(false);
        } else if (status.equals("Issued")) {
            spinnerAdapter.add("Issued");
            spinnerStatus.setEnabled(false);
        } else {
            spinnerAdapter.addAll(status, "Canceled");
        }
        spinnerStatus.setAdapter(spinnerAdapter);
        spinnerStatus.setOnItemSelectedListener(this);


        model.getFlowers().observe(this,
                flowers -> onFlowersChanged(flowers, textTotal, listAdapter, notice));
        buttonSave.setOnClickListener(v -> save(model, status, orderId));
    }

    private void onOrderChanged(MemberOrderListItemModel order, TextView textDateTime) {
        textDateTime.setText(AppFormatter.formatOrderDate(order.date));
    }

    private void onFlowersChanged(List<ListViewFlowerOrder> flowers, TextView textTotal, MemberOrderDetailAdapter adapter, TextView notice) {
        if (flowers == null || flowers.size() == 0) {
            adapter.clearItems();
            textTotal.setText(AppFormatter.formatPrice(0));
            notice.setVisibility(View.VISIBLE);
            return;
        }

        notice.setVisibility(View.GONE);
        flowers_ = flowers;
        adapter.swapItems(flowers);

        float total = 0;
        for (ListViewFlowerOrder flower : flowers) {
            total += flower.price * flower.quantity;
        }

        textTotal.setText(AppFormatter.formatPrice(total));
    }


    private void save(@NonNull MemberOrderDetailViewModel model, String status, int id) {
        if (!status.equals(selectedStatus_)) {
            executors_.diskIo.execute(() -> model.updateOrderStatus(id, selectedStatus_));
            finish();
        }
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        selectedStatus_ = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // do nothing
    }
}
