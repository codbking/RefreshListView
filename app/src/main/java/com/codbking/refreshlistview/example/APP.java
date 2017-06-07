package com.codbking.refreshlistview.example;

import android.app.Application;

/**
 * Created by wulang on 2017/6/7.
 */

public class APP extends Application {
    private static APP instance;
    public static APP getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
}
