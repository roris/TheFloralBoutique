package com.example.floralboutique.ui.admin.orderdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
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

import com.example.floralboutique.ui.member.orderdetail.ListViewFlowerOrder;
import com.example.floralboutique.ui.member.orderdetail.MemberOrderDetailAdapter;
import com.example.floralboutique.util.AppComponents;
import com.example.floralboutique.util.AppFormatter;
import com.example.floralboutique.util.ViewBuilder;
import com.example.floralboutique.R;

import java.util.List;

public class AdminOrderDetailActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    public static final String ORDER_ID_EXTRA = "Order.Id";
    public static final String ORDER_STATUS_EXTRA = "Order.Status";
    public static final String ORDER_MEMBER_EXTRA = "Order.Member";
    public static final String ORDER_DATE_EXTRA = "Order.Date";
    private static String selectedStatus_;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_detail);

        // get args
        String status = getIntent().getStringExtra(ORDER_STATUS_EXTRA);
        String member = getIntent().getStringExtra(ORDER_MEMBER_EXTRA);
        String date = getIntent().getStringExtra(ORDER_DATE_EXTRA);
        int id = getIntent().getIntExtra(ORDER_ID_EXTRA, 0);

        // get the view model
        AdminOrderDetailViewModelFactory factory = AppComponents.getAdminOrderDetailViewModelFactory(getApplicationContext(), status, id);
        AdminOrderDetailViewModel model = ViewModelProviders.of(this, factory).get(AdminOrderDetailViewModel.class);

        // setup the recycler view
        MemberOrderDetailAdapter listAdapter = new MemberOrderDetailAdapter();
        RecyclerView recyclerView = ViewBuilder.buildRecyclerView(this, listAdapter);

        // setup the toolbar
        final String title = "Order " + AppFormatter.formatId(id);
        ViewBuilder.buildToolbar(this, title);

        // setup the spinner
        Spinner spinnerStatus = findViewById(R.id.spinner_status);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (status.equals("Pending")) {
            spinnerAdapter.addAll(status, "Ready", "Issued");
        } else if (status.equals("Ready")) {
            spinnerAdapter.addAll(status, "Issued");
        } else {
            spinnerAdapter.addAll(status);
        }
        spinnerStatus.setAdapter(spinnerAdapter);
        spinnerStatus.setOnItemSelectedListener(this);

        Button buttonSave = findViewById(R.id.button_save);
        TextView textTotal = findViewById(R.id.text_total);
        TextView textMember = findViewById(R.id.text_member);
        TextView textDatetime = findViewById(R.id.text_datetime);


        textDatetime.setText(date);
        textMember.setText(member);

        buttonSave.setOnClickListener(v -> onButtonSaveClicked(model));

        model.getFlowers().observe(this,
                flowers -> onFlowersChanged(flowers, textTotal, listAdapter));
    }

    void onButtonSaveClicked(AdminOrderDetailViewModel model) {
        model.updateStatus(selectedStatus_);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        selectedStatus_ = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // do nothing
    }

    public void onFlowersChanged(List<ListViewFlowerOrder> flowers, TextView textTotal, MemberOrderDetailAdapter adapter) {
        if (flowers == null || flowers.size() == 0) {
            adapter.clearItems();
            textTotal.setText(AppFormatter.formatPrice(0));
            return;
        }

        adapter.swapItems(flowers);

        float total = 0;
        for (ListViewFlowerOrder flower : flowers) {
            total += flower.price * flower.quantity;
        }

        textTotal.setText(AppFormatter.formatPrice(total));
    }

    private void calculateTotal(List<ListViewFlowerOrder> flowers, TextView textTotal) {
        float total = 0;

        for (ListViewFlowerOrder flower : flowers) {
            total += flower.price * flower.quantity;
        }

        textTotal.setText(AppFormatter.formatPrice(total));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
