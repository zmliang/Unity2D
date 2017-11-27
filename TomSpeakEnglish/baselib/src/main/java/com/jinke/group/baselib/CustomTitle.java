package com.jinke.group.baselib;

import android.view.View;
import android.view.Window;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/24
 * CopyRight:  JinkeGroup
 */

public class CustomTitle {

    public static void getCustomTitle(final BaseActivity activity, String title){
        activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        activity.setContentView(R.layout.titleview);
        activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titleview);
        activity.findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }
}