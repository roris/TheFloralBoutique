package com.example.floralboutique.ui.admin.categorylist;

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

import com.example.floralboutique.ui.admin.categorydetail.AdminCategoryDetailActivity;
import com.example.floralboutique.ui.common.CategoryListAdapter;
import com.example.floralboutique.ui.common.CategoryListViewModel;
import com.example.floralboutique.ui.common.CategoryListViewModelFactory;
import com.example.floralboutique.util.AppComponents;
import com.example.floralboutique.util.ViewBuilder;
import com.example.floralboutique.R;
import com.example.floralboutique.data.entity.Category;

public class AdminCategoryListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup group, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_admin_list, group, false);

        // get view model
        CategoryListViewModelFactory factory = AppComponents.getCategoryListViewModelFactory(getActivity());
        CategoryListViewModel model = ViewModelProviders.of(this, factory).get(CategoryListViewModel.class);

        // setup recycler view
        CategoryListAdapter adapter = new CategoryListAdapter(this::onCategoryClick);
        RecyclerView recyclerView = ViewBuilder.buildRecyclerView(view, adapter);

        // setup other widgets
        Button buttonNew = view.findViewById(R.id.button_new);
        TextView notice = view.findViewById(R.id.notice);

        // events
        buttonNew.setOnClickListener(v -> onCategoryClick(new Category("", "")));

        // observer
        model.getAllCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories == null || categories.size() == 0) {
                adapter.clearItems();
                notice.setVisibility(View.VISIBLE);
            } else {
                adapter.swapItems(categories);
                notice.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public void onCategoryClick(@NonNull Object item) {
        Category category = (Category) item;
        Intent intent = new Intent(getActivity(), AdminCategoryDetailActivity.class);
        intent.putExtra(AdminCategoryDetailActivity.CATEGORY_EXTRA, category.name);
        intent.putExtra(AdminCategoryDetailActivity.IMAGE_EXTRA, category.image);
        startActivity(intent);
    }
}
