package com.example.floralboutique.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

@Entity(tableName = "FlowersCategories",
        primaryKeys = {"flower", "category"},
        foreignKeys = {
                @ForeignKey(entity = Flower.class, parentColumns = "name", childColumns = "flower"),
                @ForeignKey(entity = Category.class, parentColumns = "name", childColumns = "category")
        }
)
public class FlowerCategory {
    @NonNull
    public final String flower;
    @NonNull
    public final String category;

    public FlowerCategory(@NonNull String flower, @NonNull String category) {
        this.flower = flower;
        this.category = category;
    }
}
