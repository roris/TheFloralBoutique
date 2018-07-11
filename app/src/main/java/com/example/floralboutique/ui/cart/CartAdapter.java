package com.example.floralboutique.ui.cart;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.floralboutique.R;
import com.example.floralboutique.util.AppFormatter;
import com.example.floralboutique.util.BitmapUtil;
import com.example.floralboutique.util.ClickableListAdapter;

public class CartAdapter extends ClickableListAdapter<CartItemModel, CartAdapter.ViewHolder> {
    @NonNull
    private final OnItemRemovedHandler onItemRemovedHandler_;
    private final OnItemQuantityChangedHandler onItemQuantityChangedHandler_;

    public CartAdapter(@NonNull OnItemClickHandler onItemClickHandler,
                       @NonNull OnItemRemovedHandler onItemRemovedHandler,
                       @NonNull OnItemQuantityChangedHandler onItemQuantityChangedHandler) {
        super(onItemClickHandler);
        onItemRemovedHandler_ = onItemRemovedHandler;
        onItemQuantityChangedHandler_ = onItemQuantityChangedHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItemModel item = items_.get(position);


        holder.textFlower_.setText(item.flowerName);
        BitmapUtil.load(holder.imageThumbnail_, item.image);

        if (item.discountPercent != 1) {
            holder.textPrice_.setText(AppFormatter.formatDiscountPrice(item.price, item.discountPercent));
            holder.textPrice_.setTextColor(holder.itemView.getResources().getColor(R.color.colorAccent));
        } else {
            holder.textPrice_.setText(AppFormatter.formatPrice(item.price));
            holder.textPrice_.setTextColor(holder.itemView.getResources().getColor(android.R.color.tab_indicator_text));
        }

        holder.editQuantity_.setText(AppFormatter.formatQuantity(item.quantity));
        holder.editQuantity_.setOnKeyListener((view, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    onItemQuantityChangedHandler_.onItemQuantityChanged(item, Integer.parseInt(holder.editQuantity_.getText().toString()));
                    return true;
                }
            }
            return false;
        });

        holder.imageRemove_.setOnClickListener(v -> onItemRemovedHandler_.onItemRemoved(item));
        holder.itemView.setOnClickListener(v -> onItemClickHandler_.onItemClick(item));
    }

    public interface OnItemRemovedHandler {
        void onItemRemoved(@NonNull CartItemModel item);
    }

    public interface OnItemQuantityChangedHandler {
        void onItemQuantityChanged(@NonNull CartItemModel item, int quantity);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textFlower_;
        private final TextView textPrice_;
        private final EditText editQuantity_;

        private final ImageView imageThumbnail_;
        private final ImageView imageRemove_;

        public ViewHolder(View view) {
            super(view);
            textFlower_ = view.findViewById(R.id.text_flower);
            textPrice_ = view.findViewById(R.id.text_price);
            editQuantity_ = view.findViewById(R.id.edit_quantity);
            imageThumbnail_ = view.findViewById(R.id.image_thumbnail);
            imageRemove_ = view.findViewById(R.id.image_remove);
        }
    }
}
