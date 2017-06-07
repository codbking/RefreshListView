package com.codbking.refreshlistview.example.listview.morepage.contact;

/**
 * Created by wulang on 2017/6/7.
 */

public interface MoreContact {

    interface View {
    }

    interface Present {

        //没有更多
        int STATE_NONE = 0;
        int STATE_LOAD = 1;
        int STATE_FINISH = 2;
        int STATE_ERROR = 3;

        int getStatus();

    }


}
