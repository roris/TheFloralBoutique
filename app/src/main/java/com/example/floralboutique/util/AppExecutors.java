/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.floralboutique.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static AppExecutors instance_;
    public final MainThreadExecutor mainThread = new MainThreadExecutor();
    public final Executor diskIo;

    private AppExecutors(Executor diskIo) {
        this.diskIo = diskIo;
    }

    public static AppExecutors getInstance() {
        if (instance_ == null) {
            synchronized (AppExecutors.class) {
                if (instance_ == null) {
                    instance_ = new AppExecutors(Executors.newSingleThreadExecutor());
                }
            }
        }
        return instance_;
    }

    public static class MainThreadExecutor implements Executor {
        final Handler handler_ = new Handler(Looper.getMainLooper());

        private MainThreadExecutor() {
        }

        @Override
        public void execute(@NonNull Runnable task) {
            handler_.post(task);
        }
    }
}
