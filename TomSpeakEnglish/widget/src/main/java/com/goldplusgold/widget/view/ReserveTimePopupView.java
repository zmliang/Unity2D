package com.goldplusgold.widget.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.goldplusgold.widget.R;
import com.goldplusgold.widget.other.Interface.OnValueSetClickListener;

import java.util.List;

public class ReserveTimePopupView extends PopupWindow implements OnClickListener, OnTouchListener {
    private Context mContext;
    private Activity mActivity;
    private View rootView;
    private ListView mListView;
    private LayoutInflater mInflater;
    private List<String> mlist;
    private OnValueSetClickListener mListener;

    public ReserveTimePopupView(Context context, Activity activity, List<String> list) {
        this.mContext = context;
        mActivity = activity;
        mlist = list;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initView();
    }

    private void initView() {
        rootView = mInflater.inflate(R.layout.resevertime_popup_view, null);
        setContentView(rootView);
        mListView = (ListView) rootView.findViewById(R.id.lv_resever_time);
        mListView.setAdapter(new MyAdapter());
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.style.AnimBottom);
        rootView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int height = rootView.findViewById(R.id.resever_time_pop_layout).getTop();
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

    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mlist == null ? 0 : mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return mlist == null ? null : mlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.resever_time_item, null);
                holder.tvValue = (TextView) convertView.findViewById(R.id.tv_value);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final String value = mlist.get(position);
            holder.tvValue.setText(value);
            convertView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mListener.onValueSet(value);
                    dismiss();
                }
            });
            return convertView;
        }

    }

    class ViewHolder {
        TextView tvValue;
    }

    public void setValueSetClickListener(OnValueSetClickListener listener) {
        mListener = listener;
    }
}
