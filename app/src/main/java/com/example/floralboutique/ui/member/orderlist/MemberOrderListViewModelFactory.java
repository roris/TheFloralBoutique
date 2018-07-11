package com.example.floralboutique.ui.member.orderlist;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;

public class MemberOrderListViewModelFactory implements ViewModelProvider.Factory {
    private final FloralBoutiqueRepository repository_;
    private final String member_;

    public MemberOrderListViewModelFactory(FloralBoutiqueRepository repository, String member) {
        repository_ = repository;
        member_ = member;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MemberOrderListViewModel(repository_, member_);
    }
}
