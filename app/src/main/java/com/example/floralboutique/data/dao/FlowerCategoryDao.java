package com.example.floralboutique.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.floralboutique.data.entity.FlowerCategory;
import com.example.floralboutique.ui.admin.flowerdetail.CheckedCategory;
import com.example.floralboutique.ui.member.flowerlist.MemberFlowerListItemModel;

import java.util.List;

@Dao
public interface FlowerCategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertOrIgnore(FlowerCategory flowerCategory);

    @Query("SELECT " +
            "Flowers.name as name," +
            "Flowers.image as image, " +
            "Flowers.price as price, " +
            "IFNULL(Promotions.discountPercent, 1.0) as discountPercent," +
            "IFNULL(Cart.quantity, 0) as quantity " +
            "FROM Flowers " +
            "INNER JOIN FlowersCategories " +
            "ON Flowers.name=FlowersCategories.flower " +
            "LEFT JOIN FlowersPromotions " +
            "ON Flowers.name=FlowersPromotions.flower " +
            "LEFT JOIN Promotions " +
            "ON Promotions.id=FlowersPromotions.promotion " +
            "AND Promotions.startDate <= datetime('now', 'start of day') " +
            "AND Promotions.endDate > datetime('now', 'start of day') " +
            "LEFT JOIN Cart " +
            "ON Cart.flower=Flowers.name " +
            "WHERE FlowersCategories.category=:category ")
    LiveData<List<MemberFlowerListItemModel>> getFlowersForCategory(String category);

    @Query("SELECT " +
            "C.name AS category, " +
            "CASE WHEN F.name IS NULL THEN 0 ELSE 1 END AS checked " +
            "FROM Categories AS C " +
            "LEFT JOIN FlowersCategories AS FC " +
            "ON C.name=FC.category " +
            "LEFT JOIN Flowers AS F " +
            "ON FC.flower=F.name " +
            "AND F.name=:flower")
    List<CheckedCategory> getAllCategoriesCheckedForFlower(String flower);

    @Delete
    void delete(FlowerCategory flowerCategory);
}
