package com.jinkegroup.networtlib;


/**
 * SDK监听接口
 */
public interface NetworkListener {

    /**
     * 成功回调
     **/
    void onSuccess(String message);

    /**
     * 失败回调
     **/
    void onFail(String message);


}
