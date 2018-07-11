package com.example.floralboutique.ui.admin.promotionlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.floralboutique.R;
import com.example.floralboutique.data.entity.Promotion;
import com.example.floralboutique.util.AppFormatter;
import com.example.floralboutique.util.ClickableListAdapter;

class AdminPromotionListAdapter extends ClickableListAdapter<Promotion, AdminPromotionListAdapter.ViewHolder> {
    AdminPromotionListAdapter(OnItemClickHandler onItemClickHandler) {
        super(onItemClickHandler);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {
        View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_promotion_list, group, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Promotion promotion = items_.get(i);
        holder.itemView.setOnClickListener(v -> onItemClickHandler_.onItemClick(promotion));
        holder.textId.setText(AppFormatter.formatId(promotion.id));
        holder.textDiscountPercent.setText(AppFormatter.formatPercentOff(promotion.discountPercent));
        holder.textPeriod.setText(AppFormatter.formatPeriod(promotion.startDate, promotion.endDate));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textId;
        final TextView textDiscountPercent;
        final TextView textPeriod;

        ViewHolder(View view) {
            super(view);
            textId = view.findViewById(R.id.text_id);
            textDiscountPercent = view.findViewById(R.id.text_discount_percent);
            textPeriod = view.findViewById(R.id.text_period);
        }
    }
}
