package com.example.floralboutique.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Categories")
public class Category {
    @PrimaryKey
    @NonNull
    public final String name;
    @NonNull
    public final String image;

    public Category(@NonNull String name, @NonNull String image) {
        this.name = name;
        this.image = image;
    }
}
