package com.goldplusgold.widget.newpulltorefresh.internal;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public abstract class AbstractFrameLoadingLayout extends FrameLayout implements LoadingLayout {

    public AbstractFrameLoadingLayout(Context context) {
        super(context);
    }

    public void addSelf(ViewGroup parent, android.view.ViewGroup.LayoutParams lp) {
        parent.addView(this, lp);
    }

    public void addSelf(ViewGroup parent, int index, ViewGroup.LayoutParams lp, OnSuperAddCallback callback) {
        callback.addView(this, index, lp);
    }

    public void removeSelf(ViewGroup parent) {
        parent.removeView(this);
    }

    public final void setHeight(int height) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = height;
        requestLayout();
    }

    public final void setWidth(int width) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.width = width;
        requestLayout();
    }

}
