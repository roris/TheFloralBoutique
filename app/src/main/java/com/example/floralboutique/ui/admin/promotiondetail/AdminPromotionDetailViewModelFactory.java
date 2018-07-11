package com.example.floralboutique.ui.admin.promotiondetail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;

public class AdminPromotionDetailViewModelFactory implements ViewModelProvider.Factory {
    private final FloralBoutiqueRepository repository_;
    private final int id_;

    public AdminPromotionDetailViewModelFactory(FloralBoutiqueRepository repository, int id) {
        repository_ = repository;
        id_ = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AdminPromotionDetailViewModel(repository_, id_);
    }
}
