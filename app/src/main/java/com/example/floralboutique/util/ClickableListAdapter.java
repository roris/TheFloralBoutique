package com.example.floralboutique.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public abstract class ClickableListAdapter<M, VH extends RecyclerView.ViewHolder> extends GenericListAdapter<M, VH> {
    @NonNull
    protected final OnItemClickHandler<M> onItemClickHandler_;

    protected ClickableListAdapter(@NonNull OnItemClickHandler<M> onItemClickHandler) {
        onItemClickHandler_ = onItemClickHandler;
    }

    public interface OnItemClickHandler<M> {
        void onItemClick(@NonNull M item);
    }
}
