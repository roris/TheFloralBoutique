package com.example.floralboutique.util;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class GenericListAdapter<M, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<M> items_;

    @Override
    public int getItemCount() {
        return items_ == null ? 0 : items_.size();
    }

    @MainThread
    public void clearItems() {
        if (!(items_ == null || items_.size() == 0)) {
            items_.clear();
            notifyDataSetChanged();
        }
    }

    @MainThread
    public void swapItems(@NonNull List<M> items) {
        items_ = items;
        notifyDataSetChanged();
    }
}
