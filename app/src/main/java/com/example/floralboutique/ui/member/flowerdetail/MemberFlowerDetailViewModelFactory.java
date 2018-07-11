package com.example.floralboutique.ui.member.flowerdetail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;

public class MemberFlowerDetailViewModelFactory implements ViewModelProvider.Factory {
    private final FloralBoutiqueRepository repository_;
    private final String flower_;

    public MemberFlowerDetailViewModelFactory(FloralBoutiqueRepository repository, String flower) {
        repository_ = repository;
        this.flower_ = flower;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MemberFlowerDetailViewModel(repository_, flower_);
    }
}
