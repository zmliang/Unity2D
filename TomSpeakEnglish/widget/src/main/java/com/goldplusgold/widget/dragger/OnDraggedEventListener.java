package com.goldplusgold.widget.dragger;


public interface OnDraggedEventListener {

    int EC_DRAGGED_SNAP_SCREEN = 1;
    int EC_DRAGGED_DELETE = 2;

    void onDraggedEventAction(int eventCode, int screenToSnap);

}
