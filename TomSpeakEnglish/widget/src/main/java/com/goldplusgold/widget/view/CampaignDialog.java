package com.goldplusgold.widget.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.goldplusgold.widget.R;


public class CampaignDialog extends Dialog implements View.OnClickListener {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private String mPopupImageUrl;
    private String mLinkUrl;

    private CampaignDialogListener mCampaignDialogListener;

    public interface CampaignDialogListener {
        void onclickImage(String mLinkUrl);
    }


    public CampaignDialog(Context context, String popupImageUrl, String linkUrl, int theme, CampaignDialogListener listener) {
        super(context, theme);
        this.mContext = context;
        this.mPopupImageUrl = popupImageUrl;
        this.mLinkUrl = linkUrl;
        this.mCampaignDialogListener = listener;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initViews();
    }

    private void initViews() {
        View rootView = mLayoutInflater.inflate(R.layout.dialog_activity, null);
        setContentView(rootView);
        ImageView ivPopupImage = rootView.findViewById(R.id.popup_image);
        Glide.with(mContext).load(mPopupImageUrl).placeholder(R.drawable.popup_placeholder).error(R.drawable.popup_placeholder).into(ivPopupImage);
        ivPopupImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mCampaignDialogListener != null) {
            if (v.getId() == R.id.popup_image) {
                mCampaignDialogListener.onclickImage(mLinkUrl);
                dismiss();
            }
        }
    }

}
