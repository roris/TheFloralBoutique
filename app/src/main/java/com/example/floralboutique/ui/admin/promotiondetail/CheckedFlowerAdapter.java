package com.example.floralboutique.ui.admin.promotiondetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.floralboutique.R;
import com.example.floralboutique.util.AppFormatter;
import com.example.floralboutique.util.BitmapUtil;
import com.example.floralboutique.util.GenericListAdapter;

public class CheckedFlowerAdapter extends GenericListAdapter<CheckedFlower, CheckedFlowerAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_checked_flower_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        CheckedFlower flower = items_.get(i);
        holder.textFlower.setText(flower.name);
        holder.textDiscountPrice.setText(AppFormatter.formatPrice(flower.price));
        holder.textPrice.setText(AppFormatter.formatPercentOff(flower.discountPercent));
        holder.checkboxChecked.setChecked(flower.checked);
        holder.checkboxChecked.setOnClickListener(v -> flower.checked = ((CheckBox) v).isChecked());
        BitmapUtil.load(holder.imageThumbnail, flower.image);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageThumbnail;
        final TextView textFlower;
        final TextView textPrice;
        final TextView textDiscountPrice;
        final CheckBox checkboxChecked;


        ViewHolder(View view) {
            super(view);
            textFlower = view.findViewById(R.id.text_flower);
            imageThumbnail = view.findViewById(R.id.image_thumbnail);
            textPrice = view.findViewById(R.id.text_price);
            textDiscountPrice = view.findViewById(R.id.text_discount_price);
            checkboxChecked = view.findViewById(R.id.checkbox_checked);
        }
    }
}
