package com.jinkegroup.supportlib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2017/6/6.
 */

public class DateFormatUtils {
    public static int getCurrentDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date time = new Date();
        String currentTime = sdf.format(time);
        return currentTime;
    }

    public static boolean isDuringTwoTimepoint(String time, String timepoint1, String timepoint2) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date1 = sdf.parse(timepoint1);

            Date date2 = sdf.parse(timepoint2);
            Date date = sdf.parse(time);
            if (date1.before(date2)) {
                if (date.after(date1) && date.before(date2)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (date.before(date2) && date.after(date1)) {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean isInTradeTime(int dayOfWeek, String currentTime) {

        //对周一，周二至周四，周五，双休日分别进行判断
        switch (dayOfWeek) {
            case 1: //星期日，不开盘
                return false;
            case 2: //星期一,交易时间段是9:00~15:30, 20:00~24:00
                return (isDuringTwoTimepoint(currentTime, "09:00:00", "15:30:00") || isDuringTwoTimepoint(currentTime, "20:00:00", "23:59:59"));
            case 3: //星期二, 交易时间段是0:00~15:30,20:00~24:00
                return (isDuringTwoTimepoint(currentTime, "00:00:00", "15:30:00") || isDuringTwoTimepoint(currentTime, "20:00:00", "23:59:59"));
            case 4: //星期三,同星期二
                return (isDuringTwoTimepoint(currentTime, "00:00:00", "15:30:00") || isDuringTwoTimepoint(currentTime, "20:00:00", "23:59:59"));
            case 5: //星期四,同星期二
                return (isDuringTwoTimepoint(currentTime, "00:00:00", "15:30:00") || isDuringTwoTimepoint(currentTime, "20:00:00", "23:59:59"));
            case 6: //星期五,交易时间段是0:00~15:30
                return (isDuringTwoTimepoint(currentTime, "00:00:00", "15:30:00"));
            case 7: //星期六，不开盘
                return false;
            default:
                return false;
        }

    }
}
