package com.goldplusgold.widget.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/9/16.
 */
public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
    private int spanCount;
    private int spacing;
    private int headerCount;
    private boolean includeEdge;

    public ItemOffsetDecoration(int spanCount, int spacing, int headerCount, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.headerCount = headerCount < 0 ? 0 : headerCount;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view); // item position

        // skip header items
        if (headerCount > 0 && position < headerCount - 1) {
            return;
        }

        position = position - headerCount;

        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }

    }
}