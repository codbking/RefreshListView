package com.codbking.refreshlistview.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.codbking.refreshlistview.example.listview.api.SwipeRefreshMorePageListView;
import com.codbking.refreshlistview.example.listview.morepage.BaseMorePageListView;
import com.codbking.refreshlistview.example.net.RetrofitHelper;
import com.codbking.refreshlistview.example.net.bean.PageBean;
import com.codbking.refreshlistview.example.net.server.TestServer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.list)
    SwipeRefreshMorePageListView mList;

    private MyAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mMyAdapter=new MyAdapter();
        initData();

    }

    private void initData() {
        mList.setOnLoadListerner(new BaseMorePageListView.OnLoadListerner() {
            @Override
            public void loadData(final int pageNo, int pageSiz, final BaseMorePageListView.OnLoadCallBack callback) {
                RetrofitHelper.create(TestServer.class).page().subscribe(new Consumer<List<PageBean>>() {
                    @Override
                    public void accept(@NonNull List<PageBean> pageBeen) throws Exception {
                        callback.onSuccess(pageBeen.size());
                        if(pageNo==1){
                            mMyAdapter.setList(pageBeen);
                        }else {
                            mMyAdapter.list.addAll(pageBeen);
                            mMyAdapter.notifyDataSetChanged();
                        }
                        Log.d(TAG, "accept() called with: pageSize = [" + mMyAdapter.list.size() + "]"+",pageNo="+pageNo);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "accept() called with: throwable = [" + throwable.getMessage() + "]");
                        callback.onFail(true);
                    }
                });
            }
        });

        mList.setAdapter(mMyAdapter);

    }

    private class  MyAdapter extends  BaseAdapter{
        private List<PageBean> list=new ArrayList();

        public void setList(List<PageBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list==null?0:list.size();
        }

        @Override
        public PageBean getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(android.R.layout.simple_list_item_1, null);
            }


            TextView textView = (TextView) convertView;
            textView.setText("position:" +getItem(position).getName());

            return convertView;
        }
    }





}
