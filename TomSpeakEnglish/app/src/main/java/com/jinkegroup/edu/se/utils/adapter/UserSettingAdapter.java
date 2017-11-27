package com.jinkegroup.edu.se.utils.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.group.baselib.BaseListViewAdapter;
import com.jinkegroup.edu.se.R;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/25
 * CopyRight:  JinkeGroup
 */

public class UserSettingAdapter extends BaseListViewAdapter<String> {


    public UserSettingAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null){
            view = View.inflate(getContext(),R.layout.setlist_item,null);
            holder = new ViewHolder();
            holder.pic = view.findViewById(R.id.set_item_img);
            holder.name = view.findViewById(R.id.set_item_name);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(getItem(i));
        return view;
    }

    private static class ViewHolder{
        TextView name;
        ImageView pic;
    }
}
