package com.jinkegroup.edu.se.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.jinkegroup.edu.se.AppApplication;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/24
 * CopyRight:  JinkeGroup
 */

public class SharedPreferenceUtils {

    private static final String FILE_NAME = "config";
    private static SharedPreferences.Editor editor;

    public static Context getContext(){
        return AppApplication.getInstance();
    }

    public static SharedPreferences getSharedPreferences(){
        return getContext().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
    }

    public static Boolean getBooleanData(String key,Boolean value){
        return getSharedPreferences().getBoolean(key,value);
    }

    public static void setBooleanData(String key,Boolean value){
        editor = getSharedPreferences().edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public static String getStringData(String key, String value) {
        return getSharedPreferences().getString(key, value);
    }

    public static void setStringData(String key, String value) {
        editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static int getIntData(String key, int value) {
        return getSharedPreferences().getInt(key, value);
    }

    public static void setIntData(String key, int value) {
        editor = getSharedPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static long getLongData(String key, long value) {
        return getSharedPreferences().getLong(key, value);
    }

    public static void setLongData(String key, long value) {
        editor = getSharedPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void setFloatData(String key, float value) {
        editor = getSharedPreferences().edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloatData(String key, float value) {
        return getSharedPreferences().getFloat(key, value);
    }

}
