package com.jinkegroup.networtlib;


import android.text.TextUtils;

//import com.jinkegroup.supportlib.util.AppCacheSharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2017/7/11.
 */

public class TokenInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {


        Request request = chain.request().newBuilder()
//                .addHeader("Authorization", "Bearer " + AppCacheSharedPreferences.getCacheString("x-access-token"))
                .addHeader("_platform", "android")
//                .addHeader("_version", AppCacheSharedPreferences.getCacheString("versionName"))
                .addHeader("Referer", "api.goldplusgold.com")
                .build();

        Response response = chain.proceed(request);
        if (!TextUtils.isEmpty(response.header("_x-access-token"))) {
//            AppCacheSharedPreferences.putCacheString("x-access-token", response.header("_x-access-token"));
        }

        return response;
    }

}
