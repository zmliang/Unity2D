package com.jinkegroup.networtlib;

import android.content.Context;
import android.util.Log;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2017/7/20.
 */

public class LocalCacheInterceptor implements Interceptor {
    private Context context;

    LocalCacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        if (!DeviceNetworkUtil.isNetworkAvailable(context)) {
            /**
             * 离线缓存控制  总的缓存时间=在线缓存时间+设置离线缓存时间
             */
            int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周,单位:秒
            CacheControl tempCacheControl = new CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(maxStale, TimeUnit.SECONDS)
                    .build();
            request = request.newBuilder()
                    .cacheControl(tempCacheControl)
                    .build();
            Log.i("", "intercept:no network ");
//        }
        return chain.proceed(request);
    }
}
