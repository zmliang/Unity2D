package com.goldplusgold.widget.horizontallistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 */
public class SyncHorizontalScrollView extends HorizontalScrollView {

    private View mView;

    public SyncHorizontalScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public SyncHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mView != null) {
            mView.scrollTo(l, t);
        }
        if (this.l != null) {
            this.l.onScroll(l, t);
        }
    }


    /**
     * @param view
     */
    public void setScrollView(View view) {
        mView = view;
    }

    private OnScrollListenter l;

    public void setOnScrollListenter(OnScrollListenter l) {
        this.l = l;
    }

   public interface OnScrollListenter {
        void onScroll(int x, int y);
    }

}