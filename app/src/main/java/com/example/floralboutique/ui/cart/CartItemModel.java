package com.example.floralboutique.ui.cart;

import android.support.annotation.NonNull;

public class CartItemModel {
    @NonNull
    public final String flowerName;
    @NonNull
    public final String image;
    public final float price;
    public final float discountPercent;
    public final int quantity;

    public CartItemModel(@NonNull String flowerName, @NonNull String image, float price, float discountPercent, int quantity) {
        this.flowerName = flowerName;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.discountPercent = discountPercent;
    }
}
