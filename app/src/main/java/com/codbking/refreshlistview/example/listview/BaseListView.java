package com.codbking.refreshlistview.example.listview;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codbking.refreshlistview.example.R;
import com.codbking.refreshlistview.example.listview.utils.UI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by wulang on 2017/4/7.
 */

public abstract class BaseListView<T extends View, E, F> extends FrameLayout {

	@BindView(R.id.customProgressLayout)
	LinearLayout mProgressBar;
	@BindView(R.id.retryTv)
	TextView mRetryTv;
	@BindView(R.id.netErrorLayout)
	LinearLayout mNetErrorLayout;
	@BindView(R.id.emptyIv)
	ImageView mEmptyIv;
	@BindView(R.id.emptyTv)
	TextView mEmptyTv;
	@BindView(R.id.emptyLayout)
	LinearLayout mEmptyLayout;
	@BindView(R.id.noSearchResultTv)
	TextView noSearchResultTv;
	@BindView(R.id.noSearchResultLayout)
	LinearLayout noSearchResultLayout;

	private static final int TYPE_LIST_EMPTY = 0;
	private static final int TYPE_SEARCH_EMPTY = 1;
	private int type = TYPE_LIST_EMPTY;

	private T listView;
	private ViewGroup loadView;
	private F mDataObserver;

	private String keyWords;
	private OnRetryListener mOnRetryListener;

	public void setOnRetryListener(OnRetryListener onRetryListener) {
		mOnRetryListener = onRetryListener;
	}

	private List<View> views = new ArrayList<>();

	public void setKeyWords(String keyWords) {

		if (TextUtils.isEmpty(keyWords)) {
			type = TYPE_LIST_EMPTY;
			this.keyWords = "";
		} else {
			this.keyWords = keyWords;
			type = TYPE_SEARCH_EMPTY;
		}
	}

	public void setShowLoad() {
		UI.setVisibility(views, mProgressBar);
	}


	public void setShowError() {
		UI.setVisibility(views, mNetErrorLayout);
	}


	public T getListView() {
		return listView;
	}


	public void setEmptyText(String emptyText) {
		mEmptyTv.setText(emptyText);
	}


	public void showEmpty() {
//		UI.setVisibility(views, mEmptyLayout);
	}

	public void showData() {

	}

	public void onChanged() {
		if (getCount() > 0) {
			UI.setVisibility(views, listView);
			showData();
		} else if (type == TYPE_SEARCH_EMPTY) {
			showEmpty();
			UI.setVisibility(views, noSearchResultLayout);
			String baseText = "没有找到“" + keyWords + "”相关结果";
			UI.showTextHighlight(noSearchResultTv, baseText, keyWords);
		} else {
			showEmpty();
			UI.setVisibility(views, mEmptyLayout);
		}
	}

	private E adapter;

	public E getAdapter() {
		return adapter;
	}

	public BaseListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		intiView(context, attrs);
		initEvent();
		initData();
	}

	private void initEvent() {
		mRetryTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setShowLoad();
				if (mOnRetryListener != null) {
					mOnRetryListener.onRetry();
				}

			}
		});
	}

	private void intiView(Context context, AttributeSet attrs) {
		mDataObserver = inintDataObserver();
		listView = initListView(context, attrs);
		listView.setBackgroundColor(0xffffffff);
		listView.setVisibility(VISIBLE);

		Activity activity = (Activity) getContext();

		loadView = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.view_listview_e, null);
		addViewInLayout(listView, 0, generateLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)));
		addViewInLayout(loadView, 1, generateLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));

		ButterKnife.bind(this);


		views.add(listView);
		views.add(mProgressBar);
		views.add(mNetErrorLayout);
		views.add(mEmptyLayout);
		views.add(noSearchResultLayout);
	}

	private void initData() {
		UI.setVisibility(views, mProgressBar);
	}

	public void setAdapter(E adapter) {

		unregisterDataSetObserver(this.adapter, mDataObserver);

		this.adapter = adapter;
		bindAdapter(listView, this.adapter);

		registerDataSetObserver(adapter, mDataObserver);

	}

	public interface OnRetryListener {
		void onRetry();
	}


	public abstract T initListView(Context context, AttributeSet attrs);

	public abstract F inintDataObserver();

	public abstract int getCount();

	public abstract void bindAdapter(T listView, E adapter);

	public abstract void registerDataSetObserver(E adapter, F dataObserver);

	public abstract void unregisterDataSetObserver(E adapter, F dataObserver);
}
