package com.example.floralboutique.util;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.floralboutique.R;

import java.util.Objects;

public class ViewBuilder {
    private ViewBuilder() {
    }

    static public <VH extends RecyclerView.ViewHolder, A extends RecyclerView.Adapter<VH>>
    RecyclerView buildRecyclerView(@NonNull View parent, @NonNull A adapter) {
        RecyclerView recyclerView = parent.findViewById(R.id.recycler_view);
        return buildRecyclerView(recyclerView, adapter);
    }

    static public <VH extends RecyclerView.ViewHolder, A extends RecyclerView.Adapter<VH>>
    RecyclerView buildRecyclerView(@NonNull AppCompatActivity parent, @NonNull A adapter) {
        RecyclerView recyclerView = parent.findViewById(R.id.recycler_view);
        return buildRecyclerView(recyclerView, adapter);
    }

    static private <VH extends RecyclerView.ViewHolder, A extends RecyclerView.Adapter<VH>>
    RecyclerView buildRecyclerView(@NonNull RecyclerView recyclerView, A adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(divider);
        return recyclerView;
    }

    @NonNull
    static public ActionBar buildToolbar(@NonNull AppCompatActivity parent, @NonNull String title, int iconResId) {
        ActionBar actionBar = buildToolbar(parent, title);
        actionBar.setHomeAsUpIndicator(iconResId);
        return actionBar;
    }

    @NonNull
    static public ActionBar buildToolbar(@NonNull AppCompatActivity parent, @NonNull String title) {
        // setup toolbar
        Toolbar toolbar = parent.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        parent.setSupportActionBar(toolbar);
        ActionBar actionBar = parent.getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        return actionBar;
    }


}
