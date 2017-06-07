package com.codbking.refreshlistview.example.listview.morepage;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.codbking.refreshlistview.example.listview.BaseListView;
import com.codbking.refreshlistview.example.listview.morepage.contact.MoreContact;


/**
 * Created by wulang on 2017/5/15.
 */

public abstract class BaseMorePageListView<T extends View, E, F> extends BaseListView<T, E, F> {

    private static final String TAG = "BaseMorePageListView";

    protected MorePresent mPageBean;
    private boolean isLoad = false;

    private OnLoadListerner mOnLoadListerner;
    private MorePageListAdapter mMorePageListAdapter;


    public void setOnLoadListerner(OnLoadListerner onLoadListerner) {
        mOnLoadListerner = onLoadListerner;
    }

    public BaseMorePageListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setAdapter(E adapter) {
        mPageBean=new MorePresent();
        load();
        E adapter1 = adapterConverter(adapter);
        mMorePageListAdapter= (MorePageListAdapter) adapter1;
        super.setAdapter(adapter1);
        setOnScrollListener(getListView());
    }


    public void onScroll(T view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount) {
            Log.d(TAG, "onScroll() called with: view = [" + view + "], firstVisibleItem = [" + firstVisibleItem + "], visibleItemCount = [" + visibleItemCount + "], getStatus = [" + mPageBean.getStatus() + "]");
            if (!isLoad&&mPageBean.getStatus()!= MoreContact.Present.STATE_FINISH&&mPageBean.getStatus()!= MoreContact.Present.STATE_NONE) {
                  mPageBean.nextPage();
                  loadData(null);
            }
        }
    }

    public void setStatus(int status){
        Log.d(TAG, "setStatus() called with: status = [" + status + "]");
        mMorePageListAdapter.setStatus(status);
    }

    private void loadData(final OnLoadCallBack callBack) {
        isLoad = true;
        if (mOnLoadListerner != null) {
            mOnLoadListerner.loadData(mPageBean.getPageIndex(), mPageBean.getPageSize(), new OnLoadCallBack() {
                @Override
                public void onSuccess(int pageCount) {
                    Log.d(TAG, "onSuccess() called with: pageCount = [" + pageCount + "]");
                    isLoad=false;
                    mPageBean.setCurrentSize(pageCount);
                    setStatus(mPageBean.getStatus());

                    if(callBack!=null){
                        callBack.onSuccess(pageCount);
                    }
                }

                @Override
                public void onFail(boolean isShowError) {
                    isLoad=false;

                    if (mPageBean.getPageIndex()>1) {
                        //刷新数据
                        setStatus(MoreContact.Present.STATE_ERROR);
                        mPageBean.setPageIndex(mPageBean.getPageIndex()-1);
                    } else {
                        BaseMorePageListView.this.setShowError();
                    }

                    if(callBack!=null){
                        callBack.onFail(isShowError);
                    }
                }
            });
        }
    }


    private void load() {
        setOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                mPageBean.init();
                loadData(null);
            }
        });

        refreshData(null);
    }


    public void refreshData(OnLoadCallBack callBack){
        mPageBean.init();
        loadData(callBack);
    }


    public abstract void setOnScrollListener(T listview);
    public abstract E adapterConverter(E adapter);

    public interface OnLoadListerner {
        void loadData(int pageNo, int pageSiz, OnLoadCallBack callback);
    }

    public interface OnLoadCallBack {
        void onSuccess(int pageCount);
        void onFail(boolean isShowError);
    }


}
