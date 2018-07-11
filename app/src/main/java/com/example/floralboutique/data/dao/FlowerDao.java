package com.example.floralboutique.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.WorkerThread;

import com.example.floralboutique.data.entity.Flower;
import com.example.floralboutique.ui.member.flowerdetail.MemberFlowerDetailItemModel;

import java.util.List;

@Dao
public interface FlowerDao {
    @Query("SELECT F.name AS name, " +
            "F.description AS description, " +
            "F.price AS price, " +
            "F.image AS image, " +
            "IFNULL(C.quantity, 0) AS quantity, " +
            "IFNULL(P.discountPercent, 1.0) AS discountPercent " +
            "FROM Flowers AS F " +
            "LEFT JOIN FlowersPromotions AS FP " +
            "ON FP.flower=F.name " +
            "LEFT JOIN Promotions AS P " +
            "ON P.id=FP.promotion " +
            "AND P.startDate <= datetime('now', 'start of day') " +
            "AND P.endDate > datetime('now', 'start of day') " +
            "LEFT JOIN Cart AS C " +
            "ON F.name=C.flower " +
            "WHERE F.name=:name ")
    public LiveData<List<MemberFlowerDetailItemModel>> getFlowerDetails(String name);

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertOrIgnore(Flower flower);

    @Query("SELECT * FROM Flowers")
    LiveData<List<Flower>> loadAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrReplace(Flower flower);
}
