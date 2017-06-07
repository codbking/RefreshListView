package com.codbking.refreshlistview.example.listview.api;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.codbking.refreshlistview.example.listview.morepage.BaseMorePageListView;
import com.codbking.refreshlistview.example.listview.morepage.WrapperMorePageListAdapter;


/**
 * Created by codbking on 2017/5/16.
 */

public class MorePageListView extends BaseMorePageListView<ListView,ListAdapter,DataSetObserver> {


    public MorePageListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOnScrollListener(ListView listview) {
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                MorePageListView.this.onScroll((ListView) view,firstVisibleItem,visibleItemCount,totalItemCount);
            }
        });
    }

    @Override
    public ListAdapter adapterConverter(ListAdapter adapter) {
        WrapperMorePageListAdapter adapter1=new WrapperMorePageListAdapter();
        adapter1.setAdapter(adapter);
        return adapter1;
    }

    @Override
    public ListView initListView(Context context, AttributeSet attrs) {
        return new ListView(context,attrs);
    }

    @Override
    public DataSetObserver inintDataObserver() {
        return new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                MorePageListView.this.onChanged();
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
           if(adapter!=null){
               adapter.registerDataSetObserver(dataObserver);
           }
    }
}
