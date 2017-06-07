package com.codbking.refreshlistview.example.listview.utils;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codbking.refreshlistview.example.APP;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by wulang on 2017/6/7.
 */

public class UI {


    public static int px(int dip) {
        final float scale = APP.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static int px(float dip) {
        final float scale = APP.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    //设置viewgroup的第几个可见
    public static void setVisibility(ViewGroup viewGroup, int index) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (i == index) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }

    public static void setVisibility(ViewGroup viewGroup, View chilView) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            setVisibility(view,view==chilView);
        }
    }

    public static void setVisibility(List<View> views, View chilView) {
        for (int i = 0; i < views.size(); i++) {
            View view=views.get(i);
            setVisibility(view,chilView==view);
        }
    }



    //设置view是否可见
    public static void setVisibility(View view, boolean isVisible) {
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    //设置view是否可见
    public static void setInvisible(View view, boolean isVisible) {
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }


    public static void setTextView(TextView textView, Object object, String fileName) {
        String value = "";
        if (object != null) {
            if (TextUtils.isEmpty(fileName)) {
                value = object.toString();
            } else {
                try {
                    Field field = object.getClass().getField(fileName);
                    field.setAccessible(true);
                    Object valueObj = field.get(object);

                    if (!TextUtils.isEmpty(value)) {
                        value = String.valueOf(valueObj);
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        textView.setText(value);
    }




    public static void showTextHighlight(TextView tv, String baseText, String highlightText) {
        if ((null == tv) || (null == baseText) || (null == highlightText)) {
            return;
        }

        int index = baseText.indexOf(highlightText);
        if (index < 0) {
            tv.setText(baseText);
            return;
        }

        int len = highlightText.length();
        /**
         *  "<u><font color=#FF8C00 >"+str+"</font></u>"; 	//with underline
         *  "<font color=#FF8C00 >"+str+"</font>";			//without underline
         *
         *  <color name="dark_orange">#FF8C00</color>
         */
        Spanned spanned = Html.fromHtml(baseText.substring(0, index) + "<font color=#FF6E6E >"
                + baseText.substring(index, index + len) + "</font>"
                + baseText.substring(index + len, baseText.length()));

        tv.setText(spanned);
    }



}
