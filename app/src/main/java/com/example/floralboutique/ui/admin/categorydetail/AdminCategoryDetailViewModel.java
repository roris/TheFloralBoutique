package com.example.floralboutique.ui.admin.categorydetail;

import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;
import com.example.floralboutique.util.AppExecutors;
import com.example.floralboutique.util.BitmapUtil;

public class AdminCategoryDetailViewModel extends ViewModel {
    private final AppExecutors executors_ = AppExecutors.getInstance();
    private final FloralBoutiqueRepository repository_;


    public AdminCategoryDetailViewModel(FloralBoutiqueRepository repository) {
        repository_ = repository;
    }


    @AnyThread
    public void save(@NonNull String category, @NonNull Bitmap bitmap, @NonNull String imageDest) {
        executors_.diskIo.execute(() -> {
            BitmapUtil.save(imageDest, bitmap);
            repository_.saveCategory(category, imageDest);
        });
    }

}
