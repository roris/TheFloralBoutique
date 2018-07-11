package com.example.floralboutique.ui.member.flowerlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;

import java.util.List;

public class MemberFlowerListViewModel extends ViewModel {
    @NonNull
    private final String category_;
    @NonNull
    private final FloralBoutiqueRepository repository_;

    MemberFlowerListViewModel(@NonNull FloralBoutiqueRepository repository, @NonNull String category) {
        // Log.d("MemberFlowerListViewModel", category);
        category_ = category;
        repository_ = repository;
    }

    public LiveData<List<MemberFlowerListItemModel>> getFlowers() {
        return repository_.getFlowersForCategory(category_);
    }

}
