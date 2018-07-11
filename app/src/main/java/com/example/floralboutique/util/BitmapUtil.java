package com.example.floralboutique.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {
    @Nullable
    public static Bitmap load(String filename) {
        return load(new File(filename));
    }

    @Nullable
    public static Bitmap load(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            Bitmap res = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @MainThread
    public static void load(ImageView view, String path) {
        AppExecutors.getInstance().diskIo.execute(() -> {
            Bitmap bmp = load(path);
            AppExecutors.getInstance().mainThread.execute(() -> {
                view.setImageBitmap(bmp);
            });
        });
    }

    public static void save(String path, Bitmap bitmap) {
        save(new File(path), bitmap);
    }

    public static void save(File file, Bitmap bitmap) {
        try {
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
