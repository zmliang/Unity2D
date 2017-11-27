package com.goldplusgold.widget.pulltorefresh.pullableView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class PullAbleListView extends ListView implements PullAble {

    public PullAbleListView(Context context) {
        super(context);
    }

    public PullAbleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullAbleListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        if (getCount() == 0) {
            return true;
        } else if (getFirstVisiblePosition() == 0) {
            if (getChildAt(0) != null && getChildAt(0).getTop() >= 0)
                return true;
        }
        return false;
    }

    @Override
    public boolean canPullUp() {
        if (getCount() == 0) {
            return false;
        } else if (getLastVisiblePosition() == (getCount() - 1)) {
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(getLastVisiblePosition() - getFirstVisiblePosition())
                    .getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;
    }

}
