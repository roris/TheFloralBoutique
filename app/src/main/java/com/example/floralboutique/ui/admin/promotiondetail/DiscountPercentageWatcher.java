package com.example.floralboutique.ui.admin.promotiondetail;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.floralboutique.util.AppFormatter;

import java.text.ParseException;

public class DiscountPercentageWatcher implements TextWatcher {


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString().trim();
        String text2 = text;
        float percent;
        if (text.length() != 0) {
            try {
                percent = AppFormatter.parsePercentage(text2);
            } catch (ParseException ex1) {
                try {
                    percent = Float.parseFloat(text) / 100;
                } catch (NumberFormatException ex2) {
                    editable.clear();
                    return;
                }
            }

            if (percent > 1) {
                text2 = AppFormatter.formatDiscountPercent(1);
            }

            if (!text.equals(text2)) {
                editable.replace(0, editable.length(), text2);
            }
        }
    }
}
