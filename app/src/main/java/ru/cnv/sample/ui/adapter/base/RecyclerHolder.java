package ru.cnv.sample.ui.adapter.base;

import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

public abstract class RecyclerHolder<T extends RecyclerItem> {

    public abstract @LayoutRes int getLayout();

    public abstract int getType();

    public abstract void bind(T item);

    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent) {
        ButterKnife.bind(this, parent);
        return new RecyclerView.ViewHolder(parent) {};
    }
}
