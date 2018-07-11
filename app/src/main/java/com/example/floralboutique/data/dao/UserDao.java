package com.example.floralboutique.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.annotation.WorkerThread;

import com.example.floralboutique.data.entity.User;

import static android.arch.persistence.room.OnConflictStrategy.FAIL;
import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface UserDao {
    @WorkerThread
    @Insert(onConflict = IGNORE)
    void insertOrIgnore(User user);

    @WorkerThread
    @Insert(onConflict = REPLACE)
    void insertOrReplace(User user);

    @WorkerThread
    @Insert(onConflict = FAIL)
    void register(User user);

    @WorkerThread
    @Query("SELECT 1 FROM Users WHERE username=:username")
    boolean exists(String username);

    @Query("SELECT * FROM Users WHERE username=:username AND password=:password")
    User login(String username, String password);
}
