package com.jinkegroup.edu.se.fragment;

import android.view.View;
import android.widget.Button;

import com.jinkegroup.edu.se.R;
import com.jinkegroup.edu.se.activity.BaseFragmentActivity;
import com.jinkegroup.edu.se.contracts.LoginContract;
import com.jinkegroup.supportlib.util.Logger;

import butterknife.BindView;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/25
 * CopyRight:  JinkeGroup
 */

public class LoginFragment extends TomBaseFragment<LoginContract.Presenter> implements LoginContract.LoginView{
    private final String TAG = LoginFragment.class.getSimpleName();

    @BindView(R.id.login_wechat)
    Button wechatLoginBtn;

    private LoginContract.onLoginListener loginListener;

    @Override
    protected void initView(){
        super.initView();
        wechatLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (presenter!=null) {
                    presenter.doLogin();
                    Logger.i(TAG,"presenter is not null");
                }
            }
        });
    }

    public void setLoginListener(LoginContract.onLoginListener listener){
        loginListener = listener;
    }


    @Override
    public int getLayoutId() {
        return R.layout.wechat_login;
    }

    @Override
    public void showDialog() {
        showLoadingControl();
    }

    @Override
    public void dismissDialog() {
        dismisLoadingControl();
    }

    @Override
    public void loginSuccess() {
        //登陆成功
        showToastMessage(R.string.login_success,3000);
        if (loginListener!=null)
            loginListener.LoginSuccess(presenter.getUser());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        loginListener = null;
        Logger.i(TAG,"onDestroy");
    }

    @Override
    public void loginFailed() {
        if (loginListener!=null)
            loginListener.LoginFailure("error");
    }
}
