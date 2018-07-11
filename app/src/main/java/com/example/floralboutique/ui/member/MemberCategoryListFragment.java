package com.example.floralboutique.ui.member;

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

import com.example.floralboutique.ui.member.flowerlist.MemberFlowerListActivity;
import com.example.floralboutique.R;
import com.example.floralboutique.data.entity.Category;
import com.example.floralboutique.ui.common.CategoryListAdapter;
import com.example.floralboutique.ui.common.CategoryListViewModel;
import com.example.floralboutique.ui.common.CategoryListViewModelFactory;
import com.example.floralboutique.util.AppComponents;
import com.example.floralboutique.util.AppExecutors;
import com.example.floralboutique.util.ViewBuilder;

import java.util.List;
import java.util.Objects;

public class MemberCategoryListFragment extends Fragment {
    private final AppExecutors executors_ = AppExecutors.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(Objects.requireNonNull(parent).getContext()).inflate(R.layout.fragment_shop, parent, false);

        // load the ViewModel for this
        CategoryListViewModelFactory factory = AppComponents.getCategoryListViewModelFactory(getContext());
        CategoryListViewModel model = ViewModelProviders.of(this, factory).get(CategoryListViewModel.class);

        // setup the recycler view
        CategoryListAdapter adapter = new CategoryListAdapter(this::onCategoryClick);
        RecyclerView recyclerView = ViewBuilder.buildRecyclerView(view, adapter);

        //
        TextView notice = view.findViewById(R.id.notice);

        model.getAllCategories().observe(getViewLifecycleOwner(),
                categories -> onCategoriesChanged(categories, adapter, notice));

        return view;
    }

    private void onCategoriesChanged(List<Category> categories, CategoryListAdapter adapter, TextView notice) {
        if (categories == null || categories.size() == 0) {
            adapter.clearItems();
            notice.setVisibility(View.VISIBLE);
        } else {
            adapter.swapItems(categories);
            notice.setVisibility(View.GONE);
        }
    }

    private void onCategoryClick(@NonNull Object item) {
        Category category = (Category) item;
        Intent intent = new Intent(getActivity(), MemberFlowerListActivity.class);
        intent.putExtra(MemberFlowerListActivity.CATEGORY_EXTRA, category.name);
        startActivity(intent);
    }
}
