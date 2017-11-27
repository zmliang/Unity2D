package com.jinke.group.baselib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public abstract class BaseListViewAdapter<T extends Object> extends BaseAdapter {

    protected Context mContext;
    protected ArrayList<T> mArrayList;
    protected WeakReference<Fragment> mFragmentRef;

    public BaseListViewAdapter(Context ctx) {
        mContext = ctx;
    }

    public Context getContext() {
        return mContext;
    }

    public void setFragmentRef(Fragment fragment) {
        mFragmentRef = new WeakReference<Fragment>(fragment);
    }

    public Fragment getFragment() {
        if (mFragmentRef != null) {
            return mFragmentRef.get();
        }
        return null;
    }

    public void addArrayList(ArrayList<T> tArray) {
        if (mArrayList == null) {
            return;
        }

        mArrayList.addAll(tArray);
        notifyDataSetChanged();
    }

    public void addArrayList(int index, ArrayList<T> tArray) {
        if (mArrayList == null) {
            return;
        }

        mArrayList.addAll(index, tArray);
        notifyDataSetChanged();
    }

    public ArrayList<T> getArrayList() {
        return mArrayList;
    }

    public void setArrayList(ArrayList<T> pArray) {
        mArrayList = pArray;

        notifyDataSetChanged();
    }

    public void setArrayList(T[] pArray){
        if (mArrayList == null)
            mArrayList = new ArrayList<T>();
        mArrayList.clear();
        for (T t:pArray){
            mArrayList.add(t);
        }
        notifyDataSetChanged();
    }

    public void removeItem(T object) {
        mArrayList.remove(object);
        notifyDataSetInvalidated();
    }

    public void removeAll() {
        if (mArrayList == null || mArrayList.size() == 0) {
            return;
        }
        mArrayList.clear();
        notifyDataSetChanged();
    }

    protected LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(getContext());
    }

    @Override
    public int getCount() {
        if (mArrayList != null) {
            return mArrayList.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (mArrayList != null) {
            if (position >= 0 && position < mArrayList.size()) {
                return mArrayList.get(position);
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        if (getFragment() != null) {
            getFragment().startActivityForResult(intent, requestCode);
        } else {
            ((Activity) getContext()).startActivityForResult(intent, requestCode);
        }
    }
}
