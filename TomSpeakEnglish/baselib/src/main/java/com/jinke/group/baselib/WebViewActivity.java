package com.jinke.group.baselib;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;



/**
 * Created by admin on 2017/5/10.
 */

public class WebViewActivity extends BaseActivity {
    WebViewFragment webViewFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview_layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("url")) {
            String url = getIntent().getStringExtra("url");
            String title = getIntent().getStringExtra("title");
            switchFragment(url, title);
        }
    }

    @Override
    protected void back() {

    }

    @Override
    public void onBackPressed() {
        if (webViewFragment != null) {
            webViewFragment.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void switchFragment(String url, String title) {
        webViewFragment = WebViewFragment.newInstance(url, title);
        FragmentManagerUtil.getInstance().openFragment(this, webViewFragment, true);
    }

}
