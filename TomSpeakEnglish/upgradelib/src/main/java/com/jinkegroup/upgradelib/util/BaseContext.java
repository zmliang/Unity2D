package com.jinkegroup.upgradelib.util;

import android.content.Context;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public class BaseContext {
    private static Context mContext = null;

    public static void setContext(Context context){
        mContext = context.getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }

}
