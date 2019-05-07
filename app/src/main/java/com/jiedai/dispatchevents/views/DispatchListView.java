package com.jiedai.dispatchevents.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import com.jiedai.dispatchevents.LogUtils;

/**
 * Create by yuheng
 * date：2019/4/30
 * description：
 */
public class DispatchListView extends ListView {
    private HorScrollView2 horScrollView2 = null;
    private int lastX = 0;
    private int lastY = 0;
    private boolean isHorScroll = true;

    public DispatchListView(Context context) {
        this(context, null);
    }

    public DispatchListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DispatchListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHorScrollView2(HorScrollView2 horScrollView2) {
        this.horScrollView2 = horScrollView2;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                horScrollView2.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                int deltaY = y - lastY;
                int absX = Math.abs(deltaX);
                int absY = Math.abs(deltaY);
                LogUtils.d("absX: " + absX + " absY: " + absY);
                if (absX > 10 && absX > absY) {
                    if (isHorScroll) {
                        horScrollView2.requestDisallowInterceptTouchEvent(false);
                    }
                } else if (absX < absY && absY > 10) {
                    isHorScroll = false;
                    horScrollView2.requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                isHorScroll = true;
                break;
        }
        lastX = x;
        lastY = y;
        return super.dispatchTouchEvent(ev);
    }

}
