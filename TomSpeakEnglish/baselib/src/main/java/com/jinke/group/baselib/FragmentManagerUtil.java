package com.jinke.group.baselib;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


/**
 * Created by admin on 2017/2/6.
 */

public class FragmentManagerUtil {
    private static FragmentManagerUtil INSTANCE;

    private FragmentManagerUtil() {

    }

    public static FragmentManagerUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FragmentManagerUtil();
        }
        return INSTANCE;
    }

    public void openFragment(FragmentActivity activity, Fragment fragment) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.commitAllowingStateLoss();
    }


    public void openFragment(FragmentActivity activity, Fragment fragment, boolean isAddToBackStack) {
        if (activity == null || activity.isFinishing()) {
            return;
        }


        FragmentManager fm = activity.getSupportFragmentManager();
        if (fragment.isAdded()) {
            fm.beginTransaction().remove(fragment).commit();
        }

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        if (isAddToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commitAllowingStateLoss();
    }

    public void closeFragment(FragmentActivity activity, Fragment fragment, boolean isCloseActivity, boolean isPopBackStack) {
        if (activity == null) {
            return;
        }
        if (isPopBackStack) {
            activity.getSupportFragmentManager().popBackStack();
        }
        closeFragment(activity, fragment, isCloseActivity);
    }


    public void closeFragment(FragmentActivity activity, Fragment fragment, boolean isCloseActivity) {
        if (activity == null) {
            return;
        }
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.remove(fragment);
        transaction.commitAllowingStateLoss();
        if (isCloseActivity) {
            activity.finish();
        }
    }


}
