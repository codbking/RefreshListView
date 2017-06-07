package com.codbking.refreshlistview.example.listview.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by wulang on 2016/4/11.
 */
public class ViewHolder {
    private static final String TAG = "CustomViewHolder";
    public static <T extends  View > T getView (View convertView,int id){

        SparseArray<View> views= (SparseArray<View>) convertView.getTag();
        if(views==null){
            views=new SparseArray<View>();
            convertView.setTag(views);
        }
        T view= (T) views.get(id);
        if(view==null){
            view= (T) convertView.findViewById(id);
            views.put(id,view);
         }

        return view;
    }


}
