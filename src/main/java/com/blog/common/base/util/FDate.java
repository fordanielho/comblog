package com.blog.common.base.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/*
 * <p>Title :Web开发平台</p>
 * <p>Description:</p>
 * <p>Created on 2004-3-4</p>
 * <p>Company :huananwl</p>
 *  @author : wangs
 *  @version : 1.0
 */
public class FDate {
    /**
     * 时间格式(年-月-日）
     */
    public static final String DATE_FORMAT_YMD_LONG = "yyyy-MM-dd";

    /**
     * 时间格式(年月日）
     */
    public static final String DATE_FORMAT_YMD = "yyyyMMdd";

    /**
     * 时间格式（年月）
     */
    public static final String DATE_FORMAT_YM = "yyyyMM";

    /**
     * 时间格式（年）
     */
    public static final String DATE_FORMAT_Y = "yyyy";

    /**
     * Description: 将java.util.Date对象转换为java.sql.Date对象
     * @param _date 待转化的java.util.Date 对象
     * @return java.sql.Date对象
     */
    public static java.sql.Date changeToDBDate(Date _date) {
        if (_date == null) {
            return null;
        }

        return new java.sql.Date(_date.getTime());
    }

    /**
    * Description: 将字符串转换为java.sql.Date对象
    * @param _date 待转化的字符串
    * @return java.sql.Date对象
    */
    public static java.sql.Date changeToDBDate(String _date) {
        return changeToDBDate(changeToDate(_date));
    }

    /**
    * Description: 将字符串转换为java.sql.Time对象
    * @param _date 待转化的字符串
    * @return java.sql.Date对象
    */
    public static java.sql.Time changeToDBTime(String _date) {
        java.sql.Date sdate = changeToDBDate(changeToDate(_date));

        return new java.sql.Time(sdate.getTime());
    }

    /**
    * Description: 将字符串转换为java.sql.Timestamp对象
    * @param _date 待转化的字符串
    * @return java.sql.Date对象
    */
    public static Timestamp changeToDBTimestamp(String _date) {
        java.sql.Date sdate = changeToDBDate(changeToDate(_date));

        return new Timestamp(sdate.getDate());
    }

    /**
     * Description: 将java.sql.Timestamp对象转换为java.util.Date对象
     * @param _date 待转化的java.sql.Timestamp 对象
     * @return java.util.Date 对象
     */
    public static Date changeToDate(Timestamp _date) {
        return (Date) _date;
    }

    /**
     * 将java.util.Date对象转换为java.sql.Timestamp对象
     * @param _date 待转化的java.util.Date 对象
     * @return java.sql.Timestamp对象
     */
    public static Timestamp changeToTimestamp(Date _date) {
        return new Timestamp(_date.getTime());
    }

    /**
     * 将java.sql.Date对象转换为java.util.Date对象
     * @param _date 待转化的java.sql.Date对象
     * @return java.util.Date对象
     */
    public static Date changeToDate(java.sql.Date _date) {
        return (Date) _date;
    }

    /**
     * 使用中文字符以复杂的形式（"年 月 日 上午 时 分 秒"）格式化时间串
     * @param _date 日期对象
     * @return 格式化后的日期
     */
    public static String complexFormatChineseDate(Date _date) {
        Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(_date);

        String timeStr = calendar.get(Calendar.YEAR) + "年" +
            (calendar.get(Calendar.MONTH) + 1) + "月" +
            calendar.get(Calendar.DAY_OF_MONTH) + "日";
        timeStr = timeStr + calendar.get(Calendar.HOUR_OF_DAY) + "时" +
            calendar.get(Calendar.MINUTE) + "分" +
            calendar.get(Calendar.SECOND) + "秒";
        calendar.clear();

        return timeStr;
    }

