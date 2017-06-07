package com.codbking.refreshlistview.example.net.server;

import com.codbking.refreshlistview.example.net.bean.PageBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by wulang on 2017/6/7.
 */

public interface TestServer  {
    @GET("page.json")
    Observable<List<PageBean>> page();

}
