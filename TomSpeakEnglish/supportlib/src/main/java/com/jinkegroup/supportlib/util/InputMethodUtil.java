package com.jinkegroup.supportlib.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;


public class InputMethodUtil {

    public static void showInputMethod(Context ctx) {
        onHideShowInputMethod(ctx);
    }

    public static void hideInputMethod(Context ctx) {
        onHideShowInputMethod(ctx);
    }

    private static void onHideShowInputMethod(Context ctx) {
        if (ctx == null) {
            return;
        }
        InputMethodManager sInputMethodMgr = (InputMethodManager)
                ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        sInputMethodMgr.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isInputMethodShowing(Context ctx) {
        if (ctx == null) {
            return false;
        }
        InputMethodManager sInputMethodMgr = (InputMethodManager)
                ctx.getSystemService(Context.INPUT_METHOD_SERVICE);

        return sInputMethodMgr.isActive();
    }
}
