package com.jinkegroup.listener;

import com.jinkegroup.model.UpdateModel;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/31
 * CopyRight:  JinkeGroup
 */

public interface UpdateListener {
    void onFoundNewVersion(UpdateModel updateInfo);
    void onNoFoundNewVersion();
    void onCheckError(String msg);

    void onDownloading(Integer... values);
    void onDownloadFinish();
    void onDownloadError(String error);
}
