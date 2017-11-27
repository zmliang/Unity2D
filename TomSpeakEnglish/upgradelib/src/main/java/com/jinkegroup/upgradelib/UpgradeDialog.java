package com.jinkegroup.upgradelib;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.jinkegroup.upgradelib.downloadservice.DownloaderProvider;
import com.jinkegroup.upgradelib.util.CMNotification;
import com.jinkegroup.upgradelib.util.Constant;
import com.jinkegroup.upgradelib.util.SystemPackageTools;

import java.io.File;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public class UpgradeDialog {

    public static void checkShow(Context context) {

        long last = getPreferences(context).getLong(
                Constant.PREF_KEY_UPGRADE_HINT_TIME, 0);

        if (System.currentTimeMillis() - last <= Constant.UPGRADE_HINT_INTERVAL_MS) {
            return;
        }

        if (upgradeExist(context)) {
            showDialog(context);
        }

        getPreferences(context)
                .edit()
                .putLong(Constant.PREF_KEY_UPGRADE_HINT_TIME,
                        System.currentTimeMillis()).commit();

        return;
    }


    private static void showDialog(final Context context) {
        final AlertDialog dlg = new AlertDialog.Builder(context).create();
        dlg.setCanceledOnTouchOutside(false);
        dlg.show();
        Window window = dlg.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0f;
        window.setAttributes(lp);

        window.setContentView(R.layout.upgrade_dialog);

        Button ok = (Button) window.findViewById(R.id.upgrade_diglog_ok);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CMNotification.cancel(Constant.NOTIFY_ID_UPGRADE_AVAILABLE, context);
                install(context);
                dlg.cancel();
            }
        });

        Button cancel = (Button) window.findViewById(R.id.upgrade_diglog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dlg.cancel();
            }
        });

    }

    private static boolean upgradeExist(Context context) {
        UpgradeCheckService.DeviceInfo deviceInfo = UpgradeCheckService.DeviceInfo.getInstance();
        int localVersion = UpgradeCheckService.DeviceInfo.getInstance().getClientVersion();
        File apkFile = DownloaderProvider.getFile(context,
                UpgradeCheckService.DeviceInfo.getInstance().getClientApkFileName(), false);

        if (apkFile.exists()) {
            int apkVersion = SystemPackageTools.getApkVersion(context, apkFile);

            if (localVersion >= apkVersion) {
                CMNotification.cancel(Constant.NOTIFY_ID_UPGRADE_AVAILABLE, context);
                apkFile.delete();
                return false;
            } else {
                if (!UpgradeCheckService.DeviceInfo.getInstance().getSignature().equals(
                        SystemPackageTools.getSignature(context, apkFile))) {

                    CMNotification.cancel(Constant.NOTIFY_ID_UPGRADE_AVAILABLE, context);
                    apkFile.delete();
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            CMNotification.cancel(Constant.NOTIFY_ID_UPGRADE_AVAILABLE, context);
            return false;
        }

    }

    public static void install(Context context) {
        SystemPackageTools.install(context, DownloaderProvider.getFile(context,
                UpgradeCheckService.DeviceInfo.getInstance().getClientApkFileName(), false));
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("upgrade.prompt_check",
                Context.MODE_PRIVATE);
    }

}
