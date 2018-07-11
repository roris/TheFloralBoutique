package com.example.floralboutique.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Orders",
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "username", childColumns = "user")
        }
)
public class Order {
    public final String user;
    public final String status;
    public final Date date;
    @PrimaryKey(autoGenerate = true)
    public final int id;

    public Order(int id, String user, String status, Date date) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.date = date;
    }
}
