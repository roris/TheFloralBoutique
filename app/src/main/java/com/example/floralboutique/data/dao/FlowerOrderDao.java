package com.example.floralboutique.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.floralboutique.data.entity.FlowerOrder;
import com.example.floralboutique.ui.member.orderdetail.ListViewFlowerOrder;

import java.util.List;

@Dao
public interface FlowerOrderDao {
    @Insert
    void insert(List<FlowerOrder> orders);

    @Query("SELECT F.name, " +
            "FO.price, " +
            "FO.quantity, " +
            "F.image " +
            "FROM FlowersOrders AS FO " +
            "INNER JOIN Flowers AS F " +
            "ON F.name=FO.flower " +
            "WHERE FO.`order`=:orderId")
    LiveData<List<ListViewFlowerOrder>> getFlowersForOrder(int orderId);
}
