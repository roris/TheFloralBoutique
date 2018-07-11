package com.example.floralboutique.ui.member.orderlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.floralboutique.R;
import com.example.floralboutique.util.AppFormatter;
import com.example.floralboutique.util.ClickableListAdapter;

public class MemberOrderListAdapter extends ClickableListAdapter<MemberOrderListItemModel, MemberOrderListAdapter.ViewHolder> {

    public MemberOrderListAdapter(@NonNull OnItemClickHandler onItemClickHandler) {
        super(onItemClickHandler);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {
        return new ViewHolder(LayoutInflater.from(group.getContext())
                .inflate(R.layout.item_member_order_list, group, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        MemberOrderListItemModel order = items_.get(i);
        holder.textDatetime_.setText(AppFormatter.formatOrderDate(order.date));
        holder.textId_.setText(AppFormatter.formatId(order.id));
        holder.textStatus_.setText(order.status);
        holder.itemView.setOnClickListener((view) -> onItemClickHandler_.onItemClick(order));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textId_;
        TextView textDatetime_;
        TextView textStatus_;

        public ViewHolder(View view) {
            super(view);
            textId_ = view.findViewById(R.id.text_id);
            textDatetime_ = view.findViewById(R.id.text_datetime);
            textStatus_ = view.findViewById(R.id.text_status);
        }
    }
}
