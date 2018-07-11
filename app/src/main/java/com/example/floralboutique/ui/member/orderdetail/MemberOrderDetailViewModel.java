package com.example.floralboutique.ui.member.orderdetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.WorkerThread;

import com.example.floralboutique.data.FloralBoutiqueRepository;

import java.util.List;

public class MemberOrderDetailViewModel extends ViewModel {
    private final FloralBoutiqueRepository repository_;
    private final int orderId_;

    public MemberOrderDetailViewModel(FloralBoutiqueRepository repository, int orderId) {
        repository_ = repository;
        orderId_ = orderId;
    }

    public LiveData<List<ListViewFlowerOrder>> getFlowers() {
        return repository_.getFlowerOrders(orderId_);
    }

    @WorkerThread
    public void updateOrderStatus(int id, String status) {
        repository_.updateOrderStatus(id, status);
    }

}
