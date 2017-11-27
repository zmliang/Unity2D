package com.jinkegroup.edu.se.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.jinke.group.baselib.BaseActivity;
import com.jinke.group.baselib.BaseFragment;
import com.jinkegroup.edu.se.R;
import java.util.Stack;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/25
 * CopyRight:  JinkeGroup
 */

public abstract class BaseFragmentActivity extends BaseActivity{

    FragmentManager fragmentManager;
    BaseFragment currentFrag;
    protected Stack<BaseFragment> fragmentStack = new Stack<BaseFragment>();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void back() {
        if (fragmentStack.size()<=1) {
            fragmentStack.clear();
            fragmentStack = null;
            finish();
        }else if (fragmentStack.size()>1){
            removeFragment();
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_container;
    }

    //replace 的时候栈里只保留当前的Fragment
    public void replaceFragment(BaseFragment fragment){
        if (!fragmentStack.isEmpty()) {
            fragmentStack.clear();
        }
        if (fragmentManager!=null){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container,fragment);
            transaction.commitAllowingStateLoss();
            currentFrag = fragment;
            fragmentStack.push(currentFrag);
        }
    }

    private void removeFragment(){
        BaseFragment fragment = fragmentStack.pop();
        currentFrag = fragmentStack.peek();
        if(fragmentManager!=null && currentFrag!=null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(fragment);
            transaction.show(currentFrag);
            transaction.commitAllowingStateLoss();
        }
    }


    public void addFragment(BaseFragment fragment){
        if (fragmentManager!=null && fragment!=null){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (currentFrag!=null)
                transaction.hide(currentFrag);
            transaction.add(R.id.fragment_container,fragment);
            transaction.commitAllowingStateLoss();
            currentFrag = fragment;
            fragmentStack.push(currentFrag);
        }
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        fragmentManager = null;
        currentFrag = null;
    }

}
