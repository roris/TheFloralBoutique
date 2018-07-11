package com.example.floralboutique.ui.admin.flowerlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.floralboutique.data.FloralBoutiqueRepository;
import com.example.floralboutique.data.entity.Flower;

import java.util.List;

public class AdminFlowerListViewModel extends ViewModel {
    private final FloralBoutiqueRepository repository_;

    public AdminFlowerListViewModel(FloralBoutiqueRepository repository) {
        repository_ = repository;
    }

    public LiveData<List<Flower>> getFlowers() {
        return repository_.getAllFlowers();
    }


}
