package com.jinkegroup.sharesdk.widget;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.goldplusgold.sharesdk.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class SharePopupView extends PopupWindow implements OnClickListener, OnTouchListener {
	private Context mContext;
	private Activity mActivity;
	private LayoutInflater mInflater;
	private View rootView;
	private TextView btn_cancle;
	private TextView btn_share_wechat;
	private TextView btn_share_wechatfriends;
	private TextView btn_share_sina;
	private TextView btn_share_qq;
	private TextView btn_share_qqzone;
	private TextView btn_share_sms;
	private String mUrl;
	private String mShareTitle;
	private String mShareDesc;
	private String mImageUrl;
	 

	public SharePopupView(Context context, Activity activity, String url, String title, String desc, String imageUrl) {
		this.mContext = context;
		mActivity = activity;
		mUrl = url;
		mShareTitle = title;
		mShareDesc = desc;
		mImageUrl = imageUrl;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initView();
	}

	public void addDatas(String url) {
		mUrl = url;
	}

	private void initView() {
		rootView = mInflater.inflate(R.layout.shareview, null);
		setContentView(rootView);
		btn_cancle = (TextView) rootView.findViewById(R.id.btn_cancle);
		btn_share_wechat = (TextView) rootView.findViewById(R.id.btn_share_wechat);
		btn_share_wechatfriends = (TextView) rootView.findViewById(R.id.btn_share_wechatfriends);
		btn_share_sina = (TextView) rootView.findViewById(R.id.btn_share_sina);
		btn_share_qq = (TextView) rootView.findViewById(R.id.btn_share_qq);
		btn_share_qqzone = (TextView) rootView.findViewById(R.id.btn_share_qqzone);
		btn_share_sms = (TextView) rootView.findViewById(R.id.btn_share_sms);
		setFocusable(true);
		setAnimationStyle(R.style.AnimBottom);
		setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new BitmapDrawable());
		rootView.setOnTouchListener(this);
		btn_cancle.setOnClickListener(this);
		btn_share_qq.setOnClickListener(this);
		btn_share_qqzone.setOnClickListener(this);
		btn_share_sina.setOnClickListener(this);
		btn_share_sms.setOnClickListener(this);
		btn_share_wechat.setOnClickListener(this);
		btn_share_wechatfriends.setOnClickListener(this);
		btn_share_qq.setOnClickListener(this);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int height = rootView.findViewById(R.id.share_pop_layout).getTop();
		int y = (int) event.getY();
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (y < height) {
				dismiss();
			}
		}
		return true;
	}

	@Override
	public void onClick(View v) {

		UMWeb web = new UMWeb(mUrl);
		web.setTitle(mShareTitle);
		web.setDescription(mShareDesc);
		int i = v.getId();
		if (i == R.id.btn_cancle) {
			dismiss();

		} else if (i == R.id.btn_share_wechat) {
			if (TextUtils.isEmpty(mImageUrl)) {

				web.setThumb(new UMImage(mActivity, R.drawable.icon_share));

				new ShareAction(mActivity).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
						.withText(mShareDesc)
						.withMedia(web).share();
			} else {
				web.setThumb(new UMImage(mActivity, R.drawable.icon_share));

				new ShareAction(mActivity).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
						.withText(mShareDesc).withMedia(web).share();
			}
			dismiss();

		} else if (i == R.id.btn_share_wechatfriends) {
			if (TextUtils.isEmpty(mImageUrl)) {

				web.setThumb(new UMImage(mActivity, R.drawable.icon_share));

				new ShareAction(mActivity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
						.withMedia(web).share();
			} else {

				web.setThumb(new UMImage(mActivity, mImageUrl));

				new ShareAction(mActivity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
						.withText(mShareDesc).withMedia(web)
						.share();
			}
			dismiss();

		} else if (i == R.id.btn_share_qq) {
			if (TextUtils.isEmpty(mImageUrl)) {

				web.setThumb(new UMImage(mActivity, R.drawable.icon_share));
				new ShareAction(mActivity).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener).withText(mShareDesc)
						.withMedia(web)
						.share();
			} else {

				web.setThumb(new UMImage(mActivity, mImageUrl));
				new ShareAction(mActivity).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener).withText(mShareDesc)
						.withMedia(web)
						.share();
			}
			dismiss();

		} else if (i == R.id.btn_share_qqzone) {
			if (TextUtils.isEmpty(mImageUrl)) {

				web.setThumb(new UMImage(mActivity, R.drawable.icon_share));

				new ShareAction(mActivity).setPlatform(SHARE_MEDIA.QZONE).setCallback(umShareListener)
						.withText(mShareDesc)
						.withMedia(web).share();
			} else {

				web.setThumb(new UMImage(mActivity, mImageUrl));

				new ShareAction(mActivity).setPlatform(SHARE_MEDIA.QZONE).setCallback(umShareListener)
						.withText(mShareDesc).withMedia(web)
						.share();
			}
			dismiss();

		} else if (i == R.id.btn_share_sms) {

			new ShareAction(mActivity).setPlatform(SHARE_MEDIA.SMS).setCallback(umShareListener).withText(mShareDesc)
					.share();
			dismiss();

		} else if (i == R.id.btn_share_sina) {
			new ShareAction(mActivity).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener).withText(mShareDesc)
					.share();
			dismiss();

		} else {
		}
	}

	private UMShareListener umShareListener = new UMShareListener() {
		@Override
		public void onStart(SHARE_MEDIA share_media) {

		}

		@Override
		public void onResult(SHARE_MEDIA platform) {
			// Toast.makeText(mContext, platform + " 分享成功啦",
			// Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			Toast.makeText(mContext, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {
			// Toast.makeText(mContext, platform + " 分享取消了",
			// Toast.LENGTH_SHORT).show();
		}
	};
}
