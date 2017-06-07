package com.codbking.refreshlistview.example.net.server;

import com.codbking.refreshlistview.example.net.HttpBean;
import com.codbking.refreshlistview.example.net.bean.MallBean;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Created by wulang on 2017/6/6.
 */

public interface  MallServer {

    //查看所有商品
    @POST("mall/function001")
    Observable<HttpBean<MallBean>> function001(int pageIndex, int pageSize);


    //通过商品ID查询商品详情
    @POST("mall/function001")
    Observable<HttpBean<MallBean>>function002(String productId);


    //模糊查询商品,显示带几条
    @POST("mall/function001")
    Observable<HttpBean<MallBean>>function003(String proName, int count);

}
