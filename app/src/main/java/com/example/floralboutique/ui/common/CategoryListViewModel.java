package com.example.floralboutique.ui.common;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.WorkerThread;

import com.example.floralboutique.data.FloralBoutiqueRepository;
import com.example.floralboutique.data.entity.Category;

import java.util.List;

public class CategoryListViewModel extends ViewModel {
    final FloralBoutiqueRepository repository_;

    public CategoryListViewModel(FloralBoutiqueRepository repository) {
        repository_ = repository;
    }

    @WorkerThread
    public LiveData<List<Category>> getAllCategories() {
        return repository_.getAllCategories();
    }


}
