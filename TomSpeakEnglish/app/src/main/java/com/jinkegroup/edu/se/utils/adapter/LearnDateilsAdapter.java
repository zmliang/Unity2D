package com.jinkegroup.edu.se.utils.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinke.group.baselib.BaseListViewAdapter;
import com.jinkegroup.edu.se.R;
import com.jinkegroup.edu.se.utils.data.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/25
 * CopyRight:  JinkeGroup
 */

public class LearnDateilsAdapter extends BaseListViewAdapter<String> {

    private Map<String,Integer> userDetails;

    public LearnDateilsAdapter(Context ctx) {
        super(ctx);
    }

    public LearnDateilsAdapter(Context context,Map map){
        super(context);
        userDetails = map;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view==null){
            view = View.inflate(getContext(), R.layout.learn_details_item,null);
            holder = new ViewHolder();
            holder.count = view.findViewById(R.id.count);
            holder.name = view.findViewById(R.id.learn_detail);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        String key = getItem(i);
        holder.name.setText(key);
        holder.count.setText(String.valueOf(getValue(key)));
        return view;
    }

    private int getValue(String key){
        if (userDetails!=null)
            return userDetails.get(key);
        return 0;
    }

    private static class ViewHolder{
        TextView count;
        TextView name;
    }
}
