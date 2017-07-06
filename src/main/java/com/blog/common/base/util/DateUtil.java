package com.blog.common.base.util;

import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-11
 * Time: 下午4:00
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil extends DateUtils {
    public static final String FORMAT_LONGDATETIME = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMAT_SHORTDATETIME = "yyyy-MM-dd";
    public static final String FORMAT_LONGDATETIME_COMPACT = "yyyyMMddHHmmssSSS";
    public static final String FORMAT_SHORTDATETIME_COMPACT = "yyyyMMdd";
    public static final String FORMAT_LONGDATETIME_24HOUR  = "yyMMddHHmmss";

    /**
     * 由字符串变成date类型
     *
     * @param dateString
     * @param format
     * @return
     */
    public static Date getDateFormat(String dateString, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static final String convertDateToString(Date aDate, String pattern) {
        return aDate == null ? null : new SimpleDateFormat(pattern).format(aDate);
    }

    public static Date getDayEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getDayEndTime(calendar);
    }

    public static Date getDayEndTime(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertStringToDateTime("yyyy-MM-dd", date));
        return getDayEndTime(calendar);
    }

    public static Date convertStringToDateTime(String datePattern, String strDateTime) throws ParseException {
        return new Date(new SimpleDateFormat(datePattern).parse(strDateTime).getTime());
    }

    public static Date getDayEndTime(Calendar calendar) {
        Calendar tmpCalendar = (Calendar) calendar.clone();
        tmpCalendar.set(11, 0);
        tmpCalendar.set(12, 0);
        tmpCalendar.set(13, 0);
        tmpCalendar.set(14, 0);

        tmpCalendar.add(5, 1);
        tmpCalendar.add(14, -1);
        return tmpCalendar.getTime();
    }

    public static Date getDayStartTime(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertStringToDateTime("yyyy-MM-dd", date));
        return getDayStartTime(calendar);
    }

    public static Date getDayStartTime(Calendar calendar) {
        Calendar tmpCalendar = (Calendar) calendar.clone();
        tmpCalendar.set(11, 0);
        tmpCalendar.set(12, 0);
        tmpCalendar.set(13, 0);
        tmpCalendar.set(14, 0);
        return tmpCalendar.getTime();
    }
    
    /**
     * 获取传入时间在当天的最处时间
     * 如：2013-9-13 00::00:00
     * @param day
     * @return
     */
    public static Date getTheDayStart(Date day){
    	if(day == null){
    		return null;
    	}
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(day);
    	calendar.set(Calendar.HOUR, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	return new Date(calendar.getTimeInMillis());
    }
    
    /**
     * 获取传入时间在当天的最后时间
     * 如：2013-9-13 23::59:59
     * @param day
     * @return
     */
    public static Date getTheDayEnd(Date day){
    	if(day == null){
    		return null;
    	}
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(day);
    	calendar.set(Calendar.HOUR, 23);
    	calendar.set(Calendar.MINUTE, 59);
    	calendar.set(Calendar.SECOND, 59);
    	return new Date(calendar.getTimeInMillis());
    }

    /**
     * 判断现在的时间之前
     * @param date 要判断的时间
     * @param hour 小时
     * @return
     */
    public static boolean dateBefore(Date date,int hour){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int tempHour = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.set(Calendar.HOUR, tempHour+hour);
        return new Date().before(calendar.getTime());
    }
}
