package com.jinkegroup.edu.se.contracts;

import android.view.View;

import com.jinkegroup.edu.se.R;
import com.jinkegroup.supportlib.util.Logger;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/24
 * CopyRight:  JinkeGroup
 */

public class MainPresenter implements MainContract.Presenter {
    private final String TAG = MainPresenter.class.getSimpleName();
    private MainContract.MainView mMainView;


    public MainPresenter(MainContract.MainView view){
        mMainView = view;
    }

    @Override
    public void onClick(View view) {
        mMainView.enter(view.getId());

    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {
        mMainView = null;
    }

}
