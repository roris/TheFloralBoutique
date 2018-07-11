package com.example.floralboutique.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.support.annotation.WorkerThread;

import com.example.floralboutique.data.entity.FlowerOrder;
import com.example.floralboutique.data.entity.Order;
import com.example.floralboutique.ui.cart.CartItemModel;
import com.example.floralboutique.ui.member.orderlist.MemberOrderListItemModel;

import java.util.Date;
import java.util.List;
import java.util.Vector;

@Dao
public abstract class OrderDao {
    @WorkerThread
    @Insert
    public abstract void insert(Order order);

    @WorkerThread
    @Query("SELECT * FROM Orders WHERE user=:username and date=:date")
    public abstract Order findOrderByUsernameAndDate(String username, Date date);

    @WorkerThread
    @Transaction
    public void save(String username, List<CartItemModel> items, FlowerOrderDao flowerOrderDao, CartItemDao cartItemDao) {
        Date date = new Date();
        Order order = new Order(0, username, "Pending", date);
        insert(order);
        order = findOrderByUsernameAndDate(username, date);
        List<FlowerOrder> flowers = new Vector<>();

        for (CartItemModel item : items) {
            flowers.add(new FlowerOrder(item.flowerName, order.id, item.price * item.discountPercent, item.quantity));
        }

        flowerOrderDao.insert(flowers);
        cartItemDao.clearCart();
    }

    @Query("SELECT id,status,date FROM Orders where user=:member ORDER BY date DESC")
    public abstract LiveData<List<MemberOrderListItemModel>> getOrdersForMember(String member);

    @Query("SELECT id,status,date FROM Orders WHERE id=:id")
    public abstract LiveData<MemberOrderListItemModel> load(int id);

    @Query("UPDATE Orders SET status=:status WHERE id=:id")
    public abstract void updateStatus(int id, String status);

    @Query("SELECT * FROM Orders WHERE status <> 'Canceled'")
    public abstract LiveData<List<Order>> getAllUncancelledOrders();
}
