package com.goldplusgold.wechatlogin.lib;

import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by lifeng on 2017/7/14.
 */

public class WeChatUtil {
    public static final String APP_ID = "wx7b4e5d4dec309ac5";
    public IWXAPI api;


    private static class SingleTonHolder {
        private static final WeChatUtil INSTANCE = new WeChatUtil();
    }

    public static WeChatUtil getInstance() {
        return SingleTonHolder.INSTANCE;
    }

    private WeChatUtil() {

    }
    /**
     * app注册微信
     */
    public void registerToWX(Context context) {
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);
        api.registerApp(APP_ID);
    }

    /**
     * 微信登录发送权限请求
     */
    public void WXLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        api.sendReq(req);

    }
}
