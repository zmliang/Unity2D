package com.jinkegroup.supportlib.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by admin on 2017/6/26.
 */

public class AppInstallUtil {
    public static final String APP_PACKAGE = "com.ylink.MGessTraderYlink_gxjszx";

    public static boolean isAppAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(APP_PACKAGE)) {
                    return true;
                }
            }
        }

        return false;
    }
}
