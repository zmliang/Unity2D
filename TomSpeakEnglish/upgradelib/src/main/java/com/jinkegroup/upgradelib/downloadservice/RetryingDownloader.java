package com.jinkegroup.upgradelib.downloadservice;

import android.net.Uri;

import com.jinkegroup.upgradelib.util.AlarmCallback;
import com.jinkegroup.upgradelib.util.AlarmService;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public class RetryingDownloader implements DownloaderListener {
    private static final int MAX_RETRIES = 3; //retry 3, total 4
    protected int mRetryCount = 0;

    private ResumbleDownloader mDownloader;
    private DownloaderListener mListener;
    private AlarmService mAlarmService;

    public RetryingDownloader(DownloaderProvider provider, AlarmService alarmService) {
        mDownloader = new ResumbleDownloader(provider);
        mDownloader.setListener(this);
        mAlarmService = alarmService;
    }


    public void setListener(DownloaderListener listener) {
        mListener = listener;
    }


    public void download() {
        mDownloader.download();
    }


    @Override
    public void requestCompleted() {
        if (mListener != null) {
            mListener.requestCompleted();
        }
    }


    @Override
    public void downloadCompleted(Uri location) {
        if (mListener != null) {
            mListener.downloadCompleted(location);
        }
    }


    @Override
    public void downloadFailed() {
        mRetryCount += 1;
        if (retriesLeft(mRetryCount)) {
            scheduleNextRetry(getTimeout(mRetryCount));
        } else {
            if (mListener != null) {
                mListener.downloadFailed();
            }
        }
    }


    @Override
    public void downloadProgress(int read, int total) {
        if (mListener != null) {
            mListener.downloadProgress(read, total);
        }
    }


    private void scheduleNextRetry(long timeout) {

        mAlarmService.set(timeout, new AlarmCallback() {

            @Override
            public void alarmTriggered() {
                download();
            }
        });
    }


    protected long getTimeout(int retryCount) {
        return 1000 + ((retryCount-1) * 2) * 5 * 1000;
    }


    protected boolean retriesLeft(int retryCount) {
        return retryCount <= MAX_RETRIES;
    }
}