    /**
     * 使用格式<b>_pattern</b>格式化日期输出
     * @param _date 日期对象
     * @param _pattern 日期格式
     * @return 格式化后的日期
     */
    public static String formatDate(Date _date, String _pattern) {
        if (_date == null) {
            return "";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(_pattern);
        String stringDate = simpleDateFormat.format(_date);

        return stringDate;
    }

    /**
     * 使用格式{@link #DATE_FORMAT_YMD}格式化日期输出
     * @param _date 日期对象
     * @return 格式化后的日期
     */
    public static String formatDate(Date _date) {
        return formatDate(_date, DATE_FORMAT_YMD);
    }

    /**
     * 将时间串转变为时间对象，输入参数<b>_dateStr</b>必须遵循格式{@link #DATE_FORMAT_YMD}
     * @param _dateStr 时间串
     * @return 时间对象
     */
    public static Date changeToDate(String _dateStr) {
        return changeToDate(_dateStr, DATE_FORMAT_YMD);
    }

    /**
     * Description:将时间串转变为时间对象
     * @param _dateStr 时间串
     * @param _pattern 时间串使用的模式
     * @return 时间对象
     */
    public static Date changeToDate(String _dateStr, String _pattern) {
        Date date = null;

        if ((_dateStr == null) || _dateStr.trim().equals("")) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat(_pattern);

        try {
            date = format.parse(_dateStr);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("不能使用模式:[" + _pattern +
                "]格式化时间串:[" + _dateStr + "]");
        }

        return date;
    }

    /**
     * 获得以参数_fromDate为基数的年龄
     * @param _birthday 生日
     * @param _fromDate 起算时间
     * @return 年龄（起算年－出生年）
     */
    public static int getAgeFromBirthday(Date _birthday,
        Date _fromDate) {
        CheckData.checkParamNull("_birthday", _birthday);
        CheckData.checkParamNull("_fromDate", _fromDate);

        Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(_birthday);

        int birthdayYear = calendar.get(Calendar.YEAR);
        int birthdayMonth = calendar.get(Calendar.MONTH);
        int birthdayDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();
        calendar.setTime(_fromDate);

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();

        int age = currentYear - birthdayYear;

        if (!((currentMonth >= birthdayMonth) && (currentDay >= birthdayDay))) {
            age--;
        }

        return age;
    }

    /**
     * 获得当前年龄
     * @param _birthday 生日
     * @return 年龄（起算年－出生年）
     */
    public static int getAgeFromBirthday(Date _birthday) {
        return getAgeFromBirthday(_birthday,
            new Date(System.currentTimeMillis()));
    }

    /**
     * 获得当前年龄
     * @param _birthday 生日
     * @return 年龄（起算年－出生年）
     */
    public static int getAgeFromBirthday(Timestamp _birthday) {
        return getAgeFromBirthday(new Date(_birthday.getTime()),
            new Date(System.currentTimeMillis()));
    }

    /**
     * 获得系统时间的当前月的最后一天，包括年月日
     * @return 返回值格式为：20030131
     */
    public static String getCurMonthFirstDay() {
        return getCurMonthFirstDay(new Date(
                System.currentTimeMillis()));
    }

    /**
     * 获得日期的当前月的最后一天，包括年月日
     * @param date 参考时间
     * @return 返回值格式为：20030131
     */
    public static String getCurMonthFirstDay(Date date) {
        return formatDate(date, DATE_FORMAT_YM) + "01";
    }

    /**
     * 获得系统时间当前月的最后一天，包括年月日
     * @return 返回值格式为：20030131
     */
    public static String getCurMonthLastDay() {
        return getCurMonthLastDay(new Date(System.currentTimeMillis()));
    }

    /**
     * 获得日期的当前月的最后一天，包括年月日
     * @param date 参考时间
     * @return 返回值格式为：20030131
     */
    public static String getCurMonthLastDay(Date date) {
        Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(date);

        Date currentMonthFirstDayDate = changeToDate(formatDate(
                    calendar.getTime(), DATE_FORMAT_YM) + "01", DATE_FORMAT_YMD);
        calendar.clear();
        calendar.setTime(currentMonthFirstDayDate);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        return formatDate(calendar.getTime(), DATE_FORMAT_YMD);
    }

    /**
     * 获得日期的天，以月为基
     * @param _date 日期对象
     * @return 日期的天
     */
    public static int getDay(Date _date) {
        Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(_date);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();

        return day;
    }

    /**
     * 获得两个Date之间的天数
     * @param _startDate 开始时间
     * @param _endDate 结束时间
     * @return 两个Date间的天数
     */
    public static int getDayAmount(java.sql.Date _startDate,
        java.sql.Date _endDate) {
        int nDayAmount = 0;
        Calendar cldStart = Calendar.getInstance();
        Calendar cldEnd = Calendar.getInstance();

        cldStart.setTime(_startDate);
        cldEnd.setTime(_endDate);

        int nStart = cldStart.get(Calendar.DAY_OF_YEAR);
        int nEnd = cldEnd.get(Calendar.DAY_OF_YEAR);

        if ((nEnd - nStart) > 0) {
            nDayAmount = nEnd - nStart;
        } else {
            nDayAmount = 365 - (nEnd - nStart);
        }

        return nDayAmount;
    }

    /**
     * 计算两个日期间相隔的天数
     * @param _startDate 起始日期
     * @param _endDate 终止日期
     * @return 相隔天数, 如果结果为正表示<b>_endDate</b>在<b>_startDate</b>之后；如果结果为负表示<b>_endDate</b>在<b>_startDate</b>之前；如果结果为0表示<b>_endDate</b>和<b>_startDate</b>是同一天。
     */
    public static int getDayCount(Date _startDate,
        Date _endDate) {
        Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(_startDate);

        int startDay = calendar.get(Calendar.DAY_OF_YEAR);
        int startYear = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.setTime(_endDate);

        int endDay = calendar.get(Calendar.DAY_OF_YEAR);
        int endYear = calendar.get(Calendar.YEAR);
        calendar.clear();

        return ((endYear - startYear) * 365) + (endDay - startDay);
    }

    /**
     * 获得日期的小时
     * @param _date 日期对象
     * @return 日期的小时
     */
    public static int getHours(Date _date) {
        Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(_date);

        int value = calendar.get(Calendar.HOUR);
        calendar.clear();

        return value;
    }

    /**
     * 获得系统时间的上个月的第一天，包括年月日
     * @return 返回值格式为：20030101
     */
    public static String getLastMonthFirstDay() {
        return getLastMonthFirstDay(new Date(
                System.currentTimeMillis()));
    }

    /**
     * 获得上个月的第一天，包括年月日
     * @param date 参考时间
     * @return 返回值格式为：20030101
     */
    public static String getLastMonthFirstDay(Date date) {
        Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);

        return formatDate(calendar.getTime(), DATE_FORMAT_YM) + "01";
    }

