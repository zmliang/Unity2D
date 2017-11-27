package com.jinkegroup.upgradelib.downloadservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.jinkegroup.upgradelib.util.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public class DownloadService extends Service {
    public static final String ACTION_DOWNLOAD_PACKAGE = "mobi.suishi.reader.DOWNLOAD_PACKAGE";

    public static final String INTENT_EXTRA_AUTO_INSTALL = "auto_install";
    public static final String INTENT_EXTRA_SHOW_PROGRESS = "show_progress";
    public static final String INTENT_EXTRA_URL = "url";
    public static final String INTENT_EXTRA_FILE_NAME = "file";
    public static final String INTENT_EXTRA_PARAMETER = "params";
    public static final String INTENT_EXTRA_DOWNLOAD_SOURCE = "download_source";
    public static final String INTENT_EXTRA_NOTIFICATION_TITLE = "notification_title";
    public static final String INTENT_EXTRA_NOTIFICATION_TEXT = "notification_text";
    public static final String INTENT_EXTRA_NOTIFICATION_TICKER = "notification_ticker";
    public static final String INTENT_EXTRA_NOTIFICATION_ICON = "notification_icon";
    public static final String INTENT_EXTRA_NOTIFICATION_LARGE_ICON = "notification_large_icon";
    public static final String INTENT_EXTRA_NOTIFICATION_ID = "notification_id";

    public static final String DOWNLOAD_SOURCE_PUSH = "push";
    public static final String DOWNLOAD_SOURCE_UPGRADE = "upgrade";
    public static final String DOWNLOAD_SOURCE_ADV = "adv";

    private Map<String, DownloadHolder> mDownloadMap = new HashMap<String, DownloadHolder>();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String url = intent.getExtras().getString(INTENT_EXTRA_URL);
        String fileName = intent.getExtras().getString(INTENT_EXTRA_FILE_NAME);
        Bundle bundle = intent.getExtras().getBundle(INTENT_EXTRA_PARAMETER);
        boolean showProgress = intent.getExtras().getBoolean(INTENT_EXTRA_SHOW_PROGRESS);
        boolean autoInstall = intent.getExtras().getBoolean(INTENT_EXTRA_AUTO_INSTALL);
        String downloadSource = intent.getExtras().getString(INTENT_EXTRA_DOWNLOAD_SOURCE);
        String title = intent.getExtras().getString(INTENT_EXTRA_NOTIFICATION_TITLE);
        String text = intent.getExtras().getString(INTENT_EXTRA_NOTIFICATION_TEXT);
        String ticker = intent.getExtras().getString(INTENT_EXTRA_NOTIFICATION_TICKER);
        String icon = intent.getExtras().getString(INTENT_EXTRA_NOTIFICATION_ICON);
        String largeIcon = intent.getExtras().getString(INTENT_EXTRA_NOTIFICATION_LARGE_ICON);
        int id = intent.getExtras().getInt(INTENT_EXTRA_NOTIFICATION_ID);

        DownloadHolder download;
        synchronized(mDownloadMap) {
            download = mDownloadMap.get(url+fileName);
            if (download == null) {
                download = new DownloadHolder(this, url, fileName, bundle, showProgress,
                        autoInstall, downloadSource,
                        title, text, ticker, icon, largeIcon, id);
                mDownloadMap.put(url+fileName, download);
            }
        }

        download.start();

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    public void onDownloadFinished(DownloadHolder download) {
        synchronized(mDownloadMap) {
            mDownloadMap.remove(download.mUrl + download.mFileName);
            if (mDownloadMap.size() == 0) {
                stopSelf();
            }
        }
    }



    public static int genNotificationID() {
        return (new Random()).nextInt(Constant.NOTIFY_ID_DOWNLOAD_END - Constant.NOTIFY_ID_DOWNLOAD_START)
                + Constant.NOTIFY_ID_DOWNLOAD_START;
    }
}
