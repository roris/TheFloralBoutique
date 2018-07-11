package com.example.floralboutique.ui.common;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;

public class CategoryListViewModelFactory implements ViewModelProvider.Factory {
    private final FloralBoutiqueRepository repository_;

    public CategoryListViewModelFactory(FloralBoutiqueRepository repository) {
        repository_ = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CategoryListViewModel(repository_);
    }
}
