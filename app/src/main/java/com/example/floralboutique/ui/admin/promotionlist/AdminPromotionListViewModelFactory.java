package com.example.floralboutique.ui.admin.promotionlist;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;

public class AdminPromotionListViewModelFactory implements ViewModelProvider.Factory {
    private final FloralBoutiqueRepository repository_;

    public AdminPromotionListViewModelFactory(FloralBoutiqueRepository repository) {
        repository_ = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AdminPromotionListViewModel(repository_);
    }
}
