package com.example.floralboutique.ui.admin.orderlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.floralboutique.R;
import com.example.floralboutique.data.entity.Order;
import com.example.floralboutique.util.AppFormatter;
import com.example.floralboutique.util.ClickableListAdapter;

public class AdminOrderListAdapter extends ClickableListAdapter<Order, AdminOrderListAdapter.ViewHolder> {
    public AdminOrderListAdapter(OnItemClickHandler onItemClickHandler) {
        super(onItemClickHandler);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {
        View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_admin_order_list, group, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Order order = items_.get(i);

        holder.textId.setText(AppFormatter.formatId(order.id));
        holder.textDatetime.setText(AppFormatter.formatOrderDate(order.date));
        holder.textStatus.setText(order.status);
        holder.textMember.setText(order.user);

        holder.itemView.setOnClickListener(v -> onItemClickHandler_.onItemClick(order));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textId;
        TextView textDatetime;
        TextView textMember;
        TextView textStatus;

        ViewHolder(View view) {
            super(view);
            textId = view.findViewById(R.id.text_id);
            textDatetime = view.findViewById(R.id.text_datetime);
            textMember = view.findViewById(R.id.text_member);
            textStatus = view.findViewById(R.id.text_status);
        }
    }
}
