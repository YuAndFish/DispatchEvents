package com.jiedai.dispatchevents.views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class WebBackImageView extends AppCompatImageView {
    private static final String TAG = "WebBackImageView";
    private int lastX;
    private int lastY;
    private int screenWidth;
    private int screenHight;
    private int width;
    private float startx;
    private float starty;

    private static int margin = 15;
    private static int marginBottom = 60;

    private WindowManager wm;
    private LayoutParams layoutParams;

    public WebBackImageView(Context context) {
        this(context, null);
    }

    public WebBackImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebBackImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHight = wm.getDefaultDisplay().getHeight();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (width <= 0) {
            width = getWidth();
        }

        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                startx = event.getRawX();
                starty = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int offX = x - lastX;
                int offY = y - lastY;

                int left = getLeft() + offX;
                int top = getTop() + offY;
                int right = getRight() + offX;
                int bottom = getBottom() + offY;

                if (left <= margin) {
                    left = margin;
                    right = left + width;
                } else if (left >= screenWidth - width - margin) {
                    left = screenWidth - width - margin;
                    right = left + width;
                }

                if (top <= margin) {
                    top = margin;
                    bottom = top + width;
                } else if (top >= screenHight - width - marginBottom) {
                    top = screenHight - width - marginBottom;
                    bottom = top + width;
                }
                layout(left, top, right, bottom);

                break;
            case MotionEvent.ACTION_UP:
                double dis = Math.sqrt((Math.pow((startx - event.getRawX()), 2) + Math.pow((starty - event.getRawY()), 2)));
                if (dis < 25) {
                    if (listener != null) {
                        listener.onClick(this);
                    }
                }
                break;
        }
        return true;
    }

    private OnClickListener listener;

    @Override
    public void setOnClickListener(OnClickListener l) {
        listener = l;
    }

}
