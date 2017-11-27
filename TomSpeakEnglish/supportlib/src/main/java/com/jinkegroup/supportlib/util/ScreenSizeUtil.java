package com.jinkegroup.supportlib.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;


public class ScreenSizeUtil {

    public static float getDeivceDensity(Activity activity) {

        DisplayMetrics metrics = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);

        float density = metrics.density; // 屏幕密度
//        int densityDpi = metrics.densityDpi; // 屏幕密度DPI

        return density;
    }

    public static int getDeviceWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
//        int screenHeight = dm.heightPixels;

        return screenWidth;
    }

    @SuppressWarnings("deprecation")
    public static int getDeviceWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getDeviceHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;

        return screenHeight;
    }

    @SuppressWarnings("deprecation")
    public static int getDeviceHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    public static int getDeviceWidthDp(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
//        int screenHeight = dm.heightPixels;

        return (int) (screenWidth / dm.density);
    }

    public static int getDeviceHeightDp(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;

        return (int) (screenHeight/dm.density);
    }

    public static boolean isLargeSizeScreen(Activity activity) {
        return getDeviceWidthDp(activity) >= 600;
    }

    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 获取底部return、home、menu三个虚拟栏按高度
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 获取手机状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public static int getDisplayHeight(Activity activity) {
        return getDeviceHeight(activity) - getStatusBarHeight(activity);
    }

    public static final int    SCREENTYPE_PHONE          = 0;
    public static final int    SCREENTYPE_PAD_LAND       = 1;
    public static final int    SCREENTYPE_PAD_PORT       = 2;
    public static final int    SCREENTYPE_PHONE_LAND     = 3;
    public static final int    SCREENTYPE_PHONE_PORT     = 4;

    public static final String STR_SCREENTYPE_PAD_LAND   = "tablet_land";
    public static final String STR_SCREENTYPE_PAD_PORT   = "tablet_port";
    public static final String STR_SCREENTYPE_PHONE_LAND = "phone_land";
    public static final String STR_SCREENTYPE_PHONE_PORT = "phone_port";

    private static int         sScreenType               = SCREENTYPE_PHONE;

    public static void setScreenType(int screenType) {
        sScreenType = screenType;
    }

    public static void setScreenTypeByStr(String strScreenType) {
        if (STR_SCREENTYPE_PAD_LAND.equals(strScreenType)) {
            // 平板横向
            setScreenType(SCREENTYPE_PAD_LAND);

        } else if (STR_SCREENTYPE_PAD_PORT.equals(strScreenType)) {
            // 平板纵向
            setScreenType(SCREENTYPE_PAD_PORT);
        } else if (STR_SCREENTYPE_PHONE_LAND.equals(strScreenType)) {
            // 手机横向
            setScreenType(SCREENTYPE_PHONE_LAND);
        } else if (STR_SCREENTYPE_PHONE_PORT.equals(strScreenType)) {
            // 手机纵向
            setScreenType(SCREENTYPE_PHONE_PORT);
        }
    }

    public static String getScreenTypeName() {
        switch (sScreenType) {
            case SCREENTYPE_PHONE:
                return "PHONE";
            case SCREENTYPE_PAD_LAND:
                return "PAD_LAND";
            case SCREENTYPE_PAD_PORT:
                return "PAD_PORT";
            case SCREENTYPE_PHONE_LAND:
                return "PHONE_LAND";
            case SCREENTYPE_PHONE_PORT:
                return "PHONE_PORT";
            default:
                return "PHONE";
        }
    }

    public static boolean isPadPort() {
        return sScreenType == SCREENTYPE_PAD_PORT;
    }

    public static boolean isPadLand() {
        return sScreenType == SCREENTYPE_PAD_LAND;
    }

    public static boolean isPhone() {
        return sScreenType != SCREENTYPE_PAD_PORT && sScreenType != SCREENTYPE_PAD_LAND;
    }

    public static boolean isPhonePort() {
        return sScreenType == SCREENTYPE_PHONE_PORT;
    }

    public static boolean isPhoneLand() {
        return sScreenType == SCREENTYPE_PHONE_LAND;
    }

    public static boolean isPort(){
        return sScreenType == SCREENTYPE_PHONE_PORT||sScreenType == SCREENTYPE_PAD_PORT;
    }

    public static boolean isLand(){
        return sScreenType == SCREENTYPE_PHONE_LAND||sScreenType == SCREENTYPE_PAD_LAND;
    }

    public static boolean isPad(){
        return sScreenType == SCREENTYPE_PAD_LAND||sScreenType == SCREENTYPE_PAD_PORT;
    }
}
