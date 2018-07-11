package com.example.floralboutique.ui.member.orderdetail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;

public class MemberOrderDetailViewModelFactory implements ViewModelProvider.Factory {
    private final FloralBoutiqueRepository repository_;
    private final int orderId_;

    public MemberOrderDetailViewModelFactory(FloralBoutiqueRepository repository, int orderId) {
        repository_ = repository;
        orderId_ = orderId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MemberOrderDetailViewModel(repository_, orderId_);
    }
}
