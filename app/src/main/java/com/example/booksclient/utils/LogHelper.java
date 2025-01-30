package com.example.booksclient.utils;

import android.util.Log;

public class LogHelper {
    private static final boolean IS_DEBUG = true;

    private static final String TAG = "BooksClient";

    public static void v(String message) {
        if (IS_DEBUG) {
            Log.v(TAG, message);
        }
    }

    public static void d(String message) {
        if (IS_DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void i(String message) {
        if (IS_DEBUG) {
            Log.i(TAG, message);
        }
    }

    public static void w(String message) {
        if (IS_DEBUG) {
            Log.w(TAG, message);
        }
    }

    public static void e(String message) {
        if (IS_DEBUG) {
            Log.e(TAG, message);
        }
    }

    public static void e(String message, Throwable throwable) {
        if (IS_DEBUG) {
            Log.e(TAG, message, throwable);
        }
    }
}
