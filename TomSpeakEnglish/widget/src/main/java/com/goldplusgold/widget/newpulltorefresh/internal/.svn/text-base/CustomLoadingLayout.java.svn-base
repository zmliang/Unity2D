package com.goldplusgold.widget.newpulltorefresh.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import com.goldplusgold.widget.R;
import com.goldplusgold.widget.newpulltorefresh.PullToRefreshBase;

/**
 * Created by admin on 2017/6/19.
 */

public class CustomLoadingLayout extends LoadingLayoutAdapter {
    private final AnimationDrawable animationDrawable;

    public CustomLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);

        mHeaderImage.setImageResource(R.drawable.logo_refreshing);
        animationDrawable = (AnimationDrawable) mHeaderImage.getDrawable();
    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.logo_refreshing01;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {

    }

    @Override
    protected void onPullImpl(float scaleOfLayout) {
        float alpha = scaleOfLayout * 0.5f;
        mHeaderImage.setAlpha(alpha);
    }

    @Override
    protected void pullToRefreshImpl() {

    }

    @Override
    protected void releaseToRefreshImpl() {

    }

    @Override
    protected void refreshingImpl() {
        animationDrawable.start();
    }

    @Override
    protected void resetImpl() {
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
        mHeaderImage.clearAnimation();
    }

}
