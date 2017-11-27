package com.goldplusgold.widget.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.goldplusgold.widget.R;


public class TipPopupView extends Dialog {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private View mRootView;
    private TextView mTipContentTextView;
    private TextView mTipTitleTextView;
    private String tipContent;
    private String tipTitle;

    public TipPopupView(@NonNull Context context, @Nullable String tipTitle, @Nullable String tipContent, int theme) {
        super(context, theme);
        mContext = context;
        this.tipTitle = tipTitle;
        this.tipContent = tipContent;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initViews();
    }


    private void initViews() {
        mRootView = mLayoutInflater.inflate(R.layout.popup_view_tip, null);
        setContentView(mRootView);
        mTipContentTextView = mRootView.findViewById(R.id.tip_text);
        mTipTitleTextView = mRootView.findViewById(R.id.tip_title);
        mTipContentTextView.setText(TextUtils.isEmpty(tipContent) ? "" : tipContent);
        mTipTitleTextView.setText(TextUtils.isEmpty(tipTitle) ? mContext.getString(R.string.small_tip) : tipTitle);
        setCancelable(true);
    }

}
