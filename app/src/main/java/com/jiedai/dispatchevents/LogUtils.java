package com.jiedai.dispatchevents;

import android.util.Log;

/**
 * Create by yuheng
 * date：2019/4/16
 * description：
 */
public class LogUtils {
    private static final String TAG = "dispatch_events";

    public static void d(String content) {
        Log.d(TAG, content);
    }

    public static void d(String tag, String content) {
        Log.d(tag, content);
    }

    public static void e(String content) {
        Log.e(TAG, content);
    }

    public static void e(String tag, String content) {
        Log.e(tag, content);
    }

    public static void i(String content) {
        Log.i(TAG, content);
    }

    public static void i(String tag, String content) {
        Log.i(tag, content);
    }
}
