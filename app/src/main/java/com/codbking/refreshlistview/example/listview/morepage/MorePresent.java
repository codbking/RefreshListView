package com.codbking.refreshlistview.example.listview.morepage;

import com.codbking.refreshlistview.example.listview.morepage.contact.MoreContact;

/**
 * Created by wulang on 2017/6/7.
 */

public class MorePresent implements MoreContact.Present {

    private int pageSize=20;
    private int pageIndex=1;

    //加载的数据
    private int currentSize;

    private boolean isError;


    public MorePresent() {
    }

    public void init(){
        pageIndex=1;
    }

    public MorePresent(int pageSize, int pageIndex) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }

    public void setCurrentSize(int currentSize) {
        if(currentSize<pageSize){
            pageIndex--;
        }


        if(pageIndex<1){
            pageIndex=1;
        }

        this.currentSize = currentSize;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void nextPage(){
             pageIndex++;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }


    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    @Override
    public int getStatus() {
        if(currentSize<pageSize){
            if(pageIndex==1){
                return STATE_NONE;
            }else {
                return STATE_FINISH;
            }
        }
        return STATE_LOAD;
    }



}
