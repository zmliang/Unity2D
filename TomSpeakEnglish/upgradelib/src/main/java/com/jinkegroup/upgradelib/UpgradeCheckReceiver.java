package com.jinkegroup.upgradelib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public class UpgradeCheckReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        intent.setClass(context, UpgradeCheckService.class);
        context.startService(intent);
    }
}
