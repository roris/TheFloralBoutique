package com.example.floralboutique.ui.login;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.WorkerThread;

import com.example.floralboutique.data.FloralBoutiqueRepository;

public class LoginViewModel extends ViewModel {
    private final FloralBoutiqueRepository repository_;

    public LoginViewModel(FloralBoutiqueRepository repository) {
        repository_ = repository;
    }

    @WorkerThread
    public boolean login(String username, String password) {
        return repository_.login(username, password);
    }

    @WorkerThread
    public boolean register(String username, String password) {
        return repository_.register(username, password);
    }

}
