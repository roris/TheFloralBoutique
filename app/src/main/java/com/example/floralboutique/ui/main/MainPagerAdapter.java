package com.example.floralboutique.ui.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.floralboutique.ui.admin.orderlist.AdminOrderListFragment;
import com.example.floralboutique.data.entity.User;
import com.example.floralboutique.ui.admin.categorylist.AdminCategoryListFragment;
import com.example.floralboutique.ui.admin.flowerlist.AdminFlowerListFragment;
import com.example.floralboutique.ui.admin.promotionlist.AdminPromotionListFragment;
import com.example.floralboutique.ui.cart.CartFragment;
import com.example.floralboutique.ui.member.MemberCategoryListFragment;
import com.example.floralboutique.ui.member.orderlist.MemberOrderListFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private final FragmentManager manager_;
    @Nullable
    private String member_;
    private String mode_;

    public MainPagerAdapter(FragmentManager manager, String mode) {
        super(manager);
        mode_ = mode;
        manager_ = manager;
    }

    public int getCount() {
        return mode_.equals(User.USER_TYPE_ADMIN) ? 4 : 3;
    }

    public void setMode(String mode) {
        mode_ = mode;
    }

    public void setMember(@Nullable String member) {
        member_ = member;
    }

    @Override
    public Fragment getItem(int i) {
        if (mode_.equals(User.USER_TYPE_ADMIN)) {
            if (i == 0) {
                return new AdminFlowerListFragment();
            } else if (i == 1) {
                return new AdminPromotionListFragment();
            } else if (i == 2) {
                return new AdminCategoryListFragment();
            } else if (i == 3) {
                return new AdminOrderListFragment();
            }
        }

        if (i == 0) {
            return new CartFragment();
        } else if (i == 1) {
            return new MemberCategoryListFragment();
        } else if (i == 2) {
            return MemberOrderListFragment.newInstance(member_);
        }

        return null;
    }
}
