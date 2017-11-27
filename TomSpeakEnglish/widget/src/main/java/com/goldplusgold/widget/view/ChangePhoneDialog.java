package com.goldplusgold.widget.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.goldplusgold.widget.R;
import com.goldplusgold.widget.other.Interface.OnValueSetClickListener;


public class ChangePhoneDialog extends Dialog implements View.OnClickListener {
	private LayoutInflater mLayoutInflater;
	private Context mcontext;
	private EditText etPhone;
	private OnValueSetClickListener mListener;

	public ChangePhoneDialog(Context context, int theme) {
		super(context, theme);
		mcontext = context;
		mLayoutInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initViews();
	}

	private void initViews() {
		View rootView = mLayoutInflater.inflate(R.layout.dialog_change_phone, null);
		setContentView(rootView);
		etPhone = rootView.findViewById(R.id.et_phone);
		Button btnSure = rootView.findViewById(R.id.btn_sure);
		btnSure.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btn_sure){
			String phone = etPhone.getText().toString().trim() ;
			mListener.onValueSet(phone);
			dismiss();
		}

	}

	public void setValueSetClickListener(OnValueSetClickListener listener) {
		mListener = listener;
	}


}
