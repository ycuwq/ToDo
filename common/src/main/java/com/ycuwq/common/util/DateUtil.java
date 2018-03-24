package com.ycuwq.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yangchen on 2016/7/7 0007.
 */
public class DateUtil {

    public static String getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return formatter.format(currentTime);
    }

    public static String getNowDateShort() {
        Date currentTime = new Date();
        return dateShortToString(currentTime);
    }

    public static String getDateString(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return dateShortToString(calendar.getTime());
    }

    public static String dateShortToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return formatter.format(date);
    }


    public static String getYesterday(String date) {
        return getFormerlyTime(date, 60 * 60 * 24);
    }

    /**
     * 获取date过去的某一时间
     * @param date          选定的时间
     * @param second        秒
     * @return
     */
    public static String getFormerlyTime(String date, int second) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

        Date date2 = null;
        try {
            date2 = sdf.parse(date);
            date2 = new Date((date2.getTime()) - second * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date2);
    }

    /**
     * 将字符串转化成数值
     * @param s
     * @return
     */
    public static long string2long(String s) {
        long result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            Date d1 = sdf.parse(s);
            result = d1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String long2String(long l) {
        Date date = new Date(l);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return sdf.format(date);
    }

    //获取两个时间相差的秒数
    public static long getDateSecond(String s1,String s2){
        long result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            Date d1 = sdf.parse(s1);
            Date d2 = sdf.parse(s2);
            result = d2.getTime() - d1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Math.abs(result)/1000;
    }

    /**
     * 将当前的秒数转换成分时秒
     * @param second
     * @return
     */
    public static String secondToTime(long second) {
        long hour = second / 3600;
        long min = (second % 3600 ) /60;
        long s = second % 60;
        return timeFormat(hour)+ ":" + timeFormat(min)  + ":" + timeFormat(s);
    }


    /**
     * 判断如果是一位的时间在前面补0
     * @param number
     * @return
     */
    private static String timeFormat(long number) {
        if (number < 10) {
            return "0" + number;
        }
        return  number + "";
    }
}
