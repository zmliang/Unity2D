package com.jinkegroup.edu.se.activity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.jinke.group.baselib.BaseActivity;
import com.jinkegroup.edu.se.R;
import com.jinkegroup.edu.se.utils.SharedPreferenceUtils;
import com.jinkegroup.networtlib.NetworkRequestsUtil;

import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/24
 * CopyRight:  JinkeGroup
 */

public class SplashActivity extends BaseActivity {

    private boolean isFirst = true;
    private static final String KEY = "isFirst";

    private final int SPLASH_DURATION = 2000;

    private Handler mHandler;

    @BindView(R.id.splash_animation)
    LottieAnimationView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        isFirst = SharedPreferenceUtils.getBooleanData(KEY,true);
        mHandler = new Handler();
        splash.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    protected void back() {
        //nothing
    }

    @Override
    public int getLayoutId(){
        return R.layout.splash;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}
