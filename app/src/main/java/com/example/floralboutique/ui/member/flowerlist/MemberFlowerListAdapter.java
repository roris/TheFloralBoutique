package com.example.floralboutique.ui.member.flowerlist;

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
import com.example.floralboutique.util.ClickableListAdapter;

public class MemberFlowerListAdapter extends ClickableListAdapter<MemberFlowerListItemModel, MemberFlowerListAdapter.ViewHolder> {
    private final AppExecutors executors_ = AppExecutors.getInstance();

    public MemberFlowerListAdapter(@NonNull OnItemClickHandler onItemClickHandler) {
        super(onItemClickHandler);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_member_flower_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        MemberFlowerListItemModel flower = items_.get(i);

        holder.textFlower_.setText(flower.name);
        holder.textQuantity_.setText(AppFormatter.formatInCartQuantity(flower.quantity));

        if (flower.discountPercent != 1) {
            holder.textPreviousPrice_.setText(AppFormatter.formatPercentOff(flower.discountPercent));
            holder.textPrice_.setText(AppFormatter.formatPrice(flower.price * flower.discountPercent));
        } else {
            holder.textPrice_.setText(AppFormatter.formatPrice(flower.price));
        }

        BitmapUtil.load(holder.imageThumbnail_, flower.image);
        holder.itemView.setOnClickListener(v -> onItemClickHandler_.onItemClick(flower));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textFlower_;
        TextView textPrice_;
        TextView textQuantity_;
        TextView textPreviousPrice_;
        ImageView imageThumbnail_;

        ViewHolder(View view) {
            super(view);
            imageThumbnail_ = view.findViewById(R.id.image_thumbnail);
            textFlower_ = view.findViewById(R.id.text_flower);
            textPrice_ = view.findViewById(R.id.text_price);
            textPreviousPrice_ = view.findViewById(R.id.text_discount_percent);
            textQuantity_ = view.findViewById(R.id.text_quantity);
        }
    }
}
