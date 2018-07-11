package com.example.floralboutique.ui.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.floralboutique.R;
import com.example.floralboutique.data.entity.Category;
import com.example.floralboutique.util.AppExecutors;
import com.example.floralboutique.util.BitmapUtil;
import com.example.floralboutique.util.ClickableListAdapter;

public class CategoryListAdapter extends ClickableListAdapter<Category, CategoryListAdapter.ViewHolder> {
    private final AppExecutors executors_ = AppExecutors.getInstance();

    public CategoryListAdapter(@NonNull OnItemClickHandler onItemClickHandler) {
        super(onItemClickHandler);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Category category = items_.get(i);
        viewHolder.textCategoryName_.setText(category.name);
        BitmapUtil.load(viewHolder.imageThumbnail_, category.image);
        viewHolder.itemView.setOnClickListener(view -> onItemClickHandler_.onItemClick(category));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textCategoryName_;
        private final ImageView imageThumbnail_;

        ViewHolder(View view) {
            super(view);
            textCategoryName_ = view.findViewById(R.id.text_name);
            imageThumbnail_ = view.findViewById(R.id.image_thumbnail);
        }
    }
}
