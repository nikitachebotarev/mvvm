package ru.cnv.sample.ui.adapter.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<? extends RecyclerItem> items;
    private List<RecyclerHolder> holders;

    public void setItems(List<? extends RecyclerItem> items) {
        this.items = items;
    }

    public void setHolders(RecyclerHolder... holders) {
        this.holders = new ArrayList();
        this.holders.addAll(Arrays.asList(holders));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        for (RecyclerHolder holder : holders) {
            if (holder.getType() == viewType) {
                ViewGroup root = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(holder.getLayout(), parent, false);
                return holder.getViewHolder(root);
            }
        }
        throw new IllegalStateException("holder not found");
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        for (RecyclerHolder viewHolder : holders) {
            if (viewHolder.getType() == items.get(position).getType()) {
                viewHolder.bind(items.get(position));
            }
        }
    }
}
