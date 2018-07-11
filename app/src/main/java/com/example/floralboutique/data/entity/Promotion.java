package com.example.floralboutique.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Promotions")
public class Promotion {
    @PrimaryKey(autoGenerate = true)
    public final int id;
    public final Date startDate;
    public final Date endDate;
    public final float discountPercent;

    public Promotion(int id, Date startDate, Date endDate, float discountPercent) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountPercent = discountPercent;
    }
}
