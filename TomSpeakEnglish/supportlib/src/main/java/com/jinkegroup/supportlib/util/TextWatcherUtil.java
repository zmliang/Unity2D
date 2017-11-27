package com.jinkegroup.supportlib.util;

import android.text.Editable;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类TextWatcherUtil.java的实现描述：TODO 编辑框输入字符的处理
 *
 * @author shuyong.xsy 2015-6-4 下午5:26:39
 */
public class TextWatcherUtil {

    /**
     * 删除编辑框中字符的第一个空格 delete blank from string start
     */
    public static void deleteBlankByStringStart(Editable s) {
        String text = s.toString();
        int len = text.length();
        if (len == 1 && text.equals(" ")) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(text);
            if (m.matches()) {
                s.clear();
            }
            return;
        }
    }

    /**
     * 删除“0”开始的数字
     */
    public static void deleteBlankByZeroStart(Editable s) {
        String text = s.toString();
        int len = text.length();
        if (len == 1 && text.equals("0")) {
            s.clear();
            return;
        }
    }

    /**
     * "." can not start for example :".120" is not validate
     */
    public static void deleteDotbyNumberStart(Editable s) {
        String text = s.toString();
        int len = s.toString().length();
        if (len == 1 && text.equals(".")) {
            s.clear();
            return;
        }
    }

    /**
     * 数字只能有一个小数点 delete dot if dot count>1,delete dot example for : "1.2.",delete after "1.2"
     */
    public static void deleteRepeatDot(EditText mEditText) {
        String value = mEditText.getText().toString();
        if (value.indexOf(".") > 0) {
            if (value.indexOf(".", value.indexOf(".") + 1) > 0) {
                mEditText.setText(value.substring(0, value.length() - 1));
                mEditText.setSelection(mEditText.getText().toString().length());
            }
        }
    }

    public static String deleteRepeatDot(String value) {
        if (value.indexOf(".") > 0) {
            if (value.indexOf(".", value.indexOf(".") + 1) > 0) {
                value = value.substring(0, value.length() - 1);
            }
        }
        return value;
    }

    /**
     * @param width 保留小数点的位数
     */
    public static void deleteAboveNumberWidth(CharSequence s, EditText editText, int width) {
        // if "0.001"(dot after 001) >width , delete end "1".
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > width) {
                s = s.subSequence(0, s.toString().indexOf(".") + width + 1);
                editText.setText(s);
                editText.setSelection(s.length());
            }
        }

        // if "0"start , "." must follow by it.
        if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                editText.setText(s.subSequence(0, 1));
                editText.setSelection(editText.getText().toString().length());
                return;
            }
        }
    }

    public static CharSequence deleteAboveNumberWidth(CharSequence value, int width) {
        // if "0.001"(dot after 001) >width , delete end "1".
        if (value.toString().contains(".")) {
            if (value.length() - 1 - value.toString().indexOf(".") > width) {
                value = value.subSequence(0, value.toString().indexOf(".") + width + 1);
            }
        }

        // if "0"start , "." must follow by it.
        if (value.toString().startsWith("0") && value.toString().trim().length() > 1) {
            if (!value.toString().substring(1, 2).equals(".")) {
                value = value.subSequence(0, 1);
            }
        }
        return value;
    }
}
