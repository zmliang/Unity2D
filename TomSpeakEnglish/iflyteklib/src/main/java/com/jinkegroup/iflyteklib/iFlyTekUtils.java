package com.jinkegroup.iflyteklib;

import android.content.Context;

import com.iflytek.cloud.SpeechUtility;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/19
 * CopyRight:  JinkeGroup
 */

public class iFlyTekUtils {

    public static void iFlyTekInit(Context context){
        SpeechUtility.createUtility(context, "appid=" + context.getString(R.string.app_id));
    }
}
