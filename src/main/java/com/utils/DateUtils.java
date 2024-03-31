package com.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 根据给定日期计算星期
     */
    public static int dateToWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK)-1;
    }

    /**
     * 获取当前日期
     * @return String of current system date
     */
    public static String getCurrentDate() {
        return dateFormat.format(new java.util.Date());
    }

    /**
     * 将Date类型转化为String类型
     */
    public static String dateToString(Date date){
        return dateFormat.format(date);
    }
}
