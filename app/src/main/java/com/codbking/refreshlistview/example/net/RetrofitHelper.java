package com.codbking.refreshlistview.example.net;

import android.content.Context;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by wulang on 2017/3/29.
 */

public class RetrofitHelper {

    private static final String TAG = "RetrofitHelper";

    private static Retrofit retrofit;


    private static Retrofit.Builder mBuilder;
    private static OkHttpClient client;

    public static final String HOST ="http://10.60.172.14:8080/ljj/";

    static {

        client = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor())
                .build();


        mBuilder = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HOST)
        ;
    }


    public static <T> T create(Class<T> cls, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new ObserveOnMainCallAdapterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        return retrofit.create(cls);
    }


    public static <T> T create(Class<T> cls) {
        retrofit = mBuilder
                .addCallAdapterFactory(new ObserveOnMainCallAdapterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        return retrofit.create(cls);
    }


    //带dailog
    public static <T> T create(Context context, String dialogMessage, Class<T> cls) {

        retrofit = mBuilder
                .addCallAdapterFactory(new ObserveOnMainCallAdapterFactory(context, dialogMessage))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        return retrofit.create(cls);
    }

    public static <T> T create(Context context, Class<T> cls) {
        return create(context, "正在加载...", cls);
    }


}
