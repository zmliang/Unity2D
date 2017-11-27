package com.jinkegroup.edu.se.activity;

import android.os.Bundle;
import android.view.View;

import com.jinkegroup.edu.se.contracts.LoginContract;
import com.jinkegroup.edu.se.contracts.LoginPresenter;
import com.jinkegroup.edu.se.fragment.LoginFragment;
import com.jinkegroup.edu.se.fragment.ParentCenterFragment;
import com.jinkegroup.edu.se.utils.data.User;
import com.jinkegroup.supportlib.util.Logger;


/**
 * Author   :  Tomcat
 * Date     :  2017/10/23
 * CopyRight:  JinkeGroup
 */

public class LoginActivity extends BaseFragmentActivity implements LoginContract.onLoginListener{
    private final String TAG = LoginActivity.class.getSimpleName();

    LoginContract.Presenter mPresenter;

    LoginFragment loginFragment;
    ParentCenterFragment parentCenterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        loginFragment = new LoginFragment();
        mPresenter = new LoginPresenter(loginFragment);
        parentCenterFragment = new ParentCenterFragment();
        mPresenter.create();

        parentCenterFragment.setPresenter(mPresenter);
        loginFragment.setPresenter(mPresenter);
        loginFragment.setLoginListener(this);

        loginState();

    }


    private void loginState(){
        if(mPresenter.loginState()){
            //add parentCenter_Fragment
            replaceFragment(parentCenterFragment);
        }else {
            //add Login_Fragment
            replaceFragment(loginFragment);
        }
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mPresenter!=null) {
            mPresenter.destroy();
            mPresenter = null;
        }
    }

    @Override
    public void LoginFailure(String error) {

    }

    @Override
    public void LoginSuccess(User user) {
        Logger.i(TAG,user.toString());
        replaceFragment(parentCenterFragment);
    }




}
