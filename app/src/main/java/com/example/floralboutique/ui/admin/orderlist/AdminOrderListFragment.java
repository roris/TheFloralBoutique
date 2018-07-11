package com.example.floralboutique.ui.admin.orderlist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.floralboutique.R;
import com.example.floralboutique.data.entity.Order;
import com.example.floralboutique.ui.admin.orderdetail.AdminOrderDetailActivity;
import com.example.floralboutique.util.AppComponents;
import com.example.floralboutique.util.AppFormatter;
import com.example.floralboutique.util.ViewBuilder;

import java.util.List;

public class AdminOrderListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup group, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_order_list, group, false);

        // get the view model
        AdminOrderListViewModelFactory factory = AppComponents.getAdminOrderListViewModelFactory(getActivity());
        AdminOrderListViewModel model = ViewModelProviders.of(this, factory).get(AdminOrderListViewModel.class);

        // setup recycler view
        AdminOrderListAdapter adapter = new AdminOrderListAdapter(this::startOrderDetailActivity);
        RecyclerView recyclerView = ViewBuilder.buildRecyclerView(view, adapter);

        TextView notice = view.findViewById(R.id.notice);
        // observe
        model.getOrders().observe(getViewLifecycleOwner(), orders -> onOrdersChanged(orders, adapter, notice));

        return view;
    }

    private void onOrdersChanged(List<Order> orders, AdminOrderListAdapter adapter, TextView notice) {
        if (orders == null || orders.size() == 0) {
            adapter.clearItems();
            notice.setVisibility(View.VISIBLE);
        } else {
            adapter.swapItems(orders);
            notice.setVisibility(View.GONE);
        }
    }

    private void startOrderDetailActivity(@NonNull Object item) {
        Order order = (Order) item;
        Intent intent = new Intent(getActivity(), AdminOrderDetailActivity.class);
        intent.putExtra(AdminOrderDetailActivity.ORDER_ID_EXTRA, order.id);
        intent.putExtra(AdminOrderDetailActivity.ORDER_STATUS_EXTRA, order.status);
        intent.putExtra(AdminOrderDetailActivity.ORDER_MEMBER_EXTRA, order.user);
        intent.putExtra(AdminOrderDetailActivity.ORDER_DATE_EXTRA, AppFormatter.formatOrderDate(order.date));
        startActivity(intent);
    }
}
