package com.codbking.refreshlistview.example.listview.api;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import com.codbking.refreshlistview.example.listview.morepage.BaseMorePageListView;
import com.codbking.refreshlistview.example.listview.morepage.WrapperMorePageRecyclerAdapter;


/**
 * Created by wulang on 2017/5/17.
 */

public class MorePageRecyclerView extends BaseMorePageListView<RecyclerView,RecyclerView.Adapter,RecyclerView.AdapterDataObserver> {

    public MorePageRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOnScrollListener(final RecyclerView listview) {
        listview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = listview.getLayoutManager();

                int firstPostion=listview.getChildViewHolder(layoutManager.getChildAt(0)).getAdapterPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    MorePageRecyclerView.this.onScroll(listview,firstPostion,visibleItemCount,totalItemCount);
                }
            }
        });
    }

    @Override
    public RecyclerView.Adapter adapterConverter(RecyclerView.Adapter adapter) {
        WrapperMorePageRecyclerAdapter adapter1=new WrapperMorePageRecyclerAdapter();
        adapter1.setAdapter(adapter);
        return adapter1;
    }

    @Override
    public RecyclerView initListView(Context context, AttributeSet attrs) {
        return new RecyclerView(context,attrs);
    }

    @Override
    public RecyclerView.AdapterDataObserver inintDataObserver() {
        return new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Log.d("moreView", "onChanged() called");
                MorePageRecyclerView.this.onChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return getAdapter().getItemCount();
    }

    @Override
    public void bindAdapter(RecyclerView listView, RecyclerView.Adapter adapter) {
        listView.setAdapter(adapter);
    }

    @Override
    public void registerDataSetObserver(RecyclerView.Adapter adapter, RecyclerView.AdapterDataObserver dataObserver) {
        adapter.registerAdapterDataObserver(dataObserver);
    }

    @Override
    public void unregisterDataSetObserver(RecyclerView.Adapter adapter, RecyclerView.AdapterDataObserver dataObserver) {
         if(adapter!=null){
             adapter.unregisterAdapterDataObserver(dataObserver);
         }
    }
}
