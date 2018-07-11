package com.example.floralboutique.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

@Entity(tableName = "FlowersOrders",
        primaryKeys = {"flower", "order"},
        foreignKeys = {
                @ForeignKey(entity = Flower.class, parentColumns = "name", childColumns = "flower"),
                @ForeignKey(entity = Order.class, parentColumns = "id", childColumns = "order")
        }
)
public class FlowerOrder {
    @NonNull
    public final String flower;
    public final int order;
    public final float price;
    public final int quantity;

    public FlowerOrder(@NonNull String flower, int order, float price, int quantity) {
        this.flower = flower;
        this.order = order;
        this.price = price;
        this.quantity = quantity;
    }
}
