package com.example.floralboutique.ui.admin.flowerdetail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;

public class AdminFlowerDetailViewModelFactory implements ViewModelProvider.Factory {
    private final FloralBoutiqueRepository repository_;
    private final String flower_;

    public AdminFlowerDetailViewModelFactory(FloralBoutiqueRepository repository, String flower) {
        repository_ = repository;
        flower_ = flower;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AdminFlowerDetailViewModel(repository_, flower_);
    }
}
