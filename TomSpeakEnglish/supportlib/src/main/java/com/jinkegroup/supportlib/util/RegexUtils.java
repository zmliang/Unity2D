package com.jinkegroup.supportlib.util;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/5/12.
 */

public class RegexUtils {


    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }

    /**
     * 验证手机号码
     *
     * @param mobile
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        Pattern p = Pattern.compile("^(1[3|4|5|7|8][0-9])\\d{4,8}$");
        return mobile.matches(p.pattern());
    }

    /**
     * 验证登录密码是否为6-12数字字母组合
     *
     * @param password
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPassword(String password) {
        Pattern p = Pattern.compile("^(?!\\D+$)(?![^a-zA-Z]+$)\\S{6,20}$");
        return password.matches(p.pattern());
    }

}
