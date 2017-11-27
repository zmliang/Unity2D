package com.jinkegroup.listener;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/31
 * CopyRight:  JinkeGroup
 */

public interface OnUpdateCheckResultListener {
    void onSuccess(boolean hasNew);
    void onError(String msg);
}
