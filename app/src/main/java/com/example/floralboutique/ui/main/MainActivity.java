package com.example.floralboutique.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.floralboutique.R;
import com.example.floralboutique.data.entity.User;
import com.example.floralboutique.ui.login.LoginActivity;
import com.example.floralboutique.util.AppComponents;
import com.example.floralboutique.util.ViewBuilder;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout_;
    private String userType_ = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup viewmodel
        MainViewModelFactory factory = AppComponents.getMainViewModelFactory(this.getApplicationContext());
        MainViewModel viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);

        // setup navigation view
        drawerLayout_ = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((menuItem) -> {
            drawerLayout_.closeDrawers();

            if (menuItem.getItemId() == R.id.nav_login_register) {
                Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginActivity);
            } else if (menuItem.getItemId() == R.id.nav_logout) {
                AppComponents.getRepository(this.getApplicationContext()).logout();
            }

            return true;
        });

        // setup toolbar
        ViewBuilder.buildToolbar(this, "The Floral Boutique", R.drawable.ic_menu_white_24dp);

        // setup tabs
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager pager = findViewById(R.id.view_pager);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), userType_);
        pager.setAdapter(adapter);
        createMemberTabs(tabLayout, pager, adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // handle login/logout
        TextView textUsername = navigationView.getHeaderView(0).findViewById(R.id.text_current_username);
        viewModel.getCurrentUser().observe(this,
                user -> onCurrentUserChanged(user, tabLayout, pager, adapter, textUsername));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout_.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout_.isDrawerOpen(GravityCompat.START)) {
            drawerLayout_.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void resetPager(@NonNull ViewPager pager, @NonNull MainPagerAdapter adapter, @NonNull String userType) {
        pager.setAdapter(null);
        adapter.setMode(userType);
        pager.setAdapter(adapter);
    }

    @MainThread
    private void createMemberTabs(@NonNull TabLayout tabLayout, @NonNull ViewPager pager, @NonNull MainPagerAdapter adapter) {
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_cart));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_browse));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_orders));
        resetPager(pager, adapter, User.USER_TYPE_MEMBER);
    }

    @MainThread
    private void createAdminTabs(@NonNull TabLayout tabLayout, @NonNull ViewPager pager, @NonNull MainPagerAdapter adapter) {
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_flowers));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_promotions));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_categories));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_orders));
        resetPager(pager, adapter, User.USER_TYPE_ADMIN);
    }

    private void onCurrentUserChanged(User user, TabLayout tabLayout, ViewPager pager, MainPagerAdapter adapter, TextView textUsername) {
        if (user == null) {
            adapter.setMember("");
            textUsername.setText("");
            if (!userType_.equals(User.USER_TYPE_MEMBER)) {
                createMemberTabs(tabLayout, pager, adapter);
            }
            return;
        }

        adapter.setMember(user.username);
        textUsername.setText(user.username);

        if (user.userType.equals(User.USER_TYPE_ADMIN)) {
            createAdminTabs(tabLayout, pager, adapter);
        } else /* if (user.getUserType().equals(User.USER_TYPE_MEMBER)) */ {
            createMemberTabs(tabLayout, pager, adapter);
        }
    }
}
