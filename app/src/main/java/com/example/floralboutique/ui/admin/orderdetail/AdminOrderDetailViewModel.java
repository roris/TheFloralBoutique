package com.example.floralboutique.ui.admin.orderdetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.floralboutique.data.FloralBoutiqueRepository;
import com.example.floralboutique.ui.member.orderdetail.ListViewFlowerOrder;
import com.example.floralboutique.util.AppExecutors;

import java.util.List;

public class AdminOrderDetailViewModel extends ViewModel {
    private final AppExecutors executors_ = AppExecutors.getInstance();
    private final FloralBoutiqueRepository repository_;
    private final String status_;
    private final int id_;

    AdminOrderDetailViewModel(FloralBoutiqueRepository repository, String status, int id) {
        repository_ = repository;
        status_ = status;
        id_ = id;
    }

    public LiveData<List<ListViewFlowerOrder>> getFlowers() {
        return repository_.getFlowerOrders(id_);
    }

    public void updateStatus(String status) {
        if (!status.equals(status_)) {
            executors_.diskIo.execute(() -> repository_.updateOrderStatus(id_, status));

        }
    }

}
