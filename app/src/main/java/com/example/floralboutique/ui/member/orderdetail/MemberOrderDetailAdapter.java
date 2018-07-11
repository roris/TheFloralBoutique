package com.example.floralboutique.ui.member.orderdetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.floralboutique.R;
import com.example.floralboutique.util.AppExecutors;
import com.example.floralboutique.util.AppFormatter;
import com.example.floralboutique.util.BitmapUtil;

import java.util.List;

public class MemberOrderDetailAdapter extends RecyclerView.Adapter<MemberOrderDetailAdapter.ViewHolder> {
    private final AppExecutors executors_ = AppExecutors.getInstance();
    List<ListViewFlowerOrder> items_;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {
        return new ViewHolder(LayoutInflater.from(group.getContext()).inflate(R.layout.item_flower_order_list, group, false));
    }

    @Override// TODO: start the member order detail activity
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ListViewFlowerOrder flower = items_.get(i);

        holder.textFlower_.setText(flower.name);
        holder.textPrice_.setText(AppFormatter.formatPrice(flower.price));
        holder.textQuantity_.setText(AppFormatter.formatOrderQuantity(flower.quantity));

        BitmapUtil.load(holder.imageThumbnail_, flower.image);
    }

    @Override
    public int getItemCount() {
        return items_ == null ? 0 : items_.size();
    }

    public void swapItems(@NonNull List<ListViewFlowerOrder> items) {
        items_ = items;
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (items_ != null) {
            items_.clear();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageThumbnail_;
        final TextView textFlower_;
        final TextView textQuantity_;
        final TextView textPrice_;

        public ViewHolder(View view) {
            super(view);
            imageThumbnail_ = view.findViewById(R.id.image_thumbnail);
            textFlower_ = view.findViewById(R.id.text_flower);
            textQuantity_ = view.findViewById(R.id.text_quantity);
            textPrice_ = view.findViewById(R.id.text_price);
        }
    }
}
