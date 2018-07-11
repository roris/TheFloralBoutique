package com.example.floralboutique.ui.admin.flowerdetail;

import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.example.floralboutique.data.FloralBoutiqueRepository;
import com.example.floralboutique.util.AppExecutors;
import com.example.floralboutique.util.BitmapUtil;
import com.example.floralboutique.data.entity.Flower;

import java.util.List;

public class AdminFlowerDetailViewModel extends ViewModel {
    private final AppExecutors executors_ = AppExecutors.getInstance();
    private final FloralBoutiqueRepository repository_;
    private final String flower_;

    AdminFlowerDetailViewModel(FloralBoutiqueRepository repository, String flower) {
        repository_ = repository;
        flower_ = flower;
    }

    public List<CheckedCategory> getCategories() {
        return repository_.getAllCategoriesCheckedForFlower(flower_);
    }

    public void save(String name,
                     String description,
                     float price,
                     String imagePath,
                     Bitmap image,
                     List<CheckedCategory> categories) {
        executors_.diskIo.execute(() -> {
            BitmapUtil.save(imagePath, image);
            repository_.saveFlower(new Flower(name, description, price, imagePath));
            for (CheckedCategory category : categories) {
                if (category.checked) {
                    repository_.addFlowerToCategory(name, category.category);
                } else {
                    repository_.removeFlowerFromCategory(name, category.category);
                }
            }
        });
    }

}
