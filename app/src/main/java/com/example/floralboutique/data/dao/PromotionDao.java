package com.example.floralboutique.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.support.annotation.WorkerThread;

import com.example.floralboutique.data.entity.FlowerPromotion;
import com.example.floralboutique.data.entity.Promotion;
import com.example.floralboutique.ui.admin.promotiondetail.CheckedFlower;

import java.util.Date;
import java.util.List;

@Dao
public abstract class PromotionDao {
    // startDate >= currentDate,
    // endDate >= currentDate
    @Query("SELECT id,startDate,endDate,discountPercent " +
            "FROM Promotions " +
            "INNER JOIN FlowersPromotions " +
            "ON Promotions.id=FlowersPromotions.promotion " +
            "WHERE " +
            "datetime(startDate) <= datetime(:now, 'start of day') AND " +
            "datetime(endDate) > datetime(:now, 'start of day') AND " +
            "FlowersPromotions.flower=:flower")
    public abstract LiveData<List<Promotion>> getCurrentPromotionsForFlower(String flower, Date now);

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertOrReplace(Promotion entity);

    @Query("SELECT * FROM Promotions " +
            "WHERE datetime(startDate) <= datetime(:now, 'start of day') " +
            "AND datetime(endDate) > datetime(:now, 'start of day')")
    public abstract LiveData<List<Promotion>> getCurrentPromotions(Date now);

    @Query("SELECT * FROM Promotions WHERE startDate=:startDate")
    public abstract Promotion getPromotionByStartDate(Date startDate);

    @WorkerThread
    @Transaction
    public void savePromotion(FlowerPromotionDao flowersPromotionsDao, Promotion promotion, List<CheckedFlower> flowers) {
        insertOrReplace(promotion);
        if (promotion.id == 0) {
            promotion = getPromotionByStartDate(promotion.startDate);
        }
        for (CheckedFlower flower : flowers) {
            FlowerPromotion flowerPromotion = new FlowerPromotion(flower.name, promotion.id);
            if (flower.checked) {
                flowersPromotionsDao.insertOrIgnore(new FlowerPromotion(flower.name, promotion.id));
            } else {
                flowersPromotionsDao.remove(flowerPromotion);
            }
        }
    }

    @Query("SELECT * FROM Promotions ORDER BY id DESC")
    public abstract LiveData<List<Promotion>> getAllPromotions();
}
