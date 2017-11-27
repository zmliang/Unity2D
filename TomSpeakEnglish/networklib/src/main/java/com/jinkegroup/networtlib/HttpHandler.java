package com.jinkegroup.networtlib;

import android.text.TextUtils;
import android.util.Log;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by root on 17-2-28.
 */
public class HttpHandler extends StringCallback {
    private NetworkListener listener;
    private String url;

    public HttpHandler(NetworkListener listener) {
        this.listener = listener;
    }


    public HttpHandler(NetworkListener listener, String url) {
        this.listener = listener;
        this.url = url;
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        Log.e("xsy", "url: " + request.url());
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        if (null != listener) {
            listener.onFail("服务器返回错误");
        }
    }

    @Override
    public void onResponse(String response, int i) {
        Log.e("xsy", "onResponse response " + response + " url ：" + url);
        if (listener == null) {
            return;
        }
        if (TextUtils.isEmpty(response)) {
            listener.onFail("服务器返回错误");
            return;
        }
        if (response.contains("errorCode")) {
            listener.onFail(response);
        } else {
            listener.onSuccess(response);
        }
    }


}
