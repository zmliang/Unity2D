package com.jinke.group.baselib;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.goldplusgold.widget.view.TitleView;
import com.jinkegroup.supportlib.util.DeviceNetworkUtil;


/**
 * Created by admin on 2016/11/18.
 */

public class WebViewFragment extends BaseFragment implements View.OnClickListener {
    public View mView;
    protected WebView mWebView;
    protected WVJBWebViewClient mWebViewClient;
    protected TitleView mTitleView;

    public static WebViewFragment newInstance(String url, String title) {

        Bundle args = new Bundle();
        args.putString("url", url);
        args.putString("title", title);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(getLayoutId(), container, false);
        init(mView);
        Bundle args = getArguments();
        if (args != null && !args.isEmpty()) {
            String url = args.getString("url");
            String title = args.getString("title");
            if (savedInstanceState != null) {
                mWebView.restoreState(savedInstanceState);
            } else {
                mWebView.loadUrl(url);
            }
            Log.e("onPageFinished", "url" + url);
            if (title != null) {
                mTitleView.getTitletextView().setText(title);
            }
        }

        return mView;
    }

    public void init(View view) {
        mTitleView = view.findViewById(R.id.title_view_browser);
        mTitleView.getTitletextView().setVisibility(View.VISIBLE);
        mTitleView.getBackView().setVisibility(View.VISIBLE);
        mTitleView.getBackView().setOnClickListener(this);
        mTitleView.getBackView().setOnClickListener(this);
        mWebView = view.findViewById(R.id.webview_browser);
        initSetting(mWebView.getSettings());
        mWebViewClient = getBaseWebViewClient(mWebView);
        mWebView.setWebViewClient(mWebViewClient);
    }

    public int getLayoutId() {
        return R.layout.common_browser;
    }


    public void initSetting(WebSettings settings) {
        //优化显示
        if (Build.VERSION.SDK_INT >= 19) {
            mWebView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            mWebView.getSettings().setLoadsImagesAutomatically(false);
        }
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        if (DeviceNetworkUtil.isNetworkAvailable(getActivity())) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == mTitleView.getBackView().getId()) {
            goBack();
        }
    }

    public void goBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            getActivity().finish();
        }
    }

    public BaseWebViewClient getBaseWebViewClient(WebView webView) {
        return new MyWebViewClient(webView);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mWebView.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        mWebView.destroy();
        super.onDestroy();
    }

    class MyWebViewClient extends BaseWebViewClient {

        public MyWebViewClient(WebView webView) {
            super(webView);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e("onPageFinished", "url" + url);
            mTitleView.getTitletextView().setText(view.getTitle());

        }

    }
}
