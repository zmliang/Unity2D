package com.jinkegroup.upgradelib.downloadservice;

import android.net.Uri;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/18
 * CopyRight:  JinkeGroup
 */

public interface DownloaderListener {
    void requestCompleted();

    void downloadCompleted(Uri location);

    void downloadFailed();

    void downloadProgress(int read, int total);
}
