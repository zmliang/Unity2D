package com.jinkegroup.edu.se.utils.data;

import android.graphics.drawable.Drawable;

import com.jinkegroup.edu.se.AppApplication;
import com.jinkegroup.edu.se.R;

import java.io.Serializable;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/25
 * CopyRight:  JinkeGroup
 */

public class ContentItemData implements Serializable {
    private String introduction = "";
    private String count = "";
    private Drawable mImg;
    private String name = "";

    public ContentItemData(){
        this.mImg = AppApplication.getInstance().getDrawable(R.drawable.ic_launcher);
        this.introduction = "内容简介";
        this.name = "内容名称";
        this.count = "内容播放数";
    }

    public Drawable getmImg() {
        return mImg;
    }

    public String getName() {
        return name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getCount() {
        return count;
    }



    public void setmImg(Drawable mImg) {
        this.mImg = mImg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setCount(String count) {
        this.count = count;
    }




}
