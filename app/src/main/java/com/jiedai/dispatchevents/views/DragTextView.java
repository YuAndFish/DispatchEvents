package com.jiedai.dispatchevents.views;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jiedai.dispatchevents.LogUtils;
import com.jiedai.dispatchevents.utils.ScreenSizeUtils;

/**
 * Create by yuheng
 * date：2019/4/26
 * description：
 */
public class DragTextView extends AppCompatTextView {
    private int margin = 0;
    private int lastX = 0;
    private int lastY = 0;
    private int startRawX = 0;
    private int startRawY = 0;
    private int dragWidth = 0;
    private int dragHeight = 0;

    private View.OnClickListener listener = null;

    public DragTextView(Context context) {
        this(context, null);
    }

    public DragTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        dragWidth = ScreenSizeUtils.getScreenWidth(context);
        dragHeight = ScreenSizeUtils.getScreenHeight(context) - ScreenSizeUtils.getStatusBarHeight(context) - ScreenSizeUtils.getActionBarHeight(context);
        margin = ScreenSizeUtils.dip2px(context, 10);
        LogUtils.d("dragWidth: " + dragWidth + " dragHeight: " + dragHeight + " margin: " + margin);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int distanceX = 0;
        int distanceY = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //将点下的点的坐标保存
                lastX = x;
                lastY = y;
                startRawX = (int) event.getRawX();
                startRawY = (int) event.getRawY();
                LogUtils.d("lastX: " + lastX + " lastY: " + lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                distanceX = x - lastX;
                distanceY = y - lastY;
                LogUtils.d("distanceX: " + distanceX + " distanceY: " + distanceY);
                int left = getLeft() + distanceX;
                int top = getTop() + distanceY;
                int right = getRight() + distanceX;
                int bottom = getBottom() + distanceY;

                if (left <= margin) {
                    left = margin;
                    right = left + getWidth();
                } else if (left >= dragWidth - getWidth() - margin) {
                    left = dragWidth - getWidth() - margin;
                    right = left + getWidth();
                }

                if (top <= margin) {
                    top = margin;
                    bottom = top + getHeight();
                } else if (top >= dragHeight - getHeight() - margin) {
                    top = dragHeight - getHeight() - margin;
                    bottom = top + getHeight();
                }
                layout(left, top, right, bottom);
                break;
            case MotionEvent.ACTION_UP:
                int absDistanceX = Math.abs((int) event.getRawX() - startRawX);
                int absDistanceY = Math.abs((int) event.getRawY() - startRawY);
                LogUtils.d("absDistanceX: " + absDistanceX + " absDistanceY: " + absDistanceY);
                if (absDistanceX < margin && absDistanceY < margin) {
                    listener.onClick(this);
                }
                break;
        }
        return true;
    }

    @Override
    public void setOnClickListener(View.OnClickListener l) {
        this.listener = l;
    }

}
