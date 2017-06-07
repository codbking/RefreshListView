package com.codbking.refreshlistview.example.net;

import android.text.TextUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;


public class TokenInterceptor implements Interceptor {
    public static final boolean IS_PRINT_REULST = true;
    private static final Charset UTF8 = Charset.forName("UTF-8");
    public static final String LOGIN = "/user/appLogin";

    private static final String TAG = "TokenInterceptor";

//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            APP.getInstance().finishAcitivitys();
//            LoginActivity.start(APP.getInstance());
//        }
//    };


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = printLog(chain, request);

//        if (isTokenExpired(response)) {//根据和服务端的约定判断token过期
//            mHandler.sendEmptyMessage(0);
//            return null;
//        }

        return response;
    }


    private Response printLog(Chain chain, Request request) throws IOException {
        long startNs = System.nanoTime();
        Response response;
        try {
            String host = request.url().url().toString();
            String method = request.method();
            String body = NetUtils.getRequstParams(request.body());

            LogUtils.i("OkHttp", "<-- HTTP START :" + method);
            LogUtils.i("OkHttp", host + "?" + body);

            response = chain.proceed(request);
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

            Charset charset = UTF8;
            BufferedSource source = response.body().source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            String reuslt=buffer.clone().readString(charset);
            if(IS_PRINT_REULST){
                try {
                    if(reuslt!=null&&reuslt.length()>500){
                        LogUtils.json("OkHttp", reuslt.substring(0,500));
                    }else {
                        LogUtils.json("OkHttp", reuslt);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    reuslt="";
                }
            }
            float size=0;
            if(!TextUtils.isEmpty(reuslt)){
                byte[] bytes = reuslt.getBytes();
                if(bytes!=null){
                    size=bytes.length/(float)1024;
                }
            }
            LogUtils.i("OkHttp", "HTTP END take:" + tookMs + "ms，size=" +size+"KB"+" --> (" + host + "?" + body + ")");

        } catch (Exception e) {
            LogUtils.e("OkHttp", "<-- HTTP FAILED: " + e);
            throw e;
        }
        return response;
    }


    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
//    private boolean isTokenExpired(Response response) {
//
//        if (response.code() == 200) {
//            try {
//                ResponseBody responseBody = response.body();
//                BufferedSource source = responseBody.source();
//                source.request(Long.MAX_VALUE); // Buffer the entire body.
//                Buffer buffer = source.buffer();
//
//                Charset charset = UTF8;
//                MediaType contentType = responseBody.contentType();
//                if (contentType != null) {
//                    charset = contentType.charset(UTF8);
//                }
//
//                String data = buffer.clone().readString(charset);
//
//                HttpBean bean;
//                String path = response.request().url().url().getPath();
//                Gson gson = new Gson();
//
//                if (!path.contains(LOGIN)) {
//                    bean = gson.fromJson(data, HttpBean.class);
//                    if (NetStatus.CODE_FAIL_TOKEN_EXPIRED.equals(bean.getErrno()) || NetStatus.CODE_FAIL_TOKEN_INVALID.equals(bean.getErrno())) {
//                        //token过期
//                        return true;
//                    }
//                } else {
//                    HttpBean<UserBean> bean2 = gson.fromJson(data, new TypeToken<HttpBean<UserBean>>() {
//                    }.getType());
//                    if (NetStatus.CODE_SUCCESS.equals(bean2.getErrno())) {
//                        try {
//                            APP.getInstance().savaToken(bean2.getData().getTokenInfo().getToken());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }


}