package com.jiedai.dispatchevents.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.jiedai.dispatchevents.LogUtils;

/**
 * Create by yuheng
 * date：2019/4/24
 * description：
 */
public class MyLayout extends LinearLayout {

    public MyLayout(Context context) {
        this(context, null);
    }

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean flag = super.dispatchTouchEvent(ev);
        LogUtils.d("MyLayout + dispatchTouchEvent: " + flag);
        return flag;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean flag = super.onInterceptTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        LogUtils.d("MyLayout + onInterceptTouchEvent: " + flag);
        return flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag = super.onTouchEvent(event);
        LogUtils.d("MyLayout + onTouchEvent: " + flag);
        return flag;
    }
}
