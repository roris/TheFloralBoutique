package com.example.floralboutique.ui.admin.promotionlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.floralboutique.data.FloralBoutiqueRepository;
import com.example.floralboutique.data.entity.Promotion;

import java.util.List;

public class AdminPromotionListViewModel extends ViewModel {
    private final FloralBoutiqueRepository repository_;

    public AdminPromotionListViewModel(FloralBoutiqueRepository repository) {
        repository_ = repository;
    }

    public LiveData<List<Promotion>> getAllPromotions() {
        return repository_.getAllPromotions();
    }

}
