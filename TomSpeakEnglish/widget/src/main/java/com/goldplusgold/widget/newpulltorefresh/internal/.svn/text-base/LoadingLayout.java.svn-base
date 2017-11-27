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

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.goldplusgold.widget.newpulltorefresh.ILoadingLayout;

public interface LoadingLayout extends ILoadingLayout {

    void setHeight(int height);

    void setWidth(int width);

    int getContentSize();

    void hideAllViews();

    void onPull(float scaleOfLayout);

    void pullToRefresh();

    void refreshing();

    void releaseToRefresh();

    void reset();

    void showInvisibleViews();

    int getVisibility();

    void setVisibility(int invisible);

    void addSelf(ViewGroup parent, ViewGroup.LayoutParams lp);

    void addSelf(ViewGroup parent, int index, ViewGroup.LayoutParams lp, OnSuperAddCallback callback);

    void removeSelf(ViewGroup parent);

    ViewParent getParent();

    interface OnSuperAddCallback {
        void addView(View view, int index, ViewGroup.LayoutParams lp);
    }

}
