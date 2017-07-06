package com.blog.common.base.util;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * <p>Title :Web开发框架</p>
 * <p>Description:</p>
 * <p>Created on 2004-3-4</p>
 * <p>Company :huananwl</p>
 *  @author : wangs
 *  @version : 1.0
 */
public class SystemInfo {
	
	  /**
	   * 获得平台的回车换行符。Windows平台是\r\n, Unix平台是\n. 如果没指定则设为Window平台。
	   */
	  public static final String lineSeparator = System.getProperty(
	      "line.separator", "\r\n");

	  /**
	   * 获得平台的文件分割符。Windows平台是\（或//)，unix平台是/
	   */
	  public static final String fileSeparetor = System.getProperty(
	      "file.separator");

	  /**
	   * 获得平台的路径分割符。Windows和Unxi均为:
	   */
	  public static final String pathSeparetor = System.getProperty(
	      "path.separator");
	  /**
	   * 获得User的Home目录
	   */
	  public static final String userHome = System.getProperty("user.home");

	  /**
	   * 获得User目前的工作目录
	   */
	  public static final String userDir = System.getProperty("user.dir");
	  /**
	   * 获得操作系统的名称, window 2000,
	   */
	  public static final String osName = System.getProperty("os.name");

	  /**
	   * 获得操作系统的版本, window 2000 是5.0
	   */
	  public static final String osVersion = System.getProperty("os.version");
	  /**
	   * 获得JVM的版本, 目前系统为1.3.1-b24
	   */
	  public static final String jvmVersion = System.getProperty("java.vm.version");
	  /**
	   * 获得JVM的提供商，目前系统为Sun Microsystems Inc.
	   */
	  public static final String jvmVendor = System.getProperty("java.vm.vendor");
	  /**
	   * 获得JVM的名称，目前系统为Java HotSpot(TM) Client VM
	   */
	  public static final String jvmName = System.getProperty("java.vm.name");

	  /**
	   * 单子Calendar对象
	   */
	  private static Calendar calendar = Calendar.getInstance();

	  /**
	   * 获得Calendar对象实例
	   * @return Calendar对象实例
	   */
	  public static Calendar getCalendarInstance() {
	    return calendar;
	  }

	  /**
	   * 获得系统当前年
	   * @return 当前年
	   */
	  public static int getCurrentYear() {
	    calendar.setTime(new Date(System.currentTimeMillis()));
	    int year = calendar.get(Calendar.YEAR);
	    calendar.clear();
	    return year;
	  }

	  /**
	   * 获得系统当前月
	   * @return 当前月
	   */
	  public static int getCurrentMonth() {
	    calendar.setTime(new Date(System.currentTimeMillis()));
	    // 以0开始
	    int month = calendar.get(Calendar.MONTH);
	    calendar.clear();
	    return (month + 1);
	  }

	  /**
	   * 获得系统当前天，以月为基
	   * @return 当前天
	   */
	  public static int getCurrentDay() {
	    calendar.setTime(new Date(System.currentTimeMillis()));
	    // 以0开始
	    int day = calendar.get(Calendar.DAY_OF_MONTH);
	    calendar.clear();
	    return day;
	  }

	  /**
	   * 获得系统时间，如：02-3-3 上午1:26(Locale相关)
	   * @return 代表当前系统时间的字符串
	   */
	  public static String getSystemTime() {
	    DateFormat df = DateFormat.getInstance();
	    return df.format(new Date(System.currentTimeMillis()));
	  }

	  public static void main(String args[]) {
	    System.out.println("file separetor:" + jvmVersion);
	  }	
}
