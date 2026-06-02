package com.woo.netlimit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LimitTileAdapter extends RecyclerView.Adapter<LimitTileViewHolder> {

    public interface OnTileClickListener {
        void onTileClick(int value);
    }

    private final Context context;
    private int activeValue;
    private final OnTileClickListener listener;

    public LimitTileAdapter(Context context, int activeValue, OnTileClickListener listener) {
        this.context = context;
        this.activeValue = activeValue;
        this.listener = listener;
    }

    public void setActiveValue(int value) {
        int oldIndex = indexOf(activeValue);
        int newIndex = indexOf(value);
        activeValue = value;
        if (oldIndex >= 0) notifyItemChanged(oldIndex);
        if (newIndex >= 0) notifyItemChanged(newIndex);
    }

    private int indexOf(int value) {
        for (int i = 0; i < MainActivity.LIMIT_VALUES.length; i++) {
            if (MainActivity.LIMIT_VALUES[i] == value) return i;
        }
        return -1;
    }

    @NonNull
    @Override
    public LimitTileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_tile, parent, false);
        return new LimitTileViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LimitTileViewHolder holder, int position) {
        int value = MainActivity.LIMIT_VALUES[position];
        boolean isActive = value == activeValue;
        holder.bind(
            MainActivity.LIMIT_LABELS[position],
            MainActivity.LIMIT_SUBLABELS[position],
            isActive,
            () -> listener.onTileClick(value)
        );
    }

    @Override
    public int getItemCount() {
        return MainActivity.LIMIT_VALUES.length;
    }
}
