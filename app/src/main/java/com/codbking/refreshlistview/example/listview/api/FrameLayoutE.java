package com.codbking.refreshlistview.example.listview.api;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
 * Created by wulang on 2017/5/22.
 */

public class FrameLayoutE extends FrameLayout {

    private static final String TAG = "FrameLayoutE";
    
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

    private View listView;
    private ViewGroup loadView;

    private String keyWords;
    private OnRetryListener mOnRetryListener;

    public FrameLayoutE(@NonNull Context context) {
        super(context);
    }

    public FrameLayoutE(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        intiView(context,attrs);
        initEvent();
    }

    public void setOnRetryListener(OnRetryListener onRetryListener) {
        mOnRetryListener = onRetryListener;
    }

    private List<View> views = new ArrayList<>();

    public void showLoad() {
        UI.setVisibility(views, mProgressBar);
    }

    public void showError() {
        UI.setVisibility(views, mNetErrorLayout);
    }

    public void showData() {
        UI.setVisibility(views, listView);
    }

    private void initData() {
        UI.setVisibility(views, mProgressBar);
    }


    private void initEvent() {
        mRetryTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoad();
                if (mOnRetryListener != null) {
                    mOnRetryListener.onRetry();
                }
            }
        });
    }

    private void intiView(Context context, AttributeSet attrs) {


        Activity activity = (Activity) getContext();
        loadView = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.view_listview_e, null);
        addView(loadView,generateLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));
        ButterKnife.bind(this);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        listView=getChildAt(1);
        views.add(listView);
        views.add(mProgressBar);
        views.add(mNetErrorLayout);
        views.add(mEmptyLayout);
        views.add(noSearchResultLayout);
        initData();
    }

    public interface OnRetryListener {
        void onRetry();
    }

}
