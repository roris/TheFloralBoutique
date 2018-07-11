package com.example.floralboutique.ui.member.flowerdetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;
import com.example.floralboutique.data.entity.CartItem;

import java.util.List;

public class MemberFlowerDetailViewModel extends ViewModel {
    @NonNull
    private final FloralBoutiqueRepository repository_;
    @NonNull
    private final String flower_;

    public MemberFlowerDetailViewModel(@NonNull FloralBoutiqueRepository repository, @NonNull String flower) {
        repository_ = repository;
        flower_ = flower;
    }

    public LiveData<List<MemberFlowerDetailItemModel>> getFlowerDetails() {
        return repository_.getFlowerDetails(flower_);
    }

    @AnyThread
    public void updateQuantity(int quantity) {
        if (quantity > 0) {
            repository_.addToCart(new CartItem(flower_, quantity));
        } else {
            repository_.removeFromCart(flower_);
        }
    }

}
