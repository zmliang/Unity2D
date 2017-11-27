package com.goldplusgold.widget.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.goldplusgold.widget.R;

/**
 * Created by Administrator on 2017/9/7.
 */

public class LogisticsDialog extends Dialog {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private View mRootView;
    private TextView mTipTitleTextView;
    private TextView mLogisticsNameTextView ;
    private TextView mLogisticsNumTextView ;
    private TextView mCopyTextView ;
    private TextView mContinueTextView ;

    private String tipTitle;
    private String logisName;
    private String logisNum ;
    private ClipboardManager cm ;



    public LogisticsDialog(@NonNull Context context,
                           @Nullable String logisName ,
                           @Nullable String logisNum ) {
        super(context, R.style.MyDialog);
        mContext = context;
        this.logisName = logisName ;
        this.logisNum = logisNum ;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        initViews();
    }


    private void initViews() {
        mRootView = mLayoutInflater.inflate(R.layout.dialog_logistics, null);
        setContentView(mRootView);
        mTipTitleTextView = mRootView.findViewById(R.id.tip_title);
        //mTipTitleTextView.setText(TextUtils.isEmpty(tipTitle) ? mContext.getString(R.string.small_tip) : tipTitle);

        mLogisticsNameTextView = mRootView.findViewById(R.id.logistics_name_tv);
        mLogisticsNumTextView = mRootView.findViewById(R.id.logistics_num_tv);
        mCopyTextView = mRootView.findViewById(R.id.copy_tv);
        mContinueTextView = mRootView.findViewById(R.id.btn_continue);

        mLogisticsNameTextView.setText(logisName);
        mLogisticsNumTextView.setText(logisNum);
        mCopyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cm.setText(logisNum);
                Toast.makeText(mContext , "复制成功" , Toast.LENGTH_SHORT).show();
            }
        });
        mContinueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        setCancelable(true);
    }

    public void setTitle(String title){
        mTipTitleTextView.setText(title);
    }

}
