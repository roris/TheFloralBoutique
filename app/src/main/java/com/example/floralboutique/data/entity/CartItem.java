package com.example.floralboutique.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Cart")
public class CartItem {
    @PrimaryKey
    @NonNull
    public final String flower;
    public final int quantity;

    public CartItem(@NonNull String flower, int quantity) {
        this.flower = flower;
        this.quantity = quantity;
    }
}
