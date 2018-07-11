package com.example.floralboutique.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.example.floralboutique.data.entity.CartItem;
import com.example.floralboutique.data.entity.Category;
import com.example.floralboutique.data.entity.Flower;
import com.example.floralboutique.data.entity.FlowerCategory;
import com.example.floralboutique.data.entity.Order;
import com.example.floralboutique.data.entity.Promotion;
import com.example.floralboutique.data.entity.User;
import com.example.floralboutique.ui.admin.flowerdetail.CheckedCategory;
import com.example.floralboutique.ui.admin.promotiondetail.CheckedFlower;
import com.example.floralboutique.ui.cart.CartItemModel;
import com.example.floralboutique.ui.member.flowerdetail.MemberFlowerDetailItemModel;
import com.example.floralboutique.ui.member.flowerlist.MemberFlowerListItemModel;
import com.example.floralboutique.ui.member.orderdetail.ListViewFlowerOrder;
import com.example.floralboutique.ui.member.orderlist.MemberOrderListItemModel;
import com.example.floralboutique.util.AppExecutors;

import java.util.Date;
import java.util.List;

public class FloralBoutiqueRepository {
    private static FloralBoutiqueRepository instance_;
    private final AppExecutors executors_;
    private final FloralBoutiqueDatabase database_;
    private final MutableLiveData<User> currentUser_;

    private FloralBoutiqueRepository(AppExecutors executors, FloralBoutiqueDatabase database) {
        executors_ = executors;
        database_ = database;
        currentUser_ = new MutableLiveData<>();
        executors_.diskIo.execute(() -> {
            if (!database_.userDao().exists("admin")) {
                database_.populateDatabase();
            }
        });
    }

    public static FloralBoutiqueRepository getInstance(AppExecutors executors, FloralBoutiqueDatabase database) {
        if (instance_ == null) {
            synchronized (FloralBoutiqueRepository.class) {
                if (instance_ == null) {
                    instance_ = new FloralBoutiqueRepository(executors, database);
                }
            }
        }
        return instance_;
    }

    @WorkerThread
    public boolean register(String username, String password) {
        User user = new User(username, password, User.USER_TYPE_MEMBER);
        try {
            database_.userDao().register(user);
            user = database_.userDao().login(username, password);
            currentUser_.postValue(user);
            return (user != null);
        } catch (Exception e) {
            // currentUser_.postValue(null);
            return false;
        }
    }

    @WorkerThread
    public boolean login(String username, String password) {
        User user = database_.userDao().login(username, password);
        currentUser_.postValue(user);
        return user != null;
    }

    @AnyThread
    public void logout() {
        currentUser_.postValue(null);
    }

    @NonNull
    @AnyThread
    public LiveData<User> getCurrentUser() {
        return currentUser_;
    }

    public LiveData<List<CartItemModel>> getAllCartItems() {
        return database_.cartItemDao().loadAll();
    }

    @AnyThread
    public void addToCart(CartItem item) {
        executors_.diskIo.execute(() -> database_.cartItemDao().insertOrReplace(item));
    }

    @AnyThread
    public void removeFromCart(String flower) {
        executors_.diskIo.execute(() -> database_.cartItemDao().remove(new CartItem(flower, 0)));
    }

    @WorkerThread
    public void placeOrder(@NonNull List<CartItemModel> items) {
        User user = currentUser_.getValue();

        if (items.size() == 0 || user == null) {
            return;
        }

        try {
            database_.orderDao().save(user.username, items, database_.flowerOrderDao(), database_.cartItemDao());
        } catch (Exception ex) {
            // TODO: use a callback to display an error message in the UI
        }
    }

    @AnyThread
    public LiveData<List<MemberFlowerDetailItemModel>> getFlowerDetails(String name) {
        return database_.flowerDao().getFlowerDetails(name);
    }

    @AnyThread
    public LiveData<List<Promotion>> getCurrentPromotionsForFlower(String name, Date now) {
        return database_.promotionDao().getCurrentPromotionsForFlower(name, now);
    }

    @AnyThread
    public LiveData<List<Category>> getAllCategories() {
        return database_.categoryDao().loadAll();
    }

    @AnyThread
    public LiveData<List<MemberFlowerListItemModel>> getFlowersForCategory(String category) {
        return database_.flowerCategoryDao().getFlowersForCategory(category);
    }

    public LiveData<Flower> getFlower(String name) {
        return new MutableLiveData<>();
    }

    public LiveData<List<MemberOrderListItemModel>> getAllOrdersForMember(String member) {
        return database_.orderDao().getOrdersForMember(member);
    }

    public LiveData<MemberOrderListItemModel> getMemberOrder(int orderId) {
        return database_.orderDao().load(orderId);
    }

    public LiveData<List<ListViewFlowerOrder>> getFlowerOrders(int orderId) {
        return database_.flowerOrderDao().getFlowersForOrder(orderId);
    }

    @WorkerThread
    public void updateOrderStatus(int id, String status) {
        database_.orderDao().updateStatus(id, status);
    }

    public LiveData<List<Flower>> getAllFlowers() {
        return database_.flowerDao().loadAll();
    }

    @WorkerThread
    public List<CheckedCategory> getAllCategoriesCheckedForFlower(String flower) {
        return database_.flowerCategoryDao().getAllCategoriesCheckedForFlower(flower);
    }

    @WorkerThread
    public void saveFlower(Flower flower) {
        database_.flowerDao().insertOrReplace(flower);
    }

    @WorkerThread
    public void addFlowerToCategory(String flower, String category) {
        database_.flowerCategoryDao().insertOrIgnore(new FlowerCategory(flower, category));
    }

    @WorkerThread
    public void removeFlowerFromCategory(String flower, String category) {
        database_.flowerCategoryDao().delete(new FlowerCategory(flower, category));
    }

    @WorkerThread
    public void saveCategory(String name, String image) {
        database_.categoryDao().insertOrReplace(new Category(name, image));
    }

    public LiveData<List<Order>> getAllUncancelledOrders() {
        return database_.orderDao().getAllUncancelledOrders();
    }

    @WorkerThread
    public List<CheckedFlower> getAllFlowersCheckedForPromotion(int id) {
        return database_.flowerPromotionDao().getAllFlowersCheckedForPromotion(id);
    }

    public LiveData<List<Promotion>> getCurrentPromotions() {
        return database_.promotionDao().getCurrentPromotions(new Date());
    }

    public void savePromotion(Promotion promotion, List<CheckedFlower> flowers) {
        database_.promotionDao().savePromotion(database_.flowerPromotionDao(), promotion, flowers);
    }

    public LiveData<List<Promotion>> getAllPromotions() {
        return database_.promotionDao().getAllPromotions();
    }
}
