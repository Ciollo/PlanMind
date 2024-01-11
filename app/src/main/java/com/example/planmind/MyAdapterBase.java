package com.example.planmind;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class MyAdapterBase<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> mData;
    protected LayoutInflater mInflater;
    protected Context mContext;

    MyAdapterBase(Context context, List<T> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}