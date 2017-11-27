package com.jinkegroup.supportlib.util;

import android.text.TextUtils;

/**
 * Created by xiaoshuyong on 2017/4/18.
 */

public class StringConvertNumber {


    public static double parseDouble(String doubleString) {
        double number = 0.00;
        if (!TextUtils.isEmpty(doubleString)) {
            try {
                number = Double.parseDouble(doubleString);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return number;
    }

    public static int parseInt(String intString) {
        int number = 0;
        if (!TextUtils.isEmpty(intString)) {
            try {
                number = Integer.parseInt(intString);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return number;
    }


    public static float parseFloat(String floatString) {
        float number = 0.00f;
        if (!TextUtils.isEmpty(floatString)) {
            try {
                number = Float.parseFloat(floatString);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return number;
    }

    public static String volumeToString(float volume) {
        StringBuilder builder = new StringBuilder();
        float ten_thousand = 10000f;
        float one_hundred_million = 100000000f;
        if (volume < ten_thousand) {
            int vol = (int) volume;
            builder.append(String.valueOf(vol));
        }
        if (volume >= ten_thousand && volume < one_hundred_million) {
            float count = volume / ten_thousand;
            String volText = String.format("%.2f", count);
            builder.append(volText);
            builder.append("万");
        }
        if (volume > one_hundred_million) {
            float count = volume / one_hundred_million;
            String volText = String.format("%.2f", count);
            builder.append(volText);
            builder.append("亿");
        }
        return builder.toString();
    }

}
