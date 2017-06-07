package com.codbking.refreshlistview.example.listview.morepage;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codbking.refreshlistview.example.R;
import com.codbking.refreshlistview.example.listview.morepage.contact.MoreContact;
import com.codbking.refreshlistview.example.listview.utils.UI;


/**
 * Created by wulang on 2017/5/15.
 */

public class WrapperMorePageRecyclerAdapter<T extends RecyclerView.Adapter> extends RecyclerView.Adapter implements MorePageListAdapter<T> {

    private static final String TAG = "WrapperMorePageRecycler";
    private T mAdapter;

    private int status;


    private static final int TYPE_MORE = 2;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_MORE) {
            ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.view_load_more, null);
            view.getChildAt(0).setLayoutParams(new LinearLayout.LayoutParams(parent.getMeasuredWidth(), ViewGroup.LayoutParams.MATCH_PARENT));
            return new MoreViewHolder(view);
        }
        return getAdapter().onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MoreViewHolder) {
            MoreViewHolder mMoreViewHolder = (MoreViewHolder) holder;
            mMoreViewHolder.load(status);
        } else {
            getAdapter().onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (status != MoreContact.Present.STATE_NONE && position == (getItemCount() - 1)) {
            return TYPE_MORE;
        }
        return getAdapter().getItemViewType(position);
    }

    @Override
    public void setAdapter(T t) {
        mAdapter = t;
    }

    @Override
    public T getAdapter() {
        return mAdapter;
    }

    @Override
    public int getItemCount() {
        if (status != MoreContact.Present.STATE_NONE) {
            return getRealCount() + 1;
        }
        return getRealCount();
    }

    @Override
    public int getRealCount() {
        return mAdapter.getItemCount();
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }


    @Override
    public void setStatus(int status) {
        this.status = status;
        notifyDataSetChanged();
    }


    public static class MoreViewHolder extends RecyclerView.ViewHolder {

        TextView textTv;
        ContentLoadingProgressBar progressBar;

        public MoreViewHolder(View itemView) {
            super(itemView);
            textTv = (TextView) itemView.findViewById(R.id.textTv);
            progressBar = (ContentLoadingProgressBar) itemView.findViewById(R.id.progressBar);
        }

        public void load(int status) {

            switch (status) {
                case MoreContact.Present.STATE_FINISH:
                    UI.setVisibility(progressBar, false);
                    textTv.setText("数据加载完成");
                    break;
                case MoreContact.Present.STATE_ERROR:
                    UI.setVisibility(progressBar, false);
                    textTv.setText("数据加载失败");
                    break;
                case MoreContact.Present.STATE_LOAD:
                    UI.setVisibility(progressBar, true);
                    textTv.setText("数据加载中...");
                    break;
            }


        }
    }


    @Override
    public void setHasStableIds(boolean hasStableIds) {
        getAdapter().setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        return getAdapter().getItemId(position);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        getAdapter().onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        return getAdapter().onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        getAdapter().onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        getAdapter().onViewDetachedFromWindow(holder);
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        getAdapter().registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        getAdapter().unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        getAdapter().onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        getAdapter().onDetachedFromRecyclerView(recyclerView);
    }


    @Override
    public int hashCode() {
        return getAdapter().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return getAdapter().equals(obj);
    }

    @Override
    public String toString() {
        return getAdapter().toString();
    }

}
