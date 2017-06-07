package com.codbking.refreshlistview.example.net;


import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 * Created by wulang on 2017/5/2.
 */

public class NetUtils {

    private static final String TAG = "NetUtils";

    public static String getRequstParams(RequestBody requestBody) {

        if (requestBody == null) {
            return "";
        }

        Buffer buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = requestBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String paramsStr = "";
        try {
            paramsStr = buffer.readString(charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramsStr;
    }


//    public static Request getSignRequst(Interceptor.Chain chain) throws IOException {
//
//        Request request = chain.request();
//
//        RequestBody mRequestBody = request.body();
//
//
//
//        String body = getRequstParams(mRequestBody);
//
//        if(body.contains("{")&&body.contains("}")){
//            String newBody="";
//            try {
//                JSONObject jsonObject= new JSONObject(body);
//                for(Iterator<String> it=jsonObject.keys();it.hasNext();){
//                    String key=it.next();
//                     newBody+="&"+key+"="+jsonObject.optString(key);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            if(!TextUtils.isEmpty(newBody)){
//                 body=newBody.substring(1);
//            }
//        }
//
//        SortedMap<String, String> paramsMap = new TreeMap<>();
//
//        if (!StringUtils.isEmpty(body)) {
//            String[] bodyArr = body.split("&");
//            for (String s : bodyArr) {
//                String[] sArr = s.split("=");
//                if (sArr != null && sArr.length == 2) {
//                    paramsMap.put(sArr[0], sArr[1]);
//                }
//            }
//        }
//
//        long timestamp = System.currentTimeMillis();
//        paramsMap.put("timestamp", String.valueOf(timestamp));
//
//        if (isNeedToken(request)) {
//            paramsMap.put("token", APP.getInstance().getToken());
//        }
//
//        String sign = MD5Utils.getSign(paramsMap);
//
//
//        String bodyContent = body;
//
//        if(isNeedToken(request)){
//            bodyContent += "&timestamp=" + timestamp +"&sign=" + sign+"&token="+APP.getInstance().getToken();
//        }else {
//            bodyContent += "&timestamp=" + timestamp +"&sign=" + sign;
//        }
//
//
//        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        RequestBody newBody = RequestBody.create(mediaType, bodyContent);
//
//        System.out.println("signbody=" + bodyContent);
//
//
//
////        URL u=request.url().url();
////
////        StringBuilder result=new StringBuilder();
////        result.append(u.getProtocol());
////        result.append(":");
////        if (u.getAuthority() != null) {// ANDROID: && u.getAuthority().length() > 0) {
////            result.append("//");
////            result.append(u.getAuthority());
////        }
////        if(BuildConfig.isTest){
////            String  content= RetrofitHelper.HOST.substring(result.length());
////            if(content.endsWith("/")){
////                content=content.substring(0,content.length()-1);
////            }
////            result.append(content);
////        }
////
////        String fileAndQuery = u.getFile();
////        if (fileAndQuery != null) {
////            result.append(fileAndQuery);
////        }
////
////        HttpUrl newHttpUrl=HttpUrl.parse(result.toString());
//
//
//
//        Request signRequest = request.newBuilder()
//                .post(newBody)
//                .build();
//
//        return signRequest;
//    }



    private static boolean isNeedToken(Request request) {

        String url = request.url().url().getPath();
        if ("/user/appLogin".equals(url)) {
            return false;
        }
        return true;
    }


    //是登录的密码字段
    private static boolean isPassWord(String path, String key) {

        if ("/user/appLogin".equals(path) && "password".equals(key)) {
            return true;
        }
        return false;
    }


}
