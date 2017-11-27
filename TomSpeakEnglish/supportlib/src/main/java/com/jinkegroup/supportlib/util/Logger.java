package com.jinkegroup.supportlib.util;

import android.text.TextUtils;
import android.util.Log;

import com.goldplusgold.supportlib.BuildConfig;

public class Logger {

    private static boolean IS_ERR = BuildConfig.DEBUG;
    private static boolean IS_DEBUG = BuildConfig.DEBUG;
    private static boolean IS_WARN = false;
    private static boolean IS_INFO = false;
    private static boolean IS_VERBOSE = false;

    public static void init(boolean isPrintble) {
        IS_DEBUG = IS_DEBUG || isPrintble;
        IS_WARN = IS_WARN || isPrintble;
        IS_INFO = IS_INFO || isPrintble;
        IS_VERBOSE = IS_VERBOSE || isPrintble;
    }

    public static void d(String tag, String msg) {
        if (IS_DEBUG) {
            Log.d(tag, cleanStr(msg));
        }
    }

    @SuppressWarnings("rawtypes")
    public static void d(Class className, String msg) {
        if (IS_DEBUG) {
            Log.d(className.getSimpleName(), cleanStr(msg));
        }
    }

    public static void i(String tag, String msg) {
        if (IS_INFO) {
            Log.i(tag, cleanStr(msg));
        }
    }

    @SuppressWarnings("rawtypes")
    public static void i(Class className, String msg) {
        if (IS_INFO) {
            Log.i(className.getSimpleName(), cleanStr(msg));
        }
    }

    public static void w(String tag, String msg) {
        if (IS_WARN) {
            Log.w(tag, cleanStr(msg));
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (IS_WARN) {
            Log.w(tag, cleanStr(msg), tr);
        }
    }

    public static void e(String tag, String msg) {
        if (IS_ERR) {
            Log.e(tag, cleanStr(msg));
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (IS_ERR) {
            Log.e(tag, cleanStr(msg), tr);
        }
    }

    @SuppressWarnings("rawtypes")
    public static void w(Class className, String msg) {
        if (IS_WARN) {
            Log.w(className.getSimpleName(), cleanStr(msg));
        }
    }

    @SuppressWarnings("rawtypes")
    public static void e(Class className, String msg) {
        if (IS_ERR) {
            Log.e(className.getSimpleName(), cleanStr(msg));
        }
    }

    @SuppressWarnings("rawtypes")
    public static void e(Class className, String msg, Throwable tr) {
        if (IS_ERR) {
            Log.e(className.getSimpleName(), cleanStr(msg), tr);
        }
    }

    public static void v(String logTag, String msg) {
        if (IS_VERBOSE) {
            Log.v(logTag, msg);
        }
    }

    private static String cleanStr(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return "";
        }
        if (msg.length() > 3000) {
            return msg.substring(0, 3000);
        }
        return msg;
    }
}
