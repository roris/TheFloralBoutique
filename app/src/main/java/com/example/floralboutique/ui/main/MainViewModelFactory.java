package com.example.floralboutique.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final FloralBoutiqueRepository repository_;

    public MainViewModelFactory(FloralBoutiqueRepository repository) {
        repository_ = repository;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> klass) {
        return (T) new MainViewModel(repository_);
    }
}
