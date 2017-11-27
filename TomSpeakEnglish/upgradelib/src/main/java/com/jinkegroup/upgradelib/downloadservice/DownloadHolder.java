package com.jinkegroup.upgradelib.downloadservice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import com.jinkegroup.upgradelib.JobCheckService;
import com.jinkegroup.upgradelib.R;
import com.jinkegroup.upgradelib.UpgradeCheckService;
import com.jinkegroup.upgradelib.util.AlarmService;
import com.jinkegroup.upgradelib.util.CMNotification;
import com.jinkegroup.upgradelib.util.SystemPackageTools;

import java.io.File;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public class DownloadHolder implements DownloaderListener {

    DownloadService mService;
    boolean mShowProgress;
    boolean mAutoInstall;
    String mUrl;
    String mFicationID;
    String mFileName;
    int mProgress = 0;
    int mStatus = STATUS_NONE;
    Bundle mBundle;
    String mSource;
    String mTitle;
    String mText;
    String mTicker;
    String mIcon;
    String mLargeIcon;
    int mNotifyID;
    Handler mHandler;
    CMNotification mNotification;

    static final int STATUS_NONE = 0;
    static final int STATUS_DOWNLOADING = 1;
    static final int STATUS_FAILED = 2;
    static final int STATUS_FINISHED = 3;


    public DownloadHolder(DownloadService service, String url, String fileName,
                          Bundle bundle, boolean showProgress,
                          boolean autoInstall, String source,
                          String title, String text, String ticker,
                          String icon, String largeIcon, int id) {
        mService = service;
        mUrl = url;
        mFileName = fileName;
        mBundle = bundle;
        mShowProgress = showProgress;
        mAutoInstall = autoInstall;
        mSource = source;
        mTitle = title;
        mText = text;
        mTicker = ticker;
        mIcon = icon;
        mLargeIcon = largeIcon;
        mNotifyID = id;
        mNotification = new CMNotification(mService);
        mNotification.setID(mNotifyID).setIcon(mIcon)
                .setLargeIcon(largeIcon).setContentText(mText)
                .setContentTitle(mTitle).setTickerText(mTicker);
    }


    protected Handler createHandler() {
        HandlerThread ht = new HandlerThread("Downloader");
        ht.start();
        return new Handler(ht.getLooper());
    }


    synchronized void start() {
        if (mStatus != STATUS_DOWNLOADING) {
            mStatus = STATUS_DOWNLOADING;
            mHandler = createHandler();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    RetryingDownloader downloader = new RetryingDownloader(new DownloaderProvider(mService, mUrl, mFileName, mBundle),
                            new AlarmService(mService, mHandler));
                    downloader.setListener(DownloadHolder.this);
                    downloadProgress(0, 100);
                    downloader.download();
                }
            });
        }
    }


    @Override
    public void requestCompleted() {
        // TODO Auto-generated method stub
    }

    @Override
    public void downloadCompleted(Uri location) {
        downloadingFinished(location);
        handleDownloadCompleted(location);
    }

    @Override
    public void downloadFailed() {
        downloadingFinished(Uri.EMPTY);
        handleDownloadCompleted(Uri.EMPTY);
    }


    private void handleDownloadCompleted(Uri location) {
        if (location.equals(Uri.EMPTY)) {

            if (mShowProgress) {
                mNotification.setContentText(mService.getString(R.string.download_failed))
                        .setTickerText(mService.getString(R.string.download_failed))
                        .notify(0, 0, false);
            }
        } else {

            if (DownloadService.DOWNLOAD_SOURCE_PUSH.equals(mSource)) {
            //    StatisticsManager.getInstance().onAppPushEvent(StaRpcModel.AppPushAction.DOWNLOAD, mBundle.getString(SConstant.PN_PACKAGENAME));
            }
            if (mAutoInstall) {
                mNotification.cancel();
                SystemPackageTools.install(mService, new File(location.getPath()));
                if (DownloadService.DOWNLOAD_SOURCE_PUSH.equals(mSource)) {
                 //   StatisticsManager.getInstance().onAppPushEvent(StaRpcModel.AppPushAction.INSTALL, mBundle.getString(SConstant.PN_PACKAGENAME));
                }
            } else {
                mNotification.setContentText((mShowProgress || mText == null) ? mService.getString(R.string.download_finished) : mText)
                        .setTickerText((mShowProgress || mTicker == null) ? mService.getString(R.string.download_finished) : mTicker)
                        .setPendingIntent(mNotification.createInstallIntent(location)).notify(0, 0, false);
            }
        }
    }


    //it should be called after download task posted in any case
    //otherwise, mDownloading is always in true.
    private synchronized void downloadingFinished(Uri location) {
        mStatus = STATUS_FINISHED;
        mHandler.getLooper().quit();
        if (DownloadService.DOWNLOAD_SOURCE_UPGRADE.equals(mSource)) {
            Intent intent = new Intent(JobCheckService.ACTION_JOB_FINISHED, location, mService, UpgradeCheckService.class);
            mService.startService(intent);
        }
        mService.onDownloadFinished(this);
    }

    @Override
    public void downloadProgress(int read, int total) {
        if (mShowProgress) {
            if (read == 0 && total != 0) {
                mNotification.setContentText(mService.getString(R.string.download_prepare));
            } else if (read > 0 && total > 0) {
                mNotification.setContentText(String.format(mService.getString(R.string.download_progress), read>>10, total>>10));
            }
            mNotification.notify(read, total, false);
        }
    }

}
