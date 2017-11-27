package com.goldplusgold.widget.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


/* *
 * Created by Administrator on 2017/6/22.
 */

public class PasswordTextView extends AppCompatTextView {
    onClickPasteListener mListener;

    public PasswordTextView(Context context) {
        super(context);
    }

    public PasswordTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        switch (id) {
            case android.R.id.paste:
                mListener.onclickPaste();
                break;
        }

        return false;
    }

    public interface onClickPasteListener {
        void onclickPaste();
    }

    public void setClickOnPasteListener(onClickPasteListener listener) {
        this.mListener = listener;
    }
}
