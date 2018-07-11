package com.example.floralboutique.ui.cart;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.example.floralboutique.data.FloralBoutiqueRepository;
import com.example.floralboutique.data.entity.CartItem;
import com.example.floralboutique.data.entity.User;

import java.util.List;

public class CartViewModel extends ViewModel {
    private final FloralBoutiqueRepository repository_;

    public CartViewModel(FloralBoutiqueRepository repository) {
        repository_ = repository;
    }

    /**
     * Get the details of all the items in the cart. Multiple records for the same item may be
     * retrieved since the method uses a LEFT JOIN to fetch data.
     *
     * @return LiveData object containing the list of current cart items
     */
    @AnyThread
    public LiveData<List<CartItemModel>> getCartItems() {
        return repository_.getAllCartItems();
    }

    /**
     * @return The LiveData object containing the current user.
     */
    @AnyThread
    @NonNull
    public LiveData<User> getCurrentUser() {
        return repository_.getCurrentUser();
    }

    /**
     * Place an order.
     *
     * @param items The items in the order.
     */
    @WorkerThread
    public void placeOrder(List<CartItemModel> items) {
        repository_.placeOrder(items);
    }

    /**
     * Remove an item from the cart.
     *
     * @param item The item to be removed from cart.
     */
    public void removeFromCart(CartItemModel item) {
        repository_.removeFromCart(item.flowerName);
    }

    public void updateQuantity(CartItemModel item, int quantity) {
        if (quantity > 0) {
            repository_.addToCart(new CartItem(item.flowerName, quantity));
        } else {
            repository_.removeFromCart(item.flowerName);
        }
    }

}