    /**
     * 获得系统时间的上个月的最后一天，包括年月日
     * @return 返回值格式为：20030131
     */
    public static String getLastMonthLastDay() {
        return getLastMonthLastDay(new Date(
                System.currentTimeMillis()));
    }

    /**
     * 获得日期的上个月的最后一天，包括年月日
     * @param date 参考时间
     * @return 返回值格式为：20030131
     */
    public static String getLastMonthLastDay(Date date) {
        Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(date);

        Date currentMonthFirstDayDate = changeToDate(formatDate(
                    calendar.getTime(), DATE_FORMAT_YM) + "01", DATE_FORMAT_YMD);
        calendar.clear();
        calendar.setTime(currentMonthFirstDayDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        return formatDate(calendar.getTime(), DATE_FORMAT_YMD);
    }

    /**
     * 获得日期的分钟
     * @param _date 日期对象
     * @return 日期的分钟
     */
    public static int getMinutes(Date _date) {
        Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(_date);

        int value = calendar.get(Calendar.MINUTE);
        calendar.clear();

        return value;
    }

    /**
     * 获得日期的月
     * @param _date 日期对象
     * @return 日期的月
     */
    public static int getMonth(Date _date) {
        Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(_date);

        // 以0开始
        int month = calendar.get(Calendar.MONTH);
        calendar.clear();

        return (month + 1);
    }

    /**
     * 获得两个Date间的月数, 天数超过14天算1个月
     * @param _startDate 开始时间
     * @param _endDate 结束时间
     * @return 两个Date间的月数
     */
    public static int getMonthAmount(java.sql.Date _startDate,
        java.sql.Date _endDate) {
        int nYear = 0;
        int nMonth = 0;
        int nDay = 0;
        int nMonthAmount = 0;
        Calendar cldStart = Calendar.getInstance();
        Calendar cldEnd = Calendar.getInstance();

        cldStart.setTime(_startDate);
        cldEnd.setTime(_endDate);

        nYear = cldEnd.get(Calendar.YEAR) - cldStart.get(Calendar.YEAR);
        nMonth = cldEnd.get(Calendar.MONTH) - cldStart.get(Calendar.MONTH);
        nDay = cldEnd.get(Calendar.DATE) - cldStart.get(Calendar.DATE);

        if (nDay > 14) {
            nMonthAmount = (nYear * 12) + nMonth + 1;
        } else {
            nMonthAmount = (nYear * 12) + nMonth;
        }

        return nMonthAmount;
    }

    /**
     * 计算两个日期间相隔的月数, 每隔一月，其相隔天数必>30
     * @param _startDate 起始日期
     * @param _endDate 终止日期
     * @return 相隔月数, 如果结果为正表示<b>_endDate</b>在<b>_startDate</b>之后；如果结果为负表示<b>_endDate</b>在<b>_startDate</b>之前；如果结果为0表示<b>_endDate</b>和<b>_startDate</b>是同一天。
     */
    public static int getMonthCount(Date _startDate,
        Date _endDate) {
        Date startDate = _startDate;
        Date endDate = _endDate;
        boolean afterFlag = false;

        if (_startDate.after(_endDate)) {
            startDate = _endDate;
            endDate = _startDate;
            afterFlag = true;
        }

        Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(startDate);

        int startDay = calendar.get(Calendar.DAY_OF_MONTH);
        int startMonth = calendar.get(Calendar.MONTH);
        int startYear = calendar.get(Calendar.YEAR);
        int countDayOfStartMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.clear();
        calendar.setTime(endDate);

        int endDay = calendar.get(Calendar.DAY_OF_MONTH);
        int endMonth = calendar.get(Calendar.MONTH);
        int endYear = calendar.get(Calendar.YEAR);
        calendar.clear();

        int result = ((endYear - startYear) * 12) +
            (endMonth - (startMonth + 1)) +
            (int) ((endDay + (countDayOfStartMonth - startDay)) / countDayOfStartMonth);

        if (afterFlag) {
            return -result;
        } else {
            return result;
        }
    }

    /**
     * 获得日期的小秒
     * @param _date 日期对象
     * @return 日期的秒
     */
    public static int getSeconds(Date _date) {
        Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(_date);

        int value = calendar.get(Calendar.SECOND);
        calendar.clear();

        return value;
    }

    /**
     * 获得日期的年
     * @param _date 日期对象
     * @return 日期的年
     */
    public static int getYear(Date _date) {
        Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(_date);

        int year = calendar.get(Calendar.YEAR);
        calendar.clear();

        return year;
    }

    /**
     * 使用中文字符以简单的形式（"年 月 日"）格式化时间串
     * @param _date 日期对象
     * @return 格式化后的日期
     */
    public static String simpleFormatChineseDate(Date _date) {
        Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(_date);

        String timeStr = calendar.get(Calendar.YEAR) + "年" +
            (calendar.get(Calendar.MONTH) + 1) + "月" +
            calendar.get(Calendar.DAY_OF_MONTH) + "日";
        calendar.clear();

        return timeStr;
    }

    /**
     * <p>Title :雅普兰Web开发框架</p>
     * <p>Description: 得到当前时间（Timestamp）</p>
     * <p>Created on 2004-4-8</p>
     * <p>Company :YPL</p>
     *  @author : zhang_b
     *  @version : 1.0
     */
    public static Timestamp getCurTime() {
        Timestamp sdate = new Timestamp(System.currentTimeMillis());

        return sdate;
    }

    /**
     * Description: 在_dt的基础上加上_day天
     * @param _dt 日期
     * @param _day 天数
     * @return
     */
    public static Date getDateByDay(java.sql.Date _dt, int _day) {
        Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(_dt);
        calendar.add(Calendar.DAY_OF_MONTH, _day);

        return calendar.getTime();
    }

    /**
     * Description: 根据Timestamp得到Time
     * @param _timesmp
     * @return
     */
    public static java.sql.Time getTimeByTimestamp(Timestamp _timesmp) {
        String str = String.valueOf(_timesmp.getHours()) + ":" +
            String.valueOf(_timesmp.getMinutes()) + ":" +
            String.valueOf(_timesmp.getSeconds());

        return java.sql.Time.valueOf(str);
    }

    /**
     * Description: 得到当前时间的前/后@offset的时间
     * @param offset 负数为前，正数为后
     * @param splitdate 日期分割符
     * @param splittime 时间分割符
     * @return
     */
    public  static String getPriorDay(int offset, String splitdate, String splittime){

    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    	Calendar theday = Calendar.getInstance();
        theday.add(Calendar.DATE, offset);

        df.applyPattern("yyyy"+splitdate+"MM"+splitdate+"dd"+" "+"HH"+splittime+"mm"+splittime+"ss");
        return df.format(theday.getTime());
      }

    /**
     * 获得 指定时间 前/后@offset 小时后的时间
     * @param date
     * @param offset
     * @return
     */
    public static Date getDateByHour(Date date,int offset){
    	Calendar calendar = SystemInfo.getCalendarInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, offset);
        return calendar.getTime();
    }

