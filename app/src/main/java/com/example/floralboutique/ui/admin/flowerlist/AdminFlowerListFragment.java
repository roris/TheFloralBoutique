package com.example.floralboutique.ui.admin.flowerlist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.floralboutique.ui.admin.flowerdetail.AdminFlowerDetailActivity;
import com.example.floralboutique.util.AppComponents;
import com.example.floralboutique.R;
import com.example.floralboutique.data.entity.Flower;

public class AdminFlowerListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup group,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_admin_list, group, false);
        Button buttonNew = view.findViewById(R.id.button_new);
        TextView notice = view.findViewById(R.id.notice);

        AdminFlowerListViewModelFactory factory = AppComponents.getManageFlowerListViewModelFactory(getActivity());
        AdminFlowerListViewModel model = ViewModelProviders.of(this, factory).get(AdminFlowerListViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        AdminFlowerListAdapter adapter = new AdminFlowerListAdapter(this::onFlowerClick);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(adapter);

        buttonNew.setOnClickListener(v -> startManageFlowerDetailActivity("", "", 0, ""));

        model.getFlowers().observe(getViewLifecycleOwner(), flowers -> {
            if (flowers == null || flowers.size() == 0) {
                adapter.clearItems();
                notice.setVisibility(View.VISIBLE);
            } else {
                adapter.swapItems(flowers);
                notice.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void onFlowerClick(Object item) {
        Flower flower = (Flower) item;
        startManageFlowerDetailActivity(flower.name, flower.description, flower.price, flower.image);
    }

    private void startManageFlowerDetailActivity(String name, String description, float price, String image) {
        Intent intent = new Intent(getActivity(), AdminFlowerDetailActivity.class);
        intent.putExtra(AdminFlowerDetailActivity.FLOWER_EXTRA, name);
        intent.putExtra(AdminFlowerDetailActivity.PRICE_EXTRA, price);
        intent.putExtra(AdminFlowerDetailActivity.DESCRIPTION_EXTRA, description);
        intent.putExtra(AdminFlowerDetailActivity.IMAGE_EXTRA, image);
        startActivity(intent);
    }

}
