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

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.floralboutique.data.FloralBoutiqueRepository;
import com.example.floralboutique.data.FloralBoutiqueDatabase;
import com.example.floralboutique.ui.admin.categorydetail.AminCategoryDetailViewModelFactory;
import com.example.floralboutique.ui.admin.flowerlist.AdminFlowerListViewModelFactory;
import com.example.floralboutique.ui.admin.orderlist.AdminOrderListViewModelFactory;
import com.example.floralboutique.ui.admin.promotiondetail.AdminPromotionDetailViewModelFactory;
import com.example.floralboutique.ui.admin.promotionlist.AdminPromotionListViewModelFactory;
import com.example.floralboutique.ui.cart.CartViewModelFactory;
import com.example.floralboutique.ui.common.CategoryListViewModelFactory;
import com.example.floralboutique.ui.login.LoginViewModelFactory;
import com.example.floralboutique.ui.main.MainViewModelFactory;
import com.example.floralboutique.ui.member.flowerdetail.MemberFlowerDetailViewModelFactory;
import com.example.floralboutique.ui.member.flowerlist.MemberFlowerListViewModelFactory;
import com.example.floralboutique.ui.member.orderdetail.MemberOrderDetailViewModelFactory;
import com.example.floralboutique.ui.member.orderlist.MemberOrderListViewModelFactory;
import com.example.floralboutique.ui.admin.flowerdetail.AdminFlowerDetailViewModelFactory;
import com.example.floralboutique.ui.admin.orderdetail.AdminOrderDetailViewModelFactory;

public class AppComponents {
    private AppComponents() {
    }

    @NonNull
    public static FloralBoutiqueRepository getRepository(@NonNull Context context) {
        FloralBoutiqueDatabase database = FloralBoutiqueDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return FloralBoutiqueRepository.getInstance(executors, database);
    }

    @NonNull
    public static LoginViewModelFactory getLoginViewModelFactory(Context context) {
        return new LoginViewModelFactory(getRepository(context));
    }

    @NonNull
    public static MainViewModelFactory getMainViewModelFactory(Context context) {
        return new MainViewModelFactory(getRepository(context));
    }

    @NonNull
    public static CartViewModelFactory getCartViewModelFactory(Context context) {
        return new CartViewModelFactory(getRepository(context));
    }

    @NonNull
    public static CategoryListViewModelFactory getCategoryListViewModelFactory(Context context) {
        return new CategoryListViewModelFactory(getRepository(context));
    }

    @NonNull
    public static MemberFlowerListViewModelFactory getShopFlowerListViewModelFactory(Context context, String category) {
        return new MemberFlowerListViewModelFactory(getRepository(context), category);
    }

    @NonNull
    public static MemberFlowerDetailViewModelFactory getShopFlowerDetailViewModelFactory(Context context, String flower) {
        return new MemberFlowerDetailViewModelFactory(getRepository(context), flower);
    }

    @NonNull
    public static MemberOrderListViewModelFactory getMemberOrderViewModelFactory(Context context, String member) {
        return new MemberOrderListViewModelFactory(getRepository(context), member);
    }

    @NonNull
    public static MemberOrderDetailViewModelFactory getMemberOrderDetailViewModelFactory(Context context, int orderId) {
        return new MemberOrderDetailViewModelFactory(getRepository(context), orderId);
    }

    @NonNull
    public static AdminFlowerListViewModelFactory getManageFlowerListViewModelFactory(Context context) {
        return new AdminFlowerListViewModelFactory(getRepository(context));
    }

    @NonNull
    public static AdminFlowerDetailViewModelFactory getManageFlowerDetailViewModelFactory(Context context, String flower) {
        return new AdminFlowerDetailViewModelFactory(getRepository(context), flower);
    }

    @NonNull
    public static AminCategoryDetailViewModelFactory getAdminCategoryDetailViewModelFactory(Context applicationContext) {
        return new AminCategoryDetailViewModelFactory(getRepository(applicationContext));
    }

    @NonNull
    public static AdminOrderListViewModelFactory getAdminOrderListViewModelFactory(Context context) {
        return new AdminOrderListViewModelFactory(getRepository(context));
    }

    @NonNull
    public static AdminOrderDetailViewModelFactory getAdminOrderDetailViewModelFactory(Context context, String status, int id) {
        return new AdminOrderDetailViewModelFactory(getRepository(context), status, id);
    }

    @NonNull
    public static AdminPromotionListViewModelFactory getAdminPromotionListViewModelFactory(Context context) {
        return new AdminPromotionListViewModelFactory(getRepository(context));
    }

    @NonNull
    public static AdminPromotionDetailViewModelFactory getAdminPromotionDetailViewModelFactory(Context context, int id) {
        return new AdminPromotionDetailViewModelFactory(getRepository(context), id);
    }
}