    /**
     *
        * <p>Description:根据所选年月和期限类型得到该期限第一天</p>
    	* @param _Year
    	* @param _Month
    	* @param _Type,期限类型，30为季报，40为年报，41为半年报
    	* @return 当前期限的第一天,YYYYMMDD
     */
    public static String getCurQXFirstDay(String _Year,String _Month,String _Type){
    	String strRet=null;
    	if (_Type.equals("30")){
    		if (FString.toInt(_Month)<=3)
    			strRet=_Year+"0101";
    		else if (FString.toInt(_Month)>3&&FString.toInt(_Month)<=6)
    			strRet=_Year+"0401";
    		else if (FString.toInt(_Month)>6&&FString.toInt(_Month)<=9)
    			strRet=_Year+"0701";
    		else
    			strRet=_Year+"1001";
    	}
    	else if(_Type.equals("40")){
    		strRet=_Year+"0101";
    	}
    	else if (_Type.equals("41")){
    		if (FString.toInt(_Month)<=6)
    			strRet=_Year+"0101";
    		else
    			strRet=_Year+"0701";
    	}
    	else{
    		if (_Month.length()<2) _Month="0"+_Month;
    		strRet=_Year+_Month+"01";
    	}
    	return strRet;
    }
    /**
     *
        * <p>Description:根据所选年月和期限类型得到该期限最后一天</p>
    	* @param _Year
    	* @param _Month
    	* @param _Type,期限类型，30为季报，40为年报，41为半年报
    	* @return 当前期限的最后一天,YYYYMMDD
     */
    public static String getCurQXLastDay(String _Year,String _Month,String _Type){
    	String strRet=null;
    	if (_Type.equals("30")){
    		if (FString.toInt(_Month)<=3)
    			strRet=_Year+"0331";
    		else if (FString.toInt(_Month)>3&&FString.toInt(_Month)<=6)
    			strRet=_Year+"0630";
    		else if (FString.toInt(_Month)>6&&FString.toInt(_Month)<=9)
    			strRet=_Year+"0930";
    		else
    			strRet=_Year+"1231";
    	}
    	else if(_Type.equals("40")){
    		strRet=_Year+"1231";
    	}
    	else if (_Type.equals("41")){
    		if (FString.toInt(_Month)<=6)
    			strRet=_Year+"0630";
    		else
    			strRet=_Year+"1231";
    	}
    	else
    		strRet=getCurLastDay(_Year,_Month);
    	return strRet;

    }
    /**
     *
        * <p>Description:根据所选年月和期限类型得到该期限第一天</p>
    	* @param _Year
    	* @param _Month
    	* @param _Type,期限类型，30为季报，40为年报，41为半年报
    	* @return 上一期限的第一天,YYYYMMDD
     */
    public static String getPreQXFirstDay(String _Year,String _Month,String _Type){
    	String strRet=null;
    	if (_Type.equals("30")){
    		if (FString.toInt(_Month)<=3)
    			strRet=String.valueOf(FString.toInt(_Year)-1)+"1001";
    		else if (FString.toInt(_Month)>3&&FString.toInt(_Month)<=6)
    			strRet=_Year+"0101";
    		else if (FString.toInt(_Month)>6&&FString.toInt(_Month)<=9)
    			strRet=_Year+"0401";
    		else
    			strRet=_Year+"0701";
    	}
    	else if(_Type.equals("40")){
    		strRet=String.valueOf(FString.toInt(_Year)-1)+"0101";
    	}
    	else if (_Type.equals("41")){
    		if (FString.toInt(_Month)<=6)
    			strRet=String.valueOf(FString.toInt(_Year)-1)+"0701";
    		else
    			strRet=_Year+"0101";
    	}
    	else
    		{
    		 if (FString.toInt(_Month)==1){
    		 	_Year=String.valueOf(FString.toInt(_Year)-1);
    		 	 strRet=_Year+"1201";
    		 }
    		 else{
    		 	 _Month=String.valueOf(FString.toInt(_Month)-1);
    		 	 if (_Month.length()<2) _Month="0"+_Month;
    		 	 strRet=_Year+_Month+"01";
    		 }
    	}
    	return strRet;


    }
    /**
     *
        * <p>Description:根据所选年月和期限类型得到该期限最后一天</p>
    	* @param _Year
    	* @param _Month
    	* @param _Type,期限类型，30为季报，40为年报，41为半年报
    	* @return 上一期限的最后一天，YYYYMMDD
     */
    public static String getPreQXLastDay(String _Year,String _Month,String _Type){
       	String strRet=null;
    	if (_Type.equals("30")){
    		if (FString.toInt(_Month)<=3)
    			strRet=String.valueOf(FString.toInt(_Year)-1)+"1231";
    		else if (FString.toInt(_Month)>3&&FString.toInt(_Month)<=6)
    			strRet=_Year+"0331";
    		else if (FString.toInt(_Month)>6&&FString.toInt(_Month)<=9)
    			strRet=_Year+"0630";
    		else
    			strRet=_Year+"0930";
    	}
    	else if(_Type.equals("40")){
    		strRet=String.valueOf(FString.toInt(_Year)-1)+"1231";
    	}
    	else if (_Type.equals("41")){
    		if (FString.toInt(_Month)<=6)
    			strRet=String.valueOf(FString.toInt(_Year)-1)+"1231";
    		else
    			strRet=_Year+"0630";
    	}
    	else
    		strRet=getPreMonthLastDay(_Year,_Month);
    	return strRet;


    }
    /**
     *
        * <p>Description:根据所选年月得到前月的最后一天日期</p>
    	* @param _Year
    	* @param _Month
    	* @return 前月最后一天日期,YYYYMMDD
     */
	public static String getPreMonthLastDay(String _Year,String _Month){
		if (_Month.length()<2) _Month="0"+_Month;
		String strFirstDay = _Year+_Month+"01";

		Date date= FDate.changeToDate(strFirstDay);
		Calendar calendar = SystemInfo.getCalendarInstance();
		calendar.setTime(date);
        Date currentMonthFirstDayDate = FDate.changeToDate(FDate.formatDate(
                calendar.getTime(), DATE_FORMAT_YM) + "01", DATE_FORMAT_YMD);
        calendar.clear();
        calendar.setTime(currentMonthFirstDayDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return FDate.formatDate(calendar.getTime(), DATE_FORMAT_YMD);
	}
    /**
     *
        * <p>Description:根据所选年月得到前月的最后一天日期</p>
    	* @param _Year
    	* @param _Month
    	* @return 前月最后一天日期,yyyy-MM-DD
     */
	public static String getLastDayByYM(String _Year,String _Month){
		if (_Month.length()<2) _Month="0"+_Month;
		String strFirstDay = _Year+_Month+"01";

		Date date= FDate.changeToDate(strFirstDay);
		Calendar calendar = SystemInfo.getCalendarInstance();
		calendar.setTime(date);
        Date currentMonthFirstDayDate = FDate.changeToDate(FDate.formatDate(
                calendar.getTime(), DATE_FORMAT_YM) + "01", DATE_FORMAT_YMD);
        calendar.clear();
        calendar.setTime(currentMonthFirstDayDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return FDate.formatDate(calendar.getTime(), DATE_FORMAT_YMD_LONG);
	}

	/**
	 *
	    * <p>Description:根据所选年月得到当前月的最后一天日期</p>
		* @param _Year
		* @param _Month
		* @return 当前月最后一天日期,YYYYMMDD
	 */
	public static String getCurLastDay(String _Year,String _Month){
		String strMonth=String.valueOf(FString.toInt(_Month)+1);
		if (strMonth.length()<2) strMonth="0"+strMonth;
		String strFirstDay = _Year+strMonth+"01";
		Date date= FDate.changeToDate(strFirstDay);
		Calendar calendar = SystemInfo.getCalendarInstance();
		calendar.setTime(date);
        Date currentMonthFirstDayDate = FDate.changeToDate(FDate.formatDate(
                calendar.getTime(), DATE_FORMAT_YM) + "01", DATE_FORMAT_YMD);
        calendar.clear();
        calendar.setTime(currentMonthFirstDayDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        return FDate.formatDate(calendar.getTime(), DATE_FORMAT_YMD);

	}

    public static Timestamp getYearStartTime(){
    	Calendar calendar = SystemInfo.getCalendarInstance();
    	calendar.set(calendar.get(Calendar.YEAR),0,1,0,0,0);
    	long t=calendar.getTimeInMillis();
    	calendar.clear();
    	return new Timestamp(t);
    }

    public static Timestamp getYearEndTime(){
    	Calendar calendar = SystemInfo.getCalendarInstance();
    	calendar.set(calendar.get(Calendar.YEAR),11,31,23,59,59);
    	long t=calendar.getTimeInMillis();
    	calendar.clear();
    	return new Timestamp(t);
    }

    public static Timestamp getMonthStartTime(){
    	Calendar calendar = SystemInfo.getCalendarInstance();
    	calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),1,0,0,0);
    	long t=calendar.getTimeInMillis();
    	calendar.clear();
    	return new Timestamp(t);
    }

    public static Timestamp getMonthEndTime(){
    	Calendar calendar = SystemInfo.getCalendarInstance();
    	calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),31,23,59,59);
    	long t=calendar.getTimeInMillis();
    	calendar.clear();
    	return new Timestamp(t);
    }

    public static Timestamp getDayStartTime(){
    	Calendar calendar = SystemInfo.getCalendarInstance();
    	calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0,0);
    	long t=calendar.getTimeInMillis();
    	calendar.clear();
    	return new Timestamp(t);
    }


    public static Timestamp getDayEndTime(){
    	Calendar calendar = SystemInfo.getCalendarInstance();
    	calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),23,59,59);
    	long t=calendar.getTimeInMillis();
    	calendar.clear();
    	return new Timestamp(t);
    }
    /*
     * 根据给定的日期计算给定月数后的日期
     * aD_date：格式"YYYY-MM-DD"
     * 返回java.util.Date
     */
    public static Date getDateAfterMonth(String aD_date , int ai_month)
    {
    	Date lD_Ret = null;
    	Calendar calendar = SystemInfo.getCalendarInstance();
    	String lS_Year = aD_date.substring(0,4);
    	String lS_Month = aD_date.substring(5,7);
    	String lS_Day = aD_date.substring(8,10);
    	int li_Year = Integer.parseInt(lS_Year);
    	int li_Month = Integer.parseInt(lS_Month);
    	int li_Day = Integer.parseInt(lS_Day);
    	calendar.set(li_Year,li_Month,li_Day);
    	calendar.add(Calendar.MONTH,ai_month - 1);
    	lD_Ret = calendar.getTime();
    	return lD_Ret;
    }
    /*
     * 根据给定的日期计算给定年数后的日期
     * aD_date：格式"YYYY-MM-DD"
     * 返回java.util.Date
     */
    public static Date getDateAfterYear(String aD_date , int ai_Year)
    {
    	Date lD_Ret = null;
    	Calendar calendar = SystemInfo.getCalendarInstance();
    	String lS_Year = aD_date.substring(0,4);
    	String lS_Month = aD_date.substring(5,7);
    	String lS_Day = aD_date.substring(8,10);
    	int li_Year = Integer.parseInt(lS_Year);
    	int li_Month = Integer.parseInt(lS_Month);
    	int li_Day = Integer.parseInt(lS_Day);
    	calendar.set(li_Year + ai_Year,li_Month - 1,li_Day);
    	lD_Ret = calendar.getTime();
    	return lD_Ret;
    }

    /**两个日期间的状态，现只到天、小时、分钟前
     * @param
     * @param
     * @return
     */
    public static String getInternalDateTime(Date dbeginDate,Date dendDate)
    {
    	String sStr = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date beginDate= format.parse(FDate.formatDate(dbeginDate,"yyyy-MM-dd HH:mm:ss"));
			Date endDate= format.parse(FDate.formatDate(dendDate,"yyyy-MM-dd HH:mm:ss"));

			double dLeftTime = Arith.sub(endDate.getTime(), beginDate.getTime());
			double dDay = Arith.div(dLeftTime,24*60*60*1000);
			double dHour = Arith.div(dLeftTime, 60*60*1000);
			double dMinute = Arith.div(dLeftTime, 60*1000);

			if(dDay>=1)
			{
				sStr = (int)Math.ceil(dDay)+"天前";
			}else if (dHour>=1)
			{
				sStr = (int)Math.ceil(dHour)+"小时前";
			}else if (dMinute>=1)
			{
				sStr = (int)Math.ceil(dMinute)+"分钟前";
			}else
			{
				sStr = "在1分钟内";
			}
		} catch (Exception e) {

		}


		return sStr;
    }

    /**两个日期间的状态，现只到天、小时、分钟前,并精确到分钟，将秒数去掉
     * @param
     * @param
     * @return
     */
    public static String getExactDateTime(Date dbeginDate,Date dendDate)
    {
    	String sStr = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			Date beginDate= format.parse(FDate.formatDate(dbeginDate,"yyyy-MM-dd HH:mm"));
			Date endDate= format.parse(FDate.formatDate(dendDate,"yyyy-MM-dd HH:mm"));

			double dLeftTime = Arith.sub(endDate.getTime(), beginDate.getTime());
			double dDay = Arith.div(dLeftTime,24*60*60*1000);
			double dHour = Arith.div(dLeftTime, 60*60*1000);
			double dMinute = Arith.div(dLeftTime, 60*1000);

			if(dDay>=1)
			{
				sStr = (int)Math.ceil(dDay)+"天前";
			}else if (dHour>=1)
			{
				sStr = (int)Math.ceil(dHour)+"小时前";
			}else if (dMinute>=1)
			{
				sStr = (int)Math.ceil(dMinute)+"分钟前";
			}else
			{
				sStr = "在1分钟内";
			}
		} catch (Exception e) {

		}
		return sStr;
    }

    /**两个日期间的状态，
     * 1、“当前时间”减去“发布时间”在1分钟内显示秒数，如：10秒前
     * 2、1小时内显示分钟数，如：2分钟前
     * 3、1~24小时内显示小时，如：3小时前
     * 4、24小时后显示月日，如：2010-03-01
     * @param
     * @param
     * @return
     */
    public static String getDiffDateTime(Date dbeginDate,Date dendDate,String sformat)
    {
    	String sStr = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat format1 = new SimpleDateFormat(sformat);
			Date beginDate= format.parse(FDate.formatDate(dbeginDate,"yyyy-MM-dd HH:mm:ss"));
			Date endDate= format.parse(FDate.formatDate(dendDate,"yyyy-MM-dd HH:mm:ss"));

			double dLeftTime = Arith.sub(endDate.getTime(), beginDate.getTime());
			double dHour = Arith.div(dLeftTime, 60*60*1000);
			double dMinute = Arith.div(dLeftTime, 60*1000);
			double dSecond = Arith.div(dLeftTime, 1000);

			if(dHour>=24)
			{
				sStr = format1.format(dbeginDate);
			}else if (dHour>=1&&dHour<24)
			{
				sStr = (int)Math.floor(dHour)+"小时前";
			}else if (dMinute>=1&&dHour<1)
			{
				sStr = (int)Math.floor(dMinute)+"分钟前";
			}else if (dSecond<1)
			{
				sStr = "1秒前";
			}else
			{
				sStr = (int)Math.floor(dSecond)+"秒前";
			}
		} catch (Exception e) {

		}


		return sStr;
    }
    /**
     * 判断当前时间是否在指定时间之前
     * @param date2 格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static boolean isDateBefore(String date2) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			//System.out.println(date1.before(df.parse(date2)));
			return date1.before(df.parse(date2));
		} catch (ParseException e) {
			System.out.print(e.getMessage());
			return false;
		}

	}
    /**
     * 判断当前时间是否在指定时间之后
     * @param date2 格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
	public static boolean isDateAfter(String date2) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			//System.out.println(date1.after(df.parse(date2)));
			return date1.after(df.parse(date2));
		} catch (ParseException e) {
			System.out.print(e.getMessage());
			return false;
		}
	}

    /**
     * 得到明天时间 yyyy-MM-dd
     * @return
     */
	public static String getTomorrow(){
        Calendar calendar= Calendar.getInstance();
        calendar.roll(Calendar.DAY_OF_YEAR,1);//明天
        return formatDate(calendar.getTime(),DATE_FORMAT_YMD_LONG);
    }
}
