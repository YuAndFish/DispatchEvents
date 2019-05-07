package com.jiedai.dispatchevents.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

public class ListViewEx extends ListView {
    private static final String TAG = "ListViewEx";
    boolean mIsHorScroll = true;
    private HorizontalScrollViewEx2 mHorizontalScrollViewEx2;

    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    public ListViewEx(Context context) {
        super(context);
    }

    public ListViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewEx(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setHorizontalScrollViewEx2(
            HorizontalScrollViewEx2 horizontalScrollViewEx2) {
        mHorizontalScrollViewEx2 = horizontalScrollViewEx2;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(true);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                int absX = Math.abs(deltaX);
                int absY = Math.abs(deltaY);
                if (absX > absY && absX > 10) {
                    // 设置成false父元素才能拦截所需的事件
                    if (mIsHorScroll) {
                        mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(false);
                        Log.i(TAG, "dispatchTouchEvent : false " + mIsHorScroll);
                    }
                } else if (absX < absY && absY > 10) {
                    mIsHorScroll = false;
                    mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(true);
                    Log.i(TAG, "dispatchTouchEvent : true " + mIsHorScroll);
                }

                break;
            }
            case MotionEvent.ACTION_UP: {
                mIsHorScroll = true;
                break;
            }
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(event);
    }

}
