package com.rgsc.rgscaibot.ui;


import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static final String FORMAT_YYMMDDHHMMSS = "yyMMddHHmmss";
    public static final String FORMAT_YYMMDD_HHMMSS = "yyMMdd-HHmmss";
    public static final String FORMAT_COMMON = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_COMMON_TIME = "HH:mm:ss";
    //删除数据格式，取到天数
    public static final String FORMAT_COMMON_DAY = "yyyy-MM-dd";

    public static final String FORMAT_COMMON_DAY_BQ = "yyMMdd";
    public static final String FORMAT_COMMON_TIME_BQ = "HHmmss";
    public static final String FORMAT_MMDD_HHMM = "MMdd-HHmm";
    public static String formatDate(Date inDate, String pattern) {
        String ret = null;
        if (inDate != null && StringUtils.isNotBlank(pattern)) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            ret = sdf.format(inDate);
        }
        return ret;
    }

    public static Date parseString(String dateString, String pattern) {
        Date ret = null;
        try {
            if (StringUtils.isNotBlank(dateString) && StringUtils.isNotBlank(pattern)) {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                ret = sdf.parse(dateString);
            }
        } catch (ParseException e) {
            Log.e("--", e.getMessage());
        }

        return ret;
    }

    public static Date parseDate(Date date, String pattern) {
        String ret = null;
        if (date != null && StringUtils.isNotBlank(pattern)) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            ret = sdf.format(date);
        }
        Date retDate = null;
        try {
            if (StringUtils.isNotBlank(ret) && StringUtils.isNotBlank(pattern)) {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                retDate = sdf.parse(ret + " 00:00:00");
            }
        } catch (ParseException e) {
            Log.e("--", e.getMessage());
        }

        return retDate;
    }

    public static String convertDateString(String fromFormat, String toFormat, String dateString) {
        String retString = "";

        if (StringUtils.isBlank(fromFormat) || StringUtils.isBlank(toFormat) || StringUtils.isBlank(dateString)) {
            return "";
        }

        SimpleDateFormat fromformat = new SimpleDateFormat(fromFormat);
        SimpleDateFormat toformat = new SimpleDateFormat(toFormat);
        try {
            Date date = fromformat.parse(dateString);
            retString = toformat.format(date);
        } catch (ParseException e) {
            Log.e("--", e.getMessage());
        }

        return retString;

    }

    /**
     * 系统时间格式设置
     *
     * @param year
     * @param month
     * @param day
     * @param hourOfDay
     * @param minute
     * @param secend
     * @return
     */
    public static long setTime(String year, String month, String day,
                               String hourOfDay, String minute, String secend) {
        try {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, Integer.parseInt(year));
            c.set(Calendar.MONTH, Integer.parseInt(month) - 1);
            c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
            c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourOfDay));
            c.set(Calendar.MINUTE, Integer.parseInt(minute));
            c.set(Calendar.SECOND, Integer.parseInt(secend));
            c.set(Calendar.MILLISECOND, 0);
            long when = c.getTimeInMillis();

            if (when / 1000 < Integer.MAX_VALUE) {
                // SystemClock.setCurrentTimeMillis(when);
                return when;
            }
            return -1;
        } catch (Exception e) {
            Log.e("--", e.getMessage());
            return -1;
        }
    }

    /**
     * 系统时间格式设置
     *
     * @param pattern 时间格式
     * @param day     减去的天数
     * @return 减去后的时间
     */
    public static String getFormatedAddDateTime(String pattern, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - day);
        return sdf.format(date.getTime());
    }

    /**
     * 判断两个日期的时间差
     */
    public static long getTimeDifference(String pattern, String strNow, String strYxq) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date dtNow = null;
        Date dtYxq = null;
        try {
            dtNow = sdf.parse(strNow);
            dtYxq = sdf.parse(strYxq);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dtYxq.getTime() - dtNow.getTime();
    }

    /**
     * 获取当前时间
     */
    public static Date getCurrentCommonDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_COMMON);
        String time = sdf.format(new Date());
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
