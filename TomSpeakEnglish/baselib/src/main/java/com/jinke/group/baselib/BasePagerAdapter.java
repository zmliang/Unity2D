package com.jinke.group.baselib;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

/**
 * Created by admin on 2017/5/8.
 */

public class BasePagerAdapter<T extends Fragment> extends android.support.v4.app.FragmentStatePagerAdapter {
    protected Context mContext;
    private ArrayList<T> mArrayList;

    public BasePagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
        this.mArrayList = new ArrayList<>();
    }


    @Override
    public Fragment getItem(int position) {
        if (mArrayList != null) {
            if (position >= 0 && position < mArrayList.size()) {
                return mArrayList.get(position);
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        if (mArrayList != null) {
            return mArrayList.size();
        }
        return 0;
    }

    public void setArrayList(ArrayList<T> mArrayList) {
        this.mArrayList = mArrayList;
        notifyDataSetChanged();
    }

    public void add(T t) {
        this.mArrayList.add(t);
    }

}
