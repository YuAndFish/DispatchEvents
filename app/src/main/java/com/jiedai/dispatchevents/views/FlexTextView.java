package com.jiedai.dispatchevents.views;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Scroller;

public class FlexTextView extends AppCompatButton {
    private static final String TAG = "FlexTextView";

    private int mLastY;
    private int mFlexHeight;
    private boolean mFlex = true;
    private boolean mScrollStart = false;
    private int mMinHeight;
    private int mMaxHeight;
    private int mOriginalHeight;

    // private boolean mStartAnimation = false;
    // private Handler mHandler;
    private Scroller mScroller;
    private Context mContext;

    public FlexTextView(Context context) {
        this(context, null);
    }

    public FlexTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMinHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
        mMaxHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 450, context.getResources().getDisplayMetrics());
        mOriginalHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, context.getResources().getDisplayMetrics());
        this.mContext = context;
        mScroller = new Scroller(context);
    }

    private int mEventDownY = 0;
    private int mEventUpY = 0;
    private int mEventMoveHeight = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mScroller == null) {
            mScroller = new Scroller(this.mContext);
        }
        int y = (int) event.getY();
        int deltaY = y - mLastY;
        mFlexHeight = getHeight() + deltaY;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mEventDownY = (int) event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (mFlexHeight <= mMinHeight) {
                    mFlex = false;
                } else if (mFlexHeight >= mMaxHeight) {
                    mFlex = false;
                } else {
                    mFlex = true;
                }
                if (mFlex) {
                    setFlexHeight(mFlexHeight);
//                hideChildView(y - mLastY);
                }
                mEventMoveHeight = mFlexHeight;
                break;

            case MotionEvent.ACTION_UP:
//            mEventUpY = (int) event.getY();
//            if (mEventUpY - mEventDownY < 0)
//            {
//                Log.i(TAG, "mEventUpY - mEventDownY: " + (mEventUpY - mEventDownY));
//                startMinusScrolling(mEventUpY - mEventDownY);
//            } else if (mEventUpY - mEventDownY > 0)
//            {
//
//            }
                break;
        }

        mLastY = y;
        return super.onTouchEvent(event);
    }

    private void startMinusScrolling(int scrollY) {
        int distanceY = mEventMoveHeight - mMinHeight;
        mScroller.startScroll(0, scrollY, 0, -distanceY);
        invalidate();
    }

    private void setFlexHeight(int height) {
        LayoutParams params = (LayoutParams) this.getLayoutParams();
        params.height = height;
        requestLayout();
        this.setLayoutParams(params);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            // 实现了基于所传递参数的绝对滑动
            Log.i(TAG, " getCurrY: " + mScroller.getCurrY());
            if (!mScroller.isFinished()) {
                setFlexHeight(mOriginalHeight + mScroller.getCurrY());
                postInvalidate();
            } else {
                mEventDownY = 0;
                mEventUpY = 0;
            }
        }
    }

    private void hideChildView(int hideHeight) {
        MarginLayoutParams marginParams = (MarginLayoutParams) this.getLayoutParams();
        marginParams.topMargin = hideHeight;
        this.setLayoutParams(marginParams);
        requestLayout();
    }

}
