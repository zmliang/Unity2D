package com.jinkegroup.supportlib.util;

/**
 * Created by Administrator on 2017/5/11.
 */

public class ReplaceStrByAsteriskUtil {
    public static String hideMidFourPhoneNum(String phone) {
        return phone.substring(0, phone.length() - (phone.substring(3)).length()) + "****" + phone.substring(7);
    }

    public static String hideRealName(String realName) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < realName.length() - 1; i++) {
            builder.append("*");
        }
        String hideName = realName.substring(0, 1) + builder.toString();
        return hideName;
    }

    public static String hideIdNum(String idNum) {
        return idNum.substring(0, idNum.length() - (idNum.substring(10)).length()) + "********";
    }

}
