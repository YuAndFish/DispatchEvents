package com.jiedai.dispatchevents.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.jiedai.dispatchevents.LogUtils;

/**
 * Create by yuheng
 * date：2019/4/29
 * description：
 */
public class HorScrollView2 extends ViewGroup {
    private int childCount = 0;
    private int childWidth = 0;
    private int childIndex = 0;

    private int lastX = 0;
    private int lastY = 0;

    private Scroller scroller = null;
    private VelocityTracker velocityTracker = null;
    private OnGetChildListener onGetChildListener = null;

    public HorScrollView2(Context context) {
        this(context, null);
    }

    public HorScrollView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorScrollView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
        velocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = x;
            lastY = y;
            if (!scroller.isFinished()) {
                intercept = true;
                scroller.abortAnimation();
            }
        } else {
            intercept = true;
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                //scrollBy实现了基于当前位置的相对滑动
                LogUtils.d("deltaX: " + deltaX + " lastX: " + lastX);
                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                velocityTracker.computeCurrentVelocity(1000);
                float xVelocity = velocityTracker.getXVelocity();
                LogUtils.d("xVelocity: " + xVelocity);
                if (Math.abs(xVelocity) >= 50) {
                    childIndex = xVelocity > 0 ? childIndex - 1 : childIndex + 1;
                } else {
                    childIndex = (scrollX + childWidth / 2) / childWidth;
                }
                childIndex = Math.max(0, Math.min(childIndex, childCount - 1));
                int dx = childIndex * childWidth - scrollX;
                if (onGetChildListener != null) {
                    onGetChildListener.getChildIndex(childIndex);
                }
                smoothScrollBy(dx, 500);
                break;
        }
        lastX = x;
        lastY = y;
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        childCount = getChildCount();
        final View childView = getChildAt(0);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else {
            if (widthMode == MeasureSpec.AT_MOST) {
                measureWidth = childView.getMeasuredWidth() * childCount;
                setMeasuredDimension(measureWidth, heightSize);
            } else if (heightMode == MeasureSpec.AT_MOST) {
                measureHeight = childView.getMeasuredHeight();
                setMeasuredDimension(widthSize, measureHeight);
            } else {
                measureWidth = childView.getMeasuredWidth() * childCount;
                measureHeight = childView.getMeasuredHeight();
                setMeasuredDimension(measureWidth, measureHeight);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                childWidth = childView.getMeasuredWidth();
                childView.layout(childLeft, 0, childWidth + childLeft, childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    /**
     * 水平滑动
     *
     * @param dx
     * @param duration
     */
    private void smoothScrollBy(int dx, int duration) {
        LogUtils.d("getScrollX(): " + getScrollX() + " dx: " + dx);
        scroller.startScroll(getScrollX(), 0, dx, 0, duration);
        invalidate();
    }

    public void showToday() {
        int dx = childIndex * childWidth;
        if (scroller != null) {
            childIndex = 0;
            scroller.startScroll(dx, 0, -dx, 0, 1000);
            invalidate();
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        velocityTracker.recycle();
        super.onDetachedFromWindow();
    }

    public void setOnGetChildListener(OnGetChildListener onGetChildListener) {
        this.onGetChildListener = onGetChildListener;
    }

    public interface OnGetChildListener {
        void getChildIndex(int index);
    }

}
