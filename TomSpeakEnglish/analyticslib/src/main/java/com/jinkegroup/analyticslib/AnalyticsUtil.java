package com.jinkegroup.analyticslib;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * Created by admin on 2017/5/7.
 */

public class AnalyticsUtil {
    public static void onEvent(Context context, String id, HashMap<String,String> m, int value){
        //m.put("__ct__", String.valueOf(value));

        MobclickAgent.onEvent(context, id, m);
    }

}
