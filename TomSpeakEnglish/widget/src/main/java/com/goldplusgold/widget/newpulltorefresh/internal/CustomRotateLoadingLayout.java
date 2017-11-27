/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations
 * under
 * the License.
 *******************************************************************************/
package com.goldplusgold.widget.newpulltorefresh.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.goldplusgold.widget.R;
import com.goldplusgold.widget.newpulltorefresh.PullToRefreshBase.Mode;
import com.goldplusgold.widget.newpulltorefresh.PullToRefreshBase.Orientation;

public class CustomRotateLoadingLayout extends LoadingLayoutAdapter {

    static final int ROTATION_ANIMATION_DURATION = 1200;

    private final Animation mRotateAnimation;

    public CustomRotateLoadingLayout(Context context, Mode mode, Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);

        mRotateAnimation = AnimationUtils.loadAnimation(context, R.anim.reverse_anim);
        mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
    }


    protected void onPullImpl(float scaleOfLayout) {
    }

    @Override
    protected void refreshingImpl() {

    }

    @Override
    protected void resetImpl() {
        mHeaderImage.clearAnimation();
    }


    @Override
    protected void pullToRefreshImpl() {
        // NO-OP
        mHeaderImage.clearAnimation();
    }

    @Override
    protected void releaseToRefreshImpl() {
        // NO-OP
        mHeaderImage.startAnimation(mRotateAnimation);
    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.pullup_icon_big;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {

    }

}
