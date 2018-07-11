package com.example.floralboutique.ui.admin.flowerlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.floralboutique.util.AppExecutors;
import com.example.floralboutique.util.BitmapUtil;
import com.example.floralboutique.util.ClickableListAdapter;
import com.example.floralboutique.R;
import com.example.floralboutique.data.entity.Flower;

public class AdminFlowerListAdapter extends ClickableListAdapter<Flower, AdminFlowerListAdapter.ViewHolder> {
    private final AppExecutors executors_ = AppExecutors.getInstance();

    public AdminFlowerListAdapter(OnItemClickHandler onItemClickHandler) {
        super(onItemClickHandler);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {
        return new ViewHolder(LayoutInflater.from(group.getContext()).inflate(R.layout.item_admin_flower_list, group, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Flower item = items_.get(i);
        holder.textFlower.setText(item.name);
        BitmapUtil.load(holder.imageThumbnail, item.image);
        holder.itemView.setOnClickListener(v -> onItemClickHandler_.onItemClick(item));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        final TextView textFlower;
        @NonNull
        final ImageView imageThumbnail;

        ViewHolder(@NonNull View view) {
            super(view);
            textFlower = view.findViewById(R.id.text_flower);
            imageThumbnail = view.findViewById(R.id.image_thumbnail);
        }
    }
}
