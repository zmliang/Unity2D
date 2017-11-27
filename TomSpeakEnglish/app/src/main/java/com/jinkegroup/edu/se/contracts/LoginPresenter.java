package com.jinkegroup.edu.se.contracts;

import android.os.Handler;
import android.support.design.widget.TabLayout;

import com.jinkegroup.edu.se.utils.data.User;
import com.jinkegroup.supportlib.util.Logger;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/23
 * CopyRight:  JinkeGroup
 */

public class LoginPresenter implements LoginContract.Presenter {
    private final String TAG = LoginPresenter.class.getSimpleName();

    private LoginContract.LoginView mView;
    private Handler mHandler;
    private User mUser;

    public LoginPresenter(LoginContract.LoginView view){
        mView = view;
        init();
    }

    private void init(){
        mHandler = new Handler();
        mUser = new User();
    }

    @Override
    public void create() {

    }

    @Override
    public void setView(LoginContract.LoginView view) {
        mView = view;
    }

    @Override
    public boolean loginState() {
        //判断登陆状态
        return false;
    }

    @Override
    public void doLogin() {
        if (mView == null)
            return;
        Logger.i(TAG,"doLogin...");
        mView.showDialog();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.dismissDialog();
                mView.loginSuccess();
            }
        },3000);
    }

    @Override
    public void destroy() {
        mHandler = null;
        mView = null;
    }

    @Override
    public User getUser() {
        return mUser;
    }
}
