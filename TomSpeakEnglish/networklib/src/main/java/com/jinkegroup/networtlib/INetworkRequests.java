package com.jinkegroup.networtlib;

import java.util.Map;

/**
 * Created by davy on 16/6/14.
 */
public interface INetworkRequests {

    void stopNetworkRequests();

    void postNetworkRequests(final String url, final Map<String, String> paramsMap, final NetworkListener listener);

    void getNetworkRequests(final String url, Map<String, String> params, final NetworkListener listener);

    void postAsynchronousRequests(String url, final Map<String, String> headersMap, final Map<String, String> paramsMap, final NetworkListener listener);

    void putNetworkRequests(final String url, final Map<String, String> params, final NetworkListener listener);

    void deleteNetworkRequests(final String url, final Map<String, String> params, final NetworkListener listener);

}
