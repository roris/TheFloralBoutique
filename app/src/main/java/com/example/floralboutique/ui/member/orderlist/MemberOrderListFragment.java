package com.example.floralboutique.ui.member.orderlist;

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

import com.example.floralboutique.ui.member.orderdetail.MemberOrderDetailActivity;
import com.example.floralboutique.R;
import com.example.floralboutique.data.entity.Order;
import com.example.floralboutique.util.AppComponents;
import com.example.floralboutique.util.AppExecutors;
import com.example.floralboutique.util.AppFormatter;
import com.example.floralboutique.util.ViewBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemberOrderListFragment extends Fragment {
    private final static String MEMBER_EXTRA = "USERNAME";
    private final AppExecutors executors_ = AppExecutors.getInstance();
    private List<Order> orders_ = new ArrayList<>();

    public static MemberOrderListFragment newInstance(String member) {
        MemberOrderListFragment fragment = new MemberOrderListFragment();
        Bundle args = new Bundle();
        args.putString(MEMBER_EXTRA, member);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup root, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_order_list, root, false);
        String member = Objects.requireNonNull(getArguments()).getString(MEMBER_EXTRA);

        // Set up view model
        MemberOrderListViewModelFactory factory = AppComponents.getMemberOrderViewModelFactory(Objects.requireNonNull(getActivity()).getApplicationContext(), member);
        MemberOrderListViewModel model = ViewModelProviders.of(this, factory).get(MemberOrderListViewModel.class);

        // Setup recycler view
        MemberOrderListAdapter adapter = new MemberOrderListAdapter(this::startOrderDetailActivity);
        RecyclerView recyclerView = ViewBuilder.buildRecyclerView(view, adapter);

        // get the other widgets
        TextView textLogin = view.findViewById(R.id.text_login);
        TextView textNoOrders = view.findViewById(R.id.text_no_orders);

        // redraw orders whenever
        if (member == null || member.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textLogin.setVisibility(View.VISIBLE);
        } else {
            model.getOrders().observe(getViewLifecycleOwner(),
                    orders -> onOrdersChanged(orders, recyclerView, adapter, textNoOrders));
        }

        return view;
    }

    private void startOrderDetailActivity(@NonNull Object item) {
        MemberOrderListItemModel order = (MemberOrderListItemModel) item;
        Intent intent = new Intent(getContext(), MemberOrderDetailActivity.class);
        intent.putExtra(MemberOrderDetailActivity.ORDER_STATUS_EXTRA, order.status);
        intent.putExtra(MemberOrderDetailActivity.ORDER_DATE_EXTRA, AppFormatter.formatOrderDate(order.date));
        intent.putExtra(MemberOrderDetailActivity.ORDER_ID_EXTRA, order.id);
        startActivity(intent);
    }

    private void onOrdersChanged(List<MemberOrderListItemModel> orders, RecyclerView recyclerView, MemberOrderListAdapter adapter, TextView textNoOrders) {
        if (orders == null || orders.size() == 0) {
            adapter.clearItems();
            recyclerView.setVisibility(View.GONE);
            textNoOrders.setVisibility(View.VISIBLE);
            return;
        }

        recyclerView.setVisibility(View.VISIBLE);
        textNoOrders.setVisibility(View.GONE);
        adapter.swapItems(orders);
    }
}
