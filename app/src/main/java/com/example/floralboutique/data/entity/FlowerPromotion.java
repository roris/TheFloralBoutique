package com.example.floralboutique.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

@Entity(tableName = "FlowersPromotions",
        primaryKeys = {"flower", "promotion"},
        foreignKeys = {
                @ForeignKey(entity = Flower.class,
                        parentColumns = "name",
                        childColumns = "flower"),
                @ForeignKey(entity = Promotion.class,
                        parentColumns = "id",
                        childColumns = "promotion")
        }
)
public class FlowerPromotion {
    @NonNull
    public final String flower;
    public final int promotion;

    public FlowerPromotion(@NonNull String flower, int promotion) {
        this.flower = flower;
        this.promotion = promotion;
    }
}
