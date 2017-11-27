package com.jinkegroup.networtlib;

import android.content.Context;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class NetworkRequestsUtil implements INetworkRequests {
    private static final String TAG = "post";
    private static final long CONNECT_TIME_OUT = 10 * 1000L;
    private static final long READ_TIME_OUT = 10 * 1000L;
    public static OkHttpClient okHttpClient;

    private static NetworkRequestsUtil networkRequestsUtil;
    private Context mContext;

    public void init(Context context) {
        this.mContext = context;
        //设置缓存路径 内置存储
        File httpCacheDirectory = new File(mContext.getCacheDir(), "responses");
        //设置缓存 10M
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        okHttpClient = new OkHttpClient.Builder()
                //.cache(cache)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
//                .addInterceptor(new RetryIntercepter(2))
                .addInterceptor(new TokenInterceptor())
                //.addInterceptor(new LocalCacheInterceptor(mContext))
                .addNetworkInterceptor(new NetworkCacheInterceptor())
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private NetworkRequestsUtil() {

    }

    public static NetworkRequestsUtil getInstance() {
        if (networkRequestsUtil == null) {
            synchronized (NetworkRequestsUtil.class) {
                if (networkRequestsUtil == null) {
                    networkRequestsUtil = new NetworkRequestsUtil();
                }
            }
        }
        return networkRequestsUtil;
    }


    @Override
    public void stopNetworkRequests() {
        OkHttpUtils.getInstance().cancelTag(TAG);
    }

    @Override
    public void postNetworkRequests(final String url, final Map<String, String> paramsMap, final NetworkListener listener) {
        Log.e("xsy", "postNetworkRequests url" + url);
        OkHttpUtils.post().url(url)
                .params(paramsMap)
                .tag(TAG).build()
                .execute(new HttpHandler(listener, url));
    }

    @Override
    public void getNetworkRequests(String url, Map<String, String> params, NetworkListener listener) {
        String invokeUrl = url + getUrlParams(params);
        OkHttpUtils.get().url(invokeUrl).tag("get")
                .build()
                .execute(new HttpHandler(listener, url));
    }

    @Override
    public void putNetworkRequests(final String url, final Map<String, String> params, final NetworkListener listener) {
        Log.e("xsy", "putNetworkRequests url" + url);
        FormBody.Builder builder = new FormBody.Builder();
        if (!params.isEmpty()) {
            for (Map.Entry<String, String> item : params.entrySet()) {
                builder.add(item.getKey(), item.getValue());
            }
        }
        RequestBody body = builder.build();
        OkHttpUtils.put().url(url)
                .requestBody(body)
                .tag(TAG).build()
                .execute(new HttpHandler(listener));
    }

    @Override
    public void deleteNetworkRequests(final String url, final Map<String, String> params, final NetworkListener listener) {
        String invokeUrl = url + getUrlParams(params);
        OkHttpUtils.delete().url(invokeUrl)
                .tag(TAG).build()
                .execute(new HttpHandler(listener));
    }


    @Override
    public void postAsynchronousRequests(final String url, final Map<String, String> headersMap, final Map<String, String> paramsMap, final NetworkListener listener) {
        Log.e("xsy", "postAsynchronousRequests url" + url);
        OkHttpUtils.post()
                .url(url)
                .params(paramsMap)
                .headers(headersMap)
                .build()
                .execute(new HttpHandler(listener));
    }


    private String getUrlParams(Map<String, String> bodyParams) {
        if (bodyParams == null || bodyParams.isEmpty()) {
            return "";
        }
        StringBuilder content = new StringBuilder("?");
        Set<String> bodyKeys = bodyParams.keySet();
        for (Iterator<String> it = bodyKeys.iterator(); it.hasNext(); ) {
            String headKey = it.next();
            content.append(headKey);
            content.append("=");
            content.append(bodyParams.get(headKey));
            content.append("&");
        }
        if (content.length() > 0) {
            content.deleteCharAt(content.length() - 1);
        }
        return content.toString();
    }


}
