package com.example.floralboutique.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppFormatter {
    private static final SimpleDateFormat promotionDateFormat_ = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat orderDateFormat_ = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final NumberFormat percentageFormatter = NumberFormat.getPercentInstance();

    private AppFormatter() {
    }

    @NonNull
    public static String formatPrice(float price) {
        return NumberFormat.getCurrencyInstance().format(price);
    }

    @NonNull
    public static String formatInCartQuantity(int quantity) {
        if (quantity == 0) {
            return "";
        }
        return String.format("%d in cart", quantity);
    }

    @NonNull
    public static String formatPercentOff(float discountPercent) {
        return percentageFormatter.format(1 - discountPercent) + " off";
    }

    @NonNull
    public static String formatDiscountPrice(float price, float discountPercent) {
        price *= discountPercent;
        return formatPrice(price);
    }

    @NonNull
    public static String formatQuantity(int quantity) {
        return String.format("%d", quantity);
    }

    @NonNull
    public static String formatOrderDate(Date date) {
        return "Ordered on " + orderDateFormat_.format(date);
    }

    @NonNull
    public static String formatId(int id) {
        return String.format("# %08d", id);
    }

    @NonNull
    public static String formatOrderQuantity(int quantity) {
        return String.format("%d pcs", quantity);
    }

    @NonNull
    public static String formatDiscountPercent(float percent) {
        return percentageFormatter.format(percent);
    }

    @NonNull
    public static String formatPromotionDate(Date date) {
        return promotionDateFormat_.format(date);
    }

    @Nullable
    public static Date parsePromotionDate(String date) {
        try {
            return promotionDateFormat_.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    @NonNull
    public static String formatPeriod(Date startDate, Date endDate) {
        return "From " + promotionDateFormat_.format(startDate) + " to " + promotionDateFormat_.format(endDate);
    }

    public static float parsePercentage(String discountPercent) throws ParseException {
        return percentageFormatter.parse(discountPercent).floatValue();
    }
}
