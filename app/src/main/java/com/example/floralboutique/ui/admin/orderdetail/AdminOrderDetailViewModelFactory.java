package com.example.floralboutique.ui.admin.orderdetail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;

public class AdminOrderDetailViewModelFactory implements ViewModelProvider.Factory {
    private final FloralBoutiqueRepository repository_;
    private final String status_;
    private final int id_;

    public AdminOrderDetailViewModelFactory(FloralBoutiqueRepository repository, String status, int id) {
        repository_ = repository;
        status_ = status;
        id_ = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AdminOrderDetailViewModel(repository_, status_, id_);
    }
}
