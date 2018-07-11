package com.example.floralboutique.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.floralboutique.data.entity.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOrIgnore(Category category);

    @Query("SELECT * FROM Categories")
    LiveData<List<Category>> loadAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrReplace(Category category);
}
