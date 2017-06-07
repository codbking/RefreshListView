package com.codbking.refreshlistview.example.net;

/**
 * Created by wulang on 2017/5/2.
 */

public class NetThrowable extends Exception {

    private int errno;
    private String errmsg;


    public NetThrowable(int errno, String errmsg) {
        super(errmsg);
        this.errno = errno;
        this.errmsg = errmsg;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }
}
