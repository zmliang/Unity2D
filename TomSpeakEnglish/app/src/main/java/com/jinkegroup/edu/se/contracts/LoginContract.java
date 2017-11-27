package com.jinkegroup.edu.se.contracts;

import android.content.DialogInterface;
import android.view.View;

import com.jinke.group.baselib.presenter.BasePresenter;
import com.jinke.group.baselib.view.BaseView;
import com.jinkegroup.edu.se.utils.data.User;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/23
 * CopyRight:  JinkeGroup
 */

public interface LoginContract {
    interface LoginView extends BaseView<Presenter>{
        void showDialog();
        void dismissDialog();
        void loginSuccess();
        void loginFailed();
    }

    interface Presenter extends BasePresenter{
        void setView(LoginView view);
        boolean loginState();
        void doLogin();
        User getUser();

    }

    interface onLoginListener {
        void LoginFailure(String error);
        void LoginSuccess(User user);
    }

}
