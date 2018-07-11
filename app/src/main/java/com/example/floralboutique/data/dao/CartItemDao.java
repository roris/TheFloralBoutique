package com.example.floralboutique.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.annotation.WorkerThread;

import com.example.floralboutique.data.entity.CartItem;
import com.example.floralboutique.ui.cart.CartItemModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CartItemDao {
    @WorkerThread
    @Insert(onConflict = REPLACE)
    void insertOrReplace(CartItem item);

    @WorkerThread
    @Query("DELETE FROM Cart")
    void clearCart();

    @Query("SELECT " +
            "F.name AS flowerName, " +
            "F.image AS image, " +
            "F.price AS price, " +
            "IFNULL(P.discountPercent, 1) AS discountPercent, " +
            "C.quantity AS quantity " +
            "FROM Cart AS C " +
            "INNER JOIN Flowers AS F " +
            "ON C.flower=F.name " +
            "LEFT JOIN FlowersPromotions AS FP " +
            "ON F.name=FP.flower " +
            "LEFT JOIN Promotions AS P " +
            "ON FP.promotion=P.id " +
            "AND P.startDate <= datetime('now') " +
            "AND P.endDate > datetime('now')")
    LiveData<List<CartItemModel>> loadAll();


    @Delete
    void remove(CartItem cartItem);
}
