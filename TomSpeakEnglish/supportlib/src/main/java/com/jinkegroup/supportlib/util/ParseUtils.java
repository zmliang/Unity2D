package com.jinkegroup.supportlib.util;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/26
 * CopyRight:  JinkeGroup
 */

public class ParseUtils {
    public static long parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static long parseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
