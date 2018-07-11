package com.example.floralboutique.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Users")
public class User {
    public static final String USER_TYPE_ADMIN = "ADMIN";
    public static final String USER_TYPE_MEMBER = "MEMBER";
    @PrimaryKey
    @NonNull
    public final String username;
    @NonNull
    public final String password;
    @NonNull
    public final String userType;

    public User(@NonNull String username, @NonNull String password, @NonNull String userType) {
        this.username = username;
        this.userType = userType;
        this.password = password;
    }
}
