package com.jinkegroup.edu.se.fragment;

import com.jinke.group.baselib.BaseFragment;
import com.jinke.group.baselib.presenter.BasePresenter;


/**
 * Author   :  Tomcat
 * Date     :  2017/10/25
 * CopyRight:  JinkeGroup
 */

public abstract class TomBaseFragment<T extends BasePresenter> extends BaseFragment {
    protected T presenter;

    protected T getPresenter(){
        return presenter;
    }

    public TomBaseFragment(){
    }

    public void setPresenter(T t){
        this.presenter = t;
    }


    @Override
    public void onDestroy(){
        super.onDestroy();

        if (presenter!=null){
            presenter.destroy();
        }
        presenter = null;
    }


}
