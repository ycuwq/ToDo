package com.ycuwq.todo.common.recycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 多选模式
 * Created by yangchen on 2017/3/28.
 */
public abstract class ExMultiselectRecyclerAdapter<T> extends RecyclerView.Adapter<ExRecyclerViewHolder>  {
    private List<T> mList;
    private Context mContext;
    private int layoutRes;

    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();

    public ExMultiselectRecyclerAdapter(Context mContext, @LayoutRes int layoutRes) {
        this.mContext = mContext;
        this.layoutRes = layoutRes;
    }

    public void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    public boolean getItemChecked(int position) {
        return mSelectedPositions.get(position);
    }

    public void setList(List<T> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public List<T> getSelectedList() {

        List<T> selectedList = new ArrayList<>();

        for (int i = 0; i < mList.size(); i++) {
            if (getItemChecked(i)) {
                selectedList.add(mList.get(i));
            }
        }
        return selectedList;
    }

    public void cleanSelected() {
        mSelectedPositions.clear();
    }


    public void addAllData(List<T> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ExRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layoutRes, null);
        return new ExRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExRecyclerViewHolder holder, int position) {
        bindData(holder, mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public abstract void bindData(ExRecyclerViewHolder holder, T t, int position);

}
