package com.goldplusgold.widget.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.goldplusgold.widget.R;
import com.goldplusgold.widget.other.Interface;


public class ReBuyRemindPopupView extends PopupWindow implements OnClickListener, OnTouchListener {
	private Context mContext;
	private View rootView;
	private LayoutInflater mInflater;
	private String mMsg;
	private String mGoldPrice;
	private int mType;
	private Interface.OnValueSetClickListener mListener;

	public ReBuyRemindPopupView(Context context, String message, String goldPrice, int type) {
		this.mContext = context;
		this.mMsg = message;
		this.mGoldPrice = goldPrice;
		this.mType = type;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initView();
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		rootView = mInflater.inflate(R.layout.rebuy_remind_popup_view, null);
		setContentView(rootView);
		TextView mCurrentGoldPrice = rootView.findViewById(R.id.current_gold_price);
		mCurrentGoldPrice.setText("当前金价为" + mGoldPrice + "元/克");
		TextView mTextOne = rootView.findViewById(R.id.text_one);
		mTextOne.setText(mMsg);
		Button btnCancel = rootView.findViewById(R.id.btn_cancle);
		Button btnContinue = rootView.findViewById(R.id.btn_continue);
		if (mType == 1) {
			btnCancel.setText("取消");
			btnContinue.setText("继续");
		}
		setFocusable(true);
		setWidth(ViewGroup.LayoutParams.FILL_PARENT);
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		int color = mContext.getResources().getColor(R.color.popup_root_view_bg);
		ColorDrawable colorDrawable = new ColorDrawable(color);
		setBackgroundDrawable(colorDrawable);
		btnCancel.setOnClickListener(this);
		btnContinue.setOnClickListener(this);
		rootView.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int height = rootView.findViewById(R.id.rebuy_remind_pop_layout).getTop();
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
		int i = v.getId();
		if (i == R.id.btn_cancle) {
			mListener.onValueSet("cancel");
			dismiss();
		} else if (i == R.id.btn_continue) {
			mListener.onValueSet("ok");
			dismiss();
		}
	}

	public void setValueSetClickListener(Interface.OnValueSetClickListener listener) {
		mListener = listener;
	}
}
