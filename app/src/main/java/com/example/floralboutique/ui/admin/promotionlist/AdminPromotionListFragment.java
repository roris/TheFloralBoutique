package com.example.floralboutique.ui.admin.promotionlist;

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
import android.widget.Button;
import android.widget.TextView;

import com.example.floralboutique.ui.admin.promotiondetail.AdminPromotionDetailActivity;
import com.example.floralboutique.util.ViewBuilder;
import com.example.floralboutique.R;
import com.example.floralboutique.data.entity.Promotion;
import com.example.floralboutique.util.AppComponents;

import java.util.Date;
import java.util.List;

public class AdminPromotionListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup group, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_admin_list, group, false);

        // get args
        AdminPromotionListViewModelFactory factory = AppComponents.getAdminPromotionListViewModelFactory(getContext());
        AdminPromotionListViewModel model = ViewModelProviders.of(this, factory).get(AdminPromotionListViewModel.class);

        // setup recycler view
        AdminPromotionListAdapter listAdapter = new AdminPromotionListAdapter(this::startPromotionDetailActivity);
        RecyclerView recyclerView = ViewBuilder.buildRecyclerView(view, listAdapter);

        Button buttonNew = view.findViewById(R.id.button_new);
        TextView notice = view.findViewById(R.id.notice);

        buttonNew.setOnClickListener(v -> startPromotionDetailActivity(new Promotion(0, new Date(), new Date(), 1.0f)));

        model.getAllPromotions().observe(getViewLifecycleOwner(), promotions -> onPromotionsChanged(promotions, listAdapter, notice));

        return view;
    }

    private void onPromotionsChanged(List<Promotion> promotions, AdminPromotionListAdapter adapter, TextView notice) {
        if (promotions == null || promotions.size() == 0) {
            adapter.clearItems();
            notice.setVisibility(View.VISIBLE);
        } else {
            adapter.swapItems(promotions);
            notice.setVisibility(View.GONE);
        }
    }

    private void startPromotionDetailActivity(Object item) {
        Promotion promotion = (Promotion) item;
        Intent intent = new Intent(getActivity(), AdminPromotionDetailActivity.class);
        intent.putExtra(AdminPromotionDetailActivity.PROMOTION_ID_EXTRA, promotion.id);
        intent.putExtra(AdminPromotionDetailActivity.PROMOTION_START_DATE_EXTRA, promotion.startDate);
        intent.putExtra(AdminPromotionDetailActivity.PROMOTION_END_DATE_EXTRA, promotion.endDate);
        intent.putExtra(AdminPromotionDetailActivity.PROMOTION_DISCOUNT_PERCENT_EXTRA, promotion.discountPercent);
        startActivity(intent);
    }
}
