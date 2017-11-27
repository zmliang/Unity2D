package com.jinkegroup.edu.se.activity;

import android.os.Bundle;

import com.jinkegroup.edu.se.fragment.VideoFragment;


/**
 * Author   :  Tomcat
 * Date     :  2017/10/26
 * CopyRight:  JinkeGroup
 */

public class VideoActivity extends BaseFragmentActivity {
    VideoFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        listFragment = new VideoFragment();
        addFragment(listFragment);
    }

}
