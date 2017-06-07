package com.codbking.refreshlistview.example.listview.api;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.codbking.refreshlistview.example.listview.morepage.BaseMorePageListView;
import com.codbking.refreshlistview.example.listview.morepage.WrapperMorePageListAdapter;
import com.codbking.refreshlistview.example.listview.utils.UI;


/**
 * Created by codbking on 2017/5/16.
 */

public class SwipeRefreshMorePageListView extends BaseMorePageListView<SwipeRefreshLayout, ListAdapter, DataSetObserver> {

    private static final String TAG = "RefreshMorePageListView";

    private ListView mListView;

    @Override
    public void setShowError() {
        super.setShowError();
        UI.setVisibility(getListView(), false);
    }

    @Override
    public void showData() {
        super.showData();
        UI.setVisibility(getListView(), true);
    }

    @Override
    public void showEmpty() {
        super.showEmpty();
        UI.setVisibility(getListView(), true);
    }

    @Override
    public void setShowLoad() {
        super.setShowLoad();
        UI.setVisibility(getListView(), false);
    }

    public SwipeRefreshMorePageListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public SwipeRefreshLayout initListView(Context context, AttributeSet attrs) {
        SwipeRefreshLayout layout = new SwipeRefreshLayout(context);
        mListView = new ListView(context, attrs);
        layout.addView(mListView);
        return layout;
    }


    @Override
    public DataSetObserver inintDataObserver() {
        return new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                SwipeRefreshMorePageListView.this.onChanged();
            }

        };
    }

    @Override
    public int getCount() {
        return getAdapter().getCount();
    }

    @Override
    public void bindAdapter(SwipeRefreshLayout listView, ListAdapter adapter) {
        mListView.setAdapter(adapter);
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

    private int scrollState;

    @Override
    public void setOnScrollListener(SwipeRefreshLayout listview) {
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d(TAG, "onScrollStateChanged() called with: view = [" + view + "], scrollState = [" + scrollState + "]");
                SwipeRefreshMorePageListView.this.scrollState=scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d(TAG, "onScroll() called with: view = [" + view + "], firstVisibleItem = [" + firstVisibleItem + "], visibleItemCount = [" + visibleItemCount + "], totalItemCount = [" + totalItemCount + "]");
                if(totalItemCount==0||scrollState==0){
                    return;
                }
                SwipeRefreshMorePageListView.this.onScroll((SwipeRefreshLayout) mListView.getParent(), firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });

        getListView().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListView().setRefreshing(true);
                refreshData(new OnLoadCallBack() {
                    @Override
                    public void onSuccess(int pageCount) {
                        getListView().setRefreshing(false);
                    }
                    @Override
                    public void onFail(boolean isShowError) {
                        getListView().setRefreshing(false);
                    }
                });
            }
        });
    }


    @Override
    public ListAdapter adapterConverter(ListAdapter adapter) {
        WrapperMorePageListAdapter adapter1 = new WrapperMorePageListAdapter();
        adapter1.setAdapter(adapter);
        return adapter1;
    }



}
