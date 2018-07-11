package com.example.floralboutique.ui.member.flowerlist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.floralboutique.ui.member.flowerdetail.MemberFlowerDetailActivity;
import com.example.floralboutique.R;
import com.example.floralboutique.util.AppComponents;
import com.example.floralboutique.util.AppExecutors;
import com.example.floralboutique.util.ViewBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

public class MemberFlowerListActivity extends AppCompatActivity {
    public static final String CATEGORY_EXTRA = "CATEGORY";
    private final AppExecutors executors_ = AppExecutors.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_flower_list);
        String category = Objects.requireNonNull(getIntent().getExtras()).getString(CATEGORY_EXTRA);

        // load the view model
        MemberFlowerListViewModelFactory factory = AppComponents.getShopFlowerListViewModelFactory(getApplicationContext(), category);
        MemberFlowerListViewModel model = ViewModelProviders.of(this, factory).get(MemberFlowerListViewModel.class);

        // Setup the recycler view
        MemberFlowerListAdapter adapter = new MemberFlowerListAdapter(this::onFlowerSelected);
        RecyclerView recyclerView = ViewBuilder.buildRecyclerView(this, adapter);

        //
        TextView notice = findViewById(R.id.notice);

        // setup the toolbar
        ViewBuilder.buildToolbar(this, category + " Collection");

        model.getFlowers().observe(this, flowers -> onFlowersChanged(flowers, adapter, notice));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onFlowerSelected(@NonNull Object item) {
        MemberFlowerListItemModel flower = (MemberFlowerListItemModel) item;
        Intent intent = new Intent(MemberFlowerListActivity.this, MemberFlowerDetailActivity.class);
        intent.putExtra(MemberFlowerDetailActivity.FLOWER_NAME_EXTRA, flower.name);
        startActivity(intent);
    }

    public void onFlowersChanged(List<MemberFlowerListItemModel> flowers, MemberFlowerListAdapter adapter, TextView notice) {
        if (flowers == null || flowers.size() == 0) {
            adapter.clearItems();
            notice.setVisibility(View.VISIBLE);
            return;
        }
        TreeMap<String, MemberFlowerListItemModel> flowerMap = new TreeMap<>();
        for (MemberFlowerListItemModel flower : flowers) {
            MemberFlowerListItemModel mapped = flowerMap.get(flower.name);
            if (mapped != null) {
                flower = new MemberFlowerListItemModel(flower.name,
                        flower.image,
                        flower.price,
                        flower.discountPercent * mapped.discountPercent,
                        flower.quantity);
            }
            flowerMap.put(flower.name, flower);
        }
        adapter.swapItems(new ArrayList<>(flowerMap.values()));
        notice.setVisibility(View.GONE);
    }

}
