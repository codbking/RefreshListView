package com.codbking.refreshlistview.example.listview.morepage;

import android.database.DataSetObserver;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.codbking.refreshlistview.example.R;
import com.codbking.refreshlistview.example.listview.morepage.contact.MoreContact;
import com.codbking.refreshlistview.example.listview.utils.UI;
import com.codbking.refreshlistview.example.listview.utils.ViewHolder;


/**
 * Created by wulang on 2017/5/15.
 */

public class WrapperMorePageListAdapter<T extends ListAdapter> extends BaseAdapter implements MorePageListAdapter<T> {

    private T adapter;
    private static final String TAG = "WrapperMorePageListAdapter";
    private int status;

    @Override
    public Object getItem(int position) {
        return adapter.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return adapter.getItemId(position);
    }

    @Override
    public boolean hasStableIds() {
        return adapter.hasStableIds();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);

        if (type == getViewTypeCount() - 1) {
            return getMoreView(convertView, parent);
        }
        return adapter.getView(position, convertView, parent);
    }


    @Override
    public void setAdapter(T t) {
        this.adapter = t;
    }

    @Override
    public T getAdapter() {
        return adapter;
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    @Override
    public int getRealCount() {
        return adapter.getCount();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        adapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        adapter.unregisterDataSetObserver(observer);
    }

    @Override
    public int getViewTypeCount() {
        return adapter.getViewTypeCount() + 1;
    }

    @Override
    public void setStatus(int status) {
        this.status=status;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(status!=MoreContact.Present.STATE_NONE&& position == (getCount() - 1)){
            return getViewTypeCount() - 1;
        }
        return adapter.getItemViewType(position);
    }

    @Override
    public int getCount() {
        if (status!=MoreContact.Present.STATE_NONE) {
            return getRealCount() + 1;
        }
        return getRealCount();
    }

    @Override
    public boolean isEmpty() {
        return adapter.isEmpty();
    }


    public View getMoreView(View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_load_more, null);
        }

        TextView textTv = ViewHolder.getView(view, R.id.textTv);
        ContentLoadingProgressBar progressBar = ViewHolder.getView(view, R.id.progressBar);

        switch(status){
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

        return view;
    }


    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return ((BaseAdapter) adapter).getDropDownView(position, convertView, parent);
    }

    public boolean areAllItemsEnabled() {
        return adapter.areAllItemsEnabled();
    }

    public boolean isEnabled(int position) {
        return adapter.isEnabled(position);
    }

    @Override
    public boolean equals(Object o) {
        return adapter.equals(o);
    }


    @Override
    public int hashCode() {
        return adapter.hashCode();
    }

    @Override
    public void notifyDataSetChanged() {
        ((BaseAdapter) adapter).notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        ((BaseAdapter) adapter).notifyDataSetInvalidated();
    }

    @Override
    public String toString() {
        return adapter.toString();
    }



}
