package com.jinkegroup.edu.se;

import android.app.Application;

import com.goldplusgold.wechatlogin.lib.WeChatUtil;
import com.jinkegroup.iflyteklib.iFlyTekUtils;
import com.jinkegroup.networtlib.NetworkRequestsUtil;
import com.jinkegroup.supportlib.util.AppCacheSharedPreferences;
import com.jinkegroup.supportlib.util.Logger;

/**
 * Created by admin on 2017/10/18.
 */

public class AppApplication extends Application {

    private static AppApplication INSTANCE;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initSharePreference();
        iFlyTekUtils.iFlyTekInit(this);
        initNetWork();
        registerWeChat();
        Logger.init(true);
    }

    public static synchronized AppApplication getInstance() {
        return INSTANCE;
    }

    private void initSharePreference() {
        AppCacheSharedPreferences.init(this);
    }

    private void initNetWork() {
        NetworkRequestsUtil.getInstance().init(this);
    }

    private void registerWeChat() {
        WeChatUtil.getInstance().registerToWX(this);
    }

}
