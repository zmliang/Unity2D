package com.jinkegroup.edu.se.utils.data;

import java.io.Serializable;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/25
 * CopyRight:  JinkeGroup
 */

public class Details implements Serializable {

    private int sentence;
    private int word;
    private int durationDay;
    private int amountDay;



    public int getSentence() {
        return sentence;
    }

    public void setSentence(int sentence) {
        this.sentence = sentence;
    }

    public int getWord() {
        return word;
    }

    public void setWord(int word) {
        this.word = word;
    }

    public int getDurationDay() {
        return durationDay;
    }

    public void setDurationDay(int durationDay) {
        this.durationDay = durationDay;
    }

    public int getAmountDay() {
        return amountDay;
    }

    public void setAmountDay(int amountDay) {
        this.amountDay = amountDay;
    }



}
