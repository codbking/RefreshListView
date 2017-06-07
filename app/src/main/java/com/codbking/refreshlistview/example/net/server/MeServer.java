package com.codbking.refreshlistview.example.net.server;

import com.codbking.refreshlistview.example.net.HttpBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * Created by wulang on 2017/6/6.
 */

public interface MeServer {

    //用户登录
    @POST("me/function001")
    Observable<HttpBean<String>> function001(String email, String password);

    //通过邮件注册
    /**
     *
     "email" :           # 邮件
     "contactNumber" :   # 联系号码
     "firstName" :       # 姓氏
     "lastName" :        # 名字
     "companyName" :     # 公司名
     "address" :         # 地址
     "password" :        # 密码
     */
    @POST("me/function002")
    Observable<HttpBean<String>> function002(@Field("email") String email,
                                             @Field("contactNumber") String contactNumber,
                                             @Field("firstName") String firstName,
                                             @Field("lastName") String lastName,
                                             @Field("companyName") String companyName,
                                             @Field("address") String address,
                                             @Field("password") String password);

    //接收手机验证码
    @POST("me/function003")
    Observable<HttpBean<String>> function003(@Field("code") String code,
                                             @Field("contactNumber") String contactNumber);

    //校验验证码
    @POST("me/function004")
    Observable<HttpBean<String>> function004(@Field("code") String code,
                                             @Field("contactNumber") String contactNumber);

    //通过手机注册
    @POST("me/function005")
    Observable<HttpBean<String>> function005(@Field("email") String email,
                                             @Field("contactNumber") String contactNumber,
                                             @Field("firstName") String firstName,
                                             @Field("lastName") String lastName,
                                             @Field("companyName") String companyName,
                                             @Field("address") String address,
                                             @Field("password") String password);

    //用户注销登录
    @POST("me/function006")
    Observable<HttpBean<String>> function006();

    //找回密码
    @POST("me/function007")
    Observable<HttpBean<String>> function007(@Field("method") String method);

    //修改密码
    @POST("me/function008")
    Observable<HttpBean<String>> function008(@Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword);

    //获取个人信息
    @POST("me/function009")
    Observable<HttpBean<String>> function009(@Field("id") String id);

    //用户修改个人信息
    @POST("me/function010")
    Observable<HttpBean<String>> function010(int pageIndex, int pageSize);

    //APP 获取个人所有收货地址
    @POST("me/function011")
    Observable<HttpBean<String>> function011(int pageIndex, int pageSize);

    //查看地址详情
    @POST("me/function012")
    Observable<HttpBean<String>> function012(int pageIndex, int pageSize);

    //APP 添加新地址
    @POST("me/function013")
    Observable<HttpBean<String>> function013(int pageIndex, int pageSize);

    //用户修改常用地址
    @POST("me/function014")
    Observable<HttpBean<String>> function014(int pageIndex, int pageSize);

    //用户查看购物车信息
    @POST("me/function015")
    Observable<HttpBean<String>> function015(int pageIndex, int pageSize);

    //用户修改购物车中商品数量
    @POST("me/function016")
    Observable<HttpBean<String>> function016(int pageIndex, int pageSize);

    //用户批量删除购物车内商品
    @POST("me/function017")
    Observable<HttpBean<String>> function017(int pageIndex, int pageSize);

    //用户查看正在进行中的订单(未付款)
    @POST("me/function018")
    Observable<HttpBean<String>> function018(int pageIndex, int pageSize);

    //用户查看正在已完成的订单(交易取消或已支付)
    @POST("me/function019")
    Observable<HttpBean<String>> function019(int pageIndex, int pageSize);

    //根据订单编号修改订单状态
    @POST("me/function020")
    Observable<HttpBean<String>> function020(int pageIndex, int pageSize);

    //根据订单编码和用户ID查看订单详情
    @POST("me/function021")
    Observable<HttpBean<String>> function021(int pageIndex, int pageSize);

    //用户查看邮寄地址列表
    @POST("me/function022")
    Observable<HttpBean<String>> function022(int pageIndex, int pageSize);

    //添加自提信息
    @POST("me/function023")
    Observable<HttpBean<String>> function023(int pageIndex, int pageSize);

    //添加订单到支付表
    @POST("me/function024")
    Observable<HttpBean<String>> function024(int pageIndex, int pageSize);

    //添加商品到购物车
    @POST("me/function025")
    Observable<HttpBean<String>> function025(int pageIndex, int pageSize);


    @POST("me/function026")
    Observable<HttpBean<String>> function026(int pageIndex, int pageSize);


    //通过exchangeId查看汇率
    @POST("me/function027")
    Observable<HttpBean<String>> function027(int pageIndex, int pageSize);

    //通过exchangeId查看汇率图
    @POST("me/function028")
    Observable<HttpBean<String>> function028(int pageIndex, int pageSize);

}
