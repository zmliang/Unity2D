package com.goldplusgold.widget.pulltorefresh.pullableView;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/8/22.
 */

public class PullableRecyclerView extends RecyclerView implements PullAble {
    private boolean isCanPullDown = true;
    private boolean isCanPullUp = true ;

    public PullableRecyclerView(Context context) {
        super(context);
    }

    public PullableRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean canPullDown() {
        if(isCanPullDown) {
            if (getChildCount() == 0) {
                return true;
            } else if (getChildAt(0).getTop() >= 0) {
                if (getLayoutManager() instanceof LinearLayoutManager) {
                    int firstVisibleItem = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                    if (firstVisibleItem == 0) {
                        return true;
                    }
                } else if (getLayoutManager() instanceof GridLayoutManager) {
                    int firstVisibleItem = ((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                    if (firstVisibleItem == 0) {
                        return true;
                    }
                }
            }
            return false;
        }else{
            return false ;
        }
    }

    @Override
    public boolean canPullUp() {
        if(isCanPullUp) {
            RecyclerView.LayoutManager lm = getLayoutManager();
            int mLastVisiblePosition = getLastVisibleItemPosition();
            int count = getAdapter().getItemCount();
            if (0 == count) {
                // 没有item的时候可以上拉加载
                return true;
            } else if (mLastVisiblePosition == (count - 1)) {
                // 滑到底部了可以上拉加载
                if (lm.findViewByPosition(count - 1).getBottom() <= getMeasuredHeight()) {
                    return true;
                }
            }
            return false;
        }else{
            return false ;
        }
    }

    /**
     * 获取底部可见项的位置
     *
     * @return
     */
    private int getLastVisibleItemPosition() {
        RecyclerView.LayoutManager lm = getLayoutManager();
        int lastVisibleItemPosition = 0;
        if (lm instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) lm).findLastVisibleItemPosition();
        } else if (lm instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) lm).findLastVisibleItemPosition();
        }
        return lastVisibleItemPosition;
    }

    public boolean isCanPullDown() {
        return isCanPullDown;
    }

    public void setCanPullDown(boolean canPullDown) {
        isCanPullDown = canPullDown;
    }

    public boolean isCanPullUp() {
        return isCanPullUp;
    }

    public void setCanPullUp(boolean canPullUp) {
        isCanPullUp = canPullUp;
    }
}
