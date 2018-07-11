package com.example.floralboutique.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.floralboutique.data.FloralBoutiqueRepository;
import com.example.floralboutique.data.entity.User;

public class MainViewModel extends ViewModel {
    private final FloralBoutiqueRepository repository_;

    public MainViewModel(FloralBoutiqueRepository repository) {
        repository_ = repository;
    }

    public LiveData<User> getCurrentUser() {
        return repository_.getCurrentUser();
    }

}
