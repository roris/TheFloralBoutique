package com.example.floralboutique.util;

import android.text.Editable;
import android.text.TextWatcher;

public class QuantityWatcher implements TextWatcher {
    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.toString().length() > 3) {
            String substr = editable.toString().substring(0, 3);
            editable.replace(0, editable.length(), substr);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // do nothing
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // do nothing
    }
}
