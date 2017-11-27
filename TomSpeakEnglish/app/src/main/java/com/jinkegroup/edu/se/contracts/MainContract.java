package com.jinkegroup.edu.se.contracts;

import android.content.DialogInterface;
import android.view.View;

import com.jinke.group.baselib.presenter.BasePresenter;
import com.jinke.group.baselib.view.BaseView;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/24
 * CopyRight:  JinkeGroup
 */

public interface MainContract {
    interface MainView extends BaseView<Presenter>{
        void enter(int id);

    }

    interface Presenter extends BasePresenter,View.OnClickListener {

    }

}
