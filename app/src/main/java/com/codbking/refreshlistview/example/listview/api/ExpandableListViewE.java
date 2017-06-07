package com.codbking.refreshlistview.example.listview.api;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.codbking.refreshlistview.example.listview.BaseListView;


/**
 * Created by wulang on 2017/4/7.
 */

public class ExpandableListViewE extends BaseListView<ExpandableListView, BaseExpandableListAdapter, DataSetObserver> {


    public ExpandableListViewE(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public ExpandableListView initListView(Context context, AttributeSet attrs) {
        return new ExpandableListView(context, attrs);
    }

    @Override
    public DataSetObserver inintDataObserver() {
        return new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                ExpandableListViewE.this.onChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return getAdapter().getGroupCount()==0?getListView().getHeaderViewsCount():getAdapter().getGroupCount();
    }

    @Override
    public void bindAdapter(ExpandableListView listView, BaseExpandableListAdapter adapter) {
        listView.setAdapter(adapter);
    }

    @Override
    public void registerDataSetObserver(BaseExpandableListAdapter adapter, DataSetObserver dataObserver) {
        adapter.registerDataSetObserver(dataObserver);
    }

    @Override
    public void unregisterDataSetObserver(BaseExpandableListAdapter adapter, DataSetObserver dataObserver) {
        if (adapter != null) {
            adapter.unregisterDataSetObserver(dataObserver);
        }
    }

}
