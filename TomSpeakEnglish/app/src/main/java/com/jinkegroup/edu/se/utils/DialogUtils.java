package com.jinkegroup.edu.se.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.goldplusgold.widget.view.MyAlertDialog;
import com.jinkegroup.edu.se.R;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/27
 * CopyRight:  JinkeGroup
 */

public class DialogUtils {

    public static void showNewVersionAlert(Context context, View.OnClickListener listener){
        MyAlertDialog dialog = new MyAlertDialog(context).builder();
        dialog.setMsg("有新版本可以更新");
        dialog.setCancelable(true);
        dialog.setTitle("更新提示");
        dialog.setNegativeButton("过会儿再说",listener);
        dialog.setPositiveButton("免流量安装",true,listener);
        dialog.show();
    }

    public static void showSharedDialog(Context context, View.OnClickListener listener){
        Dialog dialog = new Dialog(context, com.goldplusgold.widget.R.style.BottomMenuDialog);
        dialog.setContentView(R.layout.shared_dialog);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);

        dialog.findViewById(R.id.shared_qq).setOnClickListener(listener);
        dialog.findViewById(R.id.shared_wechat_friend).setOnClickListener(listener);
        dialog.findViewById(R.id.shared_wechat_zone).setOnClickListener(listener);
        dialog.findViewById(R.id.shared_weibo).setOnClickListener(listener);
        dialog.show();
    }

}
