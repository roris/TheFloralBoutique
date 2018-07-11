package com.example.floralboutique.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Flowers")
public class Flower {
    @PrimaryKey
    @NonNull
    public final String name;
    @NonNull
    public final String description;
    @NonNull
    public final String image;
    public final float price;

    public Flower(@NonNull String name, @NonNull String description, float price, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }
}
