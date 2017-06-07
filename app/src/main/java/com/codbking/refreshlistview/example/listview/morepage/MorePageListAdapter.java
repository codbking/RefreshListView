package com.codbking.refreshlistview.example.listview.morepage;

/**
 * Created by wulang on 2017/5/15.
 */

public interface MorePageListAdapter<T> {


    void setAdapter(T t);

    T getAdapter();

    int getItemCount();
    int getRealCount();

    int getItemViewType(int position);

    int getViewTypeCount();


     void setStatus(int status);


    }
