package com.example.floralboutique.ui.admin.flowerdetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.floralboutique.util.GenericListAdapter;
import com.example.floralboutique.R;

class AdminFlowerDetailAdapter extends GenericListAdapter<CheckedCategory, AdminFlowerDetailAdapter.ViewHolder> {
    AdminFlowerDetailAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {
        return new ViewHolder(LayoutInflater.from(group.getContext()).inflate(R.layout.item_checked_category_list, group, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        CheckedCategory item = items_.get(i);
        holder.checkboxChecked.setChecked(item.checked);
        holder.textCategory.setText(item.category);
        holder.checkboxChecked.setOnClickListener(v -> {
            item.checked = ((CheckBox) v).isChecked();
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textCategory;
        final AppCompatCheckBox checkboxChecked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textCategory = itemView.findViewById(R.id.text_category);
            checkboxChecked = itemView.findViewById(R.id.checkbox_checked);
        }
    }
}
