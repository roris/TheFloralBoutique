package com.example.floralboutique.data.converter;

import android.arch.persistence.room.TypeConverter;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    private final static SimpleDateFormat formatter_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DateConverter() {
    }


    @TypeConverter
    public static String toString(Date date) {
        return formatter_.format(date);
    }

    @TypeConverter
    @Nullable
    public static Date toDate(String string) {
        try {
            return formatter_.parse(string);
        } catch (Exception ex) {
            return null;
        }
    }
}
