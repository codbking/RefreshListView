package com.codbking.refreshlistview.example.listview.morepage;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.BaseAdapter;


public class MorePageBean {

    private static final String TAG = "MorePageBean";
    private MorePageListAdapter adapter;
    int currentPage = 1;
    int pageSize = 20;
    int pageCount = Integer.MAX_VALUE;
    private boolean isError = false;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        Log.d("moreView", "setError() called with: error = [" + error + "]");
        isError = error;
        if(isError){
            if (adapter instanceof BaseAdapter) {
                Log.d("moreView", "setError() called with: BaseAdapter = [" + error + "]");
                BaseAdapter mBaseAdapter = (BaseAdapter) this.adapter;
                mBaseAdapter.notifyDataSetChanged();
            } else if (adapter instanceof RecyclerView.Adapter) {
                Log.d("moreView", "setError() called with:  RecyclerView.Adapter = [" + error + "]");
                RecyclerView.Adapter mBaseAdapter = (RecyclerView.Adapter) this.adapter.getAdapter();
                mBaseAdapter.notifyDataSetChanged();
            }
        }
    }

    public void setAdapter(MorePageListAdapter adapter) {
        this.adapter = adapter;
    }

    public MorePageBean(int currentPage, int pageSize, int pageCount) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pageCount = pageCount;
    }

    public boolean isMore() {
        return isShowMore() && currentPage * pageSize <= pageCount;
    }

    public boolean isShowMore() {
        return adapter.getRealCount() >= pageSize;
    }

    public boolean isMoreItem(int postion) {
        boolean isMoreItem = isShowMore() && adapter.getItemCount() - 1 == postion;
        return isMoreItem;
    }

    public void nextPage() {
        currentPage++;
    }

    public void clear() {
        currentPage = 1;
        pageSize = 20;
        pageCount = Integer.MAX_VALUE;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        isError = false;
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}