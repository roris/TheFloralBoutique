package com.example.floralboutique.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.WorkerThread;

import com.example.floralboutique.data.entity.FlowerPromotion;
import com.example.floralboutique.ui.admin.promotiondetail.CheckedFlower;

import java.util.List;

@Dao
public interface FlowerPromotionDao {
    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOrIgnore(FlowerPromotion flowerPromotion);

    @Query("SELECT " +
            "F.name AS name, " +
            "F.price AS price, " +
            "F.image AS image, " +
            "IFNULL(P.discountPercent, 1.0) AS discountPercent, " +
            "CASE " +
            "   WHEN P.id IS NOT NULL AND P.id=:id THEN 1 " +
            "   ELSE 0 " +
            "END AS checked " +
            "FROM Flowers AS F " +
            "LEFT JOIN FlowersPromotions AS FP " +
            "ON F.name=FP.flower " +
            "LEFT JOIN Promotions AS P " +
            "ON P.id=FP.promotion " +
            "AND P.startDate <= datetime('now') " +
            "AND P.endDate > datetime('now') ")
    List<CheckedFlower> getAllFlowersCheckedForPromotion(int id);

    @Delete
    void remove(FlowerPromotion flowerPromotion);
}
