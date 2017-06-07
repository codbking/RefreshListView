package com.codbking.refreshlistview.example.listview.api;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.codbking.refreshlistview.example.listview.BaseListView;


/**
 * Created by wulang on 2017/4/7.
 */

public class ListViewE extends BaseListView<ListView, ListAdapter, DataSetObserver> {


    public ListViewE(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public ListView initListView(Context context, AttributeSet attrs) {
        return new ListView(context, attrs);
    }


    @Override
    public DataSetObserver inintDataObserver() {
        return new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                ListViewE.this.onChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return getAdapter().getCount();
    }

    @Override
    public void bindAdapter(ListView listView, ListAdapter adapter) {
        listView.setAdapter(adapter);
    }

    @Override
    public void registerDataSetObserver(ListAdapter adapter, DataSetObserver dataObserver) {
        adapter.registerDataSetObserver(dataObserver);
    }

    @Override
    public void unregisterDataSetObserver(ListAdapter adapter, DataSetObserver dataObserver) {
        if (adapter != null) {
            adapter.unregisterDataSetObserver(dataObserver);
        }
    }

}
