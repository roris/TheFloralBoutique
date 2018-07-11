package com.example.floralboutique.ui.member.orderlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.floralboutique.data.FloralBoutiqueRepository;

import java.util.List;

public class MemberOrderListViewModel extends ViewModel {
    private final FloralBoutiqueRepository repository_;
    private final String member_;

    public MemberOrderListViewModel(FloralBoutiqueRepository repository, String member) {
        repository_ = repository;
        member_ = member;
    }

    public LiveData<List<MemberOrderListItemModel>> getOrders() {
        return repository_.getAllOrdersForMember(member_);
    }

}
