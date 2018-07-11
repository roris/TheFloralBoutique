package com.example.floralboutique.ui.member.flowerlist;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;

public class MemberFlowerListViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    private final String category_;
    private final FloralBoutiqueRepository repository_;

    public MemberFlowerListViewModelFactory(@NonNull FloralBoutiqueRepository repository, @NonNull String category) {
        category_ = category;
        repository_ = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MemberFlowerListViewModel(repository_, category_);
    }
}
