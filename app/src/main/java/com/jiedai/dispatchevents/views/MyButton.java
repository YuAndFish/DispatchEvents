package com.jiedai.dispatchevents.views;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jiedai.dispatchevents.LogUtils;
import com.jiedai.dispatchevents.utils.ScreenSizeUtils;
import com.nineoldandroids.view.ViewHelper;

/**
 * Create by yuheng
 * date：2019/4/24
 * description：
 */
public class MyButton extends AppCompatButton {
    private int lastX = 0;
    private int lastY = 0;
    private int dragWidth = 0;
    private int dragHeight = 0;

    public MyButton(Context context) {
        this(context, null);
    }

    public MyButton(final Context context, AttributeSet attrs) {
        super(context, attrs);
        dragWidth = ScreenSizeUtils.getScreenWidth(context);
        dragHeight = ScreenSizeUtils.getScreenHeight(context) - ScreenSizeUtils.getStatusBarHeight(context) - ScreenSizeUtils.getActionBarHeight(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = rawX - lastX;
                int deltaY = rawY - lastY;
                int translationX = (int) ViewHelper.getTranslationX(this) + deltaX;
                int translationY = (int) ViewHelper.getTranslationY(this) + deltaY;
                LogUtils.d("rawX: " + rawX + " deltaX: " + deltaX + " translationX: " + translationX);
                if (translationX <= 0) {
                    translationX = 0;
                } else if (translationX >= dragWidth - getWidth()) {
                    translationX = dragWidth - getWidth();
                }
                if (translationY <= 0) {
                    translationY = 0;
                } else if (translationY >= dragHeight - getHeight()) {
                    translationY = dragHeight - getHeight();
                }
                ViewHelper.setTranslationX(this, translationX);
                ViewHelper.setTranslationY(this, translationY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        lastX = rawX;
        lastY = rawY;
        return super.onTouchEvent(event);
    }

}
