package com.codbking.refreshlistview.example.listview.api;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.codbking.refreshlistview.example.listview.BaseListView;


/**
 * Author: 沈浩波
 * Date: $date$
 * Time: $time$
 * FIXME
 */
public class RecyclerViewE extends BaseListView<RecyclerView, RecyclerView.Adapter, RecyclerView.AdapterDataObserver> implements NestedScrollingChild {


	public RecyclerViewE(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public RecyclerView initListView(Context context, AttributeSet attrs) {
		return new RecyclerView(context, attrs);
	}

	@Override
	public RecyclerView.AdapterDataObserver inintDataObserver() {
		return new RecyclerView.AdapterDataObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				RecyclerViewE.this.onChanged();
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
		if (adapter != null) {
			adapter.unregisterAdapterDataObserver(dataObserver);
		}
	}

}
