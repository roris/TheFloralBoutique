package com.example.floralboutique.ui.admin.promotiondetail;

import android.arch.lifecycle.ViewModel;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.floralboutique.data.FloralBoutiqueRepository;
import com.example.floralboutique.data.entity.Promotion;
import com.example.floralboutique.util.AppExecutors;
import com.example.floralboutique.util.AppFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

class AdminPromotionDetailViewModel extends ViewModel {
    private final FloralBoutiqueRepository repository_;
    private final AppExecutors executors_ = AppExecutors.getInstance();
    private final int id_;
    private List<CheckedFlower> flowers_;

    AdminPromotionDetailViewModel(FloralBoutiqueRepository repository, int id) {
        repository_ = repository;
        id_ = id;
    }

    public void getAllFlowersChecked(CheckedFlowerAdapter adapter, TextView notice) {
        executors_.diskIo.execute(() -> {
            List<CheckedFlower> flowers = repository_.getAllFlowersCheckedForPromotion(id_);
            if (flowers == null || flowers.size() == 0) {
                executors_.mainThread.execute(() -> notice.setVisibility(View.VISIBLE));
            }

            final TreeMap<String, CheckedFlower> map = new TreeMap<>();
            for (CheckedFlower flower : flowers) {
                CheckedFlower mapped = map.get(flower.name);

                if (mapped == null) {
                    mapped = flower;
                } else {
                    mapped = new CheckedFlower(flower.name,
                            flower.image,
                            flower.price,
                            flower.discountPercent * mapped.discountPercent,
                            flower.checked || mapped.checked);
                }
                map.put(flower.name, mapped);
            }
            updateFlowers(adapter, new ArrayList<>(map.values()), notice);
        });
    }

    private void updateFlowers(CheckedFlowerAdapter adapter, List<CheckedFlower> flowers, TextView notice) {
        executors_.mainThread.execute(() -> {
            adapter.swapItems(flowers);
            flowers_ = flowers;
            notice.setVisibility(View.GONE);
        });
    }

    public void save(AppCompatActivity activity, EditText editStartDate, EditText editEndDate, EditText editDiscountPercent) {
        final String strStartDate = editStartDate.getText().toString().trim();
        final String strEndDate = editEndDate.getText().toString().trim();
        final String strDiscountPercent = editDiscountPercent.getText().toString().replace('%', ' ').trim();
        Date startDate = null;
        Date endDate = null;
        float discountPercent = 0;
        boolean valid = true;

        if (editStartDate.getText().toString().trim().length() == 0) {
            editStartDate.setError("click to select start date");
            executors_.mainThread.execute(() -> Toast.makeText(activity, "please select a start date", Toast.LENGTH_SHORT).show());
            valid = false;
        } else {
            startDate = AppFormatter.parsePromotionDate(editStartDate.getText().toString().trim());
        }

        if (editEndDate.getText().toString().trim().length() == 0) {
            editEndDate.setError("click to select end date");
            valid = false;
            executors_.mainThread.execute(() -> Toast.makeText(activity, "please select an end date", Toast.LENGTH_SHORT).show());
        } else {
            endDate = AppFormatter.parsePromotionDate(editEndDate.getText().toString().trim());
        }

        if (endDate != null && startDate != null) {
            if (endDate.getTime() <= startDate.getTime()) {
                editEndDate.setError("end date should be after start date");
                executors_.mainThread.execute(() -> Toast.makeText(activity, "end date should be after start date", Toast.LENGTH_SHORT).show());
                valid = false;
            }
        }

        if (strDiscountPercent.length() == 0) {
            editDiscountPercent.setError("please enter the discount percent");
            valid = false;
        } else {
            try {
                discountPercent = Float.parseFloat(strDiscountPercent);
            } catch (NumberFormatException ex1) {
                try {
                    discountPercent = AppFormatter.parsePercentage(strDiscountPercent);
                } catch (Exception ex2){
                    valid = false;
                }
            }
        }

        discountPercent = 1 - (discountPercent / 100);

        if (!valid) {
            return;
        }

        final Promotion promotion = new Promotion(id_, startDate, endDate, discountPercent);

        executors_.diskIo.execute(() -> {
            try {
                repository_.savePromotion(promotion, flowers_);
                executors_.mainThread.execute(activity::finish);
            } catch (Exception ex) {
                executors_.mainThread.execute(() -> Toast.makeText(activity, "Failed to save data!", Toast.LENGTH_SHORT).show());
                // ex.printStackTrace();
            }
        });
    }

}
