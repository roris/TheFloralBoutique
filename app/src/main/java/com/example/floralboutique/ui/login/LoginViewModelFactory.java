package com.example.floralboutique.ui.login;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;

public class LoginViewModelFactory implements ViewModelProvider.Factory {
    final FloralBoutiqueRepository repository_;

    public LoginViewModelFactory(FloralBoutiqueRepository repository) {
        repository_ = repository;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> klass) {
        return (T) new LoginViewModel(repository_);
    }
}
