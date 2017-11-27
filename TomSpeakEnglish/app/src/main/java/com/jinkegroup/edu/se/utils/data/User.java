package com.jinkegroup.edu.se.utils.data;

import android.bluetooth.BluetoothAssignedNumbers;
import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/25
 * CopyRight:  JinkeGroup
 */

public class User implements Serializable {
    public final static String SENTENCE = "句子";
    public final static String WORD = "单词";
    public final static String DURATION_DAY = "连续学习(天)";
    public final static String AMOUNT_DAY = "总学习(天)";

    private String nickName="";
    private int StarCount = 0;
    private long userID = 0l;
    private int userAge = 0;
    private Drawable userHead;

    public Map<String, Integer> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Integer> details) {
        this.details = details;
    }

    private Map<String, Integer> details = new HashMap<String, Integer>();

    public User(){
        details.put(SENTENCE,0);
        details.put(WORD,0);
        details.put(DURATION_DAY,0);
        details.put(AMOUNT_DAY,0);
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public Drawable getUserHead() {
        return userHead;
    }

    public void setUserHead(Drawable userHead) {
        this.userHead = userHead;
    }



    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getStarCount() {
        return StarCount;
    }

    public void setStarCount(int starCount) {
        StarCount = starCount;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (userID != user.userID)
            return false;
        else
            return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickName='" + nickName + '\'' +
                ", StarCount=" + StarCount +
                ", userID=" + userID +
                '}';
    }
}
