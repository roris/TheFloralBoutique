package com.example.floralboutique.ui.cart;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;

public class CartViewModelFactory implements ViewModelProvider.Factory {
    private final FloralBoutiqueRepository repository_;

    public CartViewModelFactory(FloralBoutiqueRepository repository) {
        this.repository_ = repository;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> klass) {
        return (T) new CartViewModel(repository_);
    }
}
