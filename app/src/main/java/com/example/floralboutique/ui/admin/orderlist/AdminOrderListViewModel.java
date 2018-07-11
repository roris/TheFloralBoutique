package com.example.floralboutique.ui.admin.orderlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.floralboutique.data.FloralBoutiqueRepository;
import com.example.floralboutique.data.entity.Order;

import java.util.List;

public class AdminOrderListViewModel extends ViewModel {
    private final FloralBoutiqueRepository repository_;

    public AdminOrderListViewModel(FloralBoutiqueRepository repository) {
        repository_ = repository;
    }

    public LiveData<List<Order>> getOrders() {
        return repository_.getAllUncancelledOrders();
    }

}
