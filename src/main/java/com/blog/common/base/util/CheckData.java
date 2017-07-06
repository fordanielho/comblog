package com.blog.common.base.util;

//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.StringTokenizer;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * <p>Title :雅普兰Web开发框架</p>
 * <p>Description:数据有效性检查类</p>
 * <p>Created on 2004-3-4</p>
 * <p>Company :YPL</p>
 *  @author : wangs
 *  @version : 1.0
 */
public class CheckData {
	/**
	 * 缺省的IP地址检查的正则表达式，四点分地址
	 */
	public static final String IP_REGEXP = "\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}";
	/**
	 * 缺省的电话号码检查的正则表达式, 3位区号-8位号码，其中‘-’可有可无
	 */
	public static final String PHONE_REGEXP = "\\d{3}-?\\d{8}";

	private CheckData() {
	}

	/**
	 * 检查对象是否为空
	 * @param _str  对象的描述
	 * @param _obj  对象
	 * @throws java.lnag.IllegalArgumentException 当对象为空时指出
	 */
	public static void checkParamNull(String _str, Object _obj) {
		if (_obj == null) {
			throw new IllegalArgumentException("参数:" + _str + "不能为空");
		}
	}

	/**
	 * 合法的IP地址检查
	 * @param _ipAddress
	 * @throw java.lnag.IllegalArgumentException IP地址无效时掷出
	 */
	  /*
	     public static void checkIPAddress(String _ipAddress) {
	    checkParamNull("_ipAddress", _ipAddress);
	    Pattern pattern = Pattern.compile(IP_REGEXP);
	    Matcher matcher = pattern.matcher(_ipAddress);
	    if(matcher.matches()) {
	      StringTokenizer st = new StringTokenizer(_ipAddress, ".");
	      while(st.hasMoreTokens()) {
	        int value = Integer.parseInt(st.nextToken());
	        if(value > 255) {
	          throw new IllegalArgumentException("IP地址：[" +_ipAddress + "]不合法");
	        }
	      }
	    }else{
	      throw new IllegalArgumentException("IP地址：[" +_ipAddress + "]不合法");
	    }
	     }
	   */

	/**
	 * 检查电话号码，形如：021-68532099
	 * @param _phoneNumber 电话号码
	 * @throw java.lang.IllegalArgumentException 当电话号码无效时掷出
	 * @since JDK1.4
	 */
	  /*
	     public static void checkPhoneNumber(String _phoneNumber) {
	    checkParamNull("_phoneNumber", _phoneNumber);
	    checkPhoneNumber(_phoneNumber, "\\d{3}-?\\d{8}");
	     }
	   */
	/**
	 * 使用指定的正则表达式，检查电话号码的有效性
	 * @param _phoneNumber 电话号码
	 * @param _regExp 正则表达式
	 */
	  /*
	     public static void checkPhoneNumber(String _phoneNumber, String _regExp) {
	    checkParamNull("_phoneNumber", _phoneNumber);
	    checkParamNull("_regExp", _regExp);
	    String regexStr = _regExp;
	    Pattern pattern = Pattern.compile(regexStr);
	    Matcher matcher = pattern.matcher(_phoneNumber);
	    if(!matcher.matches()) {
	      throw new IllegalArgumentException("无效的电话号码："+ _phoneNumber);
	    }
	     }
	   */
	/**
	 * 检查Email地址的有效性
	 * @param _emailStr Emial地址
	 * @throws IllegalArgumentException 当Email地址无效时掷出
	 * @since JDK1.4
	 */
	public static void checkEmail(String _emailStr) {
		checkParamNull("_emailStr", _emailStr);
		String regexEmailGroup = "^(.+)@(.+)$";                                                                         // sam@com
		String regexEmailUserValidChar = "[^\\s\\(\\)><@,:;'\"\\.\\{\\}\\[\\]]+";                                       // 空格 \ ( ) < > @ , : ; ' " . [ ] { }
		String regexEmailDomainValidChar = ".*(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum)$";  // 合法域名
		String regexEmailIPAddress = "^\\[(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\]$";                       // IP地址
		// Domaim类别，1：友好名，2：IP地址
		int  domainStyle = 0;
		//分组
		Pattern pattern = Pattern.compile(regexEmailGroup);
		Matcher matcher = pattern.matcher(_emailStr);
		if(!matcher.matches()) {
			throw new  IllegalArgumentException("无效的Email:[" + _emailStr + "]，必须用@分割");
		}
		//用户名部分
		String group1 = matcher.group(1);
		Pattern pattern1 = pattern.compile(regexEmailUserValidChar);
		Matcher matcher1 = pattern1.matcher(group1);
		if( !matcher1.matches()) {
			throw new IllegalArgumentException("无效的Email:[" + _emailStr + "]，用户名部分包含了无效字符");
		}
		//域部分，IP检查
		String group2 = matcher.group(2);
		Pattern pattern2 = Pattern.compile(regexEmailIPAddress);
		Matcher matcher2 = pattern2.matcher(group2);
		// 使用了IP
		if(matcher2.matches()) {
			domainStyle = 2;
			// 解析 IP地址，255检查
			StringTokenizer st = new StringTokenizer(group2.substring(1, group2.length()-1), ".");
			while(st.hasMoreTokens()) {
				int i = Integer.parseInt(st.nextToken());
				if(i > 255 ){
					throw new IllegalArgumentException("无效的Email:[" + _emailStr + "]，IP地址不正确!");
				}
			}
		}
	}

	/**
	 * 18位身份证的有效行检验
	 * @param _certiCode 身份证
	 * @return true正确；false错误
	 * @throw java.lnag.IllegalArgumentException 身份证位数或为空或数字格式不正确时，掷出
	 */
	public static boolean check18CertiCode(String _certiCode) {
		int[] nCertiCheckW = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,
				1};
		int nCount = 0;
		int nIdNum = 0;
		String lastBitStr = String.valueOf(_certiCode.charAt(17));
		String checkStr = null;

		if ( (_certiCode == null) || (_certiCode.length() != 18)) {
			throw new RuntimeException("Invalid certi code[" + _certiCode + " length");
		}
		for (int i = 0; i < 17; i++) {
			char c = _certiCode.charAt(i);
			if ( (c <= '9') || (c >= '0')) {
				nIdNum = c - '0';
			} else {
				throw new RuntimeException("Invalid Certi Code char");
			}
			nCount += nIdNum * nCertiCheckW[i];
		}
		nCount = nCount % 11;
		switch (nCount) {
			case 0:
				checkStr = "1";
			case 1:
				checkStr = "0";
			case 2:
				checkStr = "X";
			case 3:
				checkStr = "9";
			case 4:
				checkStr = "8";
			case 5:
				checkStr = "7";
			case 6:
				checkStr = "6";
			case 7:
				checkStr = "5";
			case 8:
				checkStr = "4";
			case 9:
				checkStr = "3";
			case 10:
				checkStr = "2";
			default:
				checkStr = "";
		}

		return checkStr.equals(lastBitStr);
	}

	/**
	 * 身份证生日校验
	 * @param _certiCode 身份证
	 * @param _year 出生年
	 * @param _month 出生月
	 * @param _day 出生天
	 * @throw java.lnag.IllegalArgumentException 身份证无效时掷出
	 */
	public static void checkCertiCodeByBirthday(String _certiCode, int _year,
												int _month, int _day) {
		checkParamNull("_certiCode", _certiCode);

		int yearID = -1;
		int monthID = -1;
		int dayID = -1;

		if (_certiCode.length() == 15) {
			yearID = 1900 + Integer.parseInt(_certiCode.substring(6, 8));
			monthID = Integer.parseInt(_certiCode.substring(8, 10));
			dayID = Integer.parseInt(_certiCode.substring(10, 12));
		} else if (_certiCode.length() == 18) {
			yearID = Integer.parseInt(_certiCode.substring(6, 10));
			monthID = Integer.parseInt(_certiCode.substring(10, 12));
			dayID = Integer.parseInt(_certiCode.substring(12, 14));
		} else {
			throw new IllegalArgumentException("身份证:[" + _certiCode +
					"]位数不正确");
		}

		if (_year != -1 && _year != yearID) {
			throw new IllegalArgumentException("身份证[" + _certiCode + "]校验错误，与出生年[" +
					_year + "]不符");
		}

		if (_month != -1 && _month != monthID) {
			throw new IllegalArgumentException("身份证[" + _certiCode + "]校验错误，与出生月[" +
					_month + "]不符");
		}

		if (_day != -1 && _day != dayID) {
			throw new IllegalArgumentException("身份证[" + _certiCode + "]校验错误，与出生天[" +
					_day + "]不符");
		}
	}

/*	  *//**
	 * 身份证有效性检验
	 * @param _certiCode 身份证
	 * @param _birthday 生日
	 *//*
	  public static void checkCertiCodeByBirthday(String _certiCode,
	                                              java.util.Date _birthday) {
	    checkParamNull("_certiCode", _certiCode);
	    checkParamNull("_birthday", _birthday);

	    Calendar calendar = SystemInfo.;

	    calendar.setTime(new java.util.Date(System.currentTimeMillis()));
	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH) + 1;
	    int day = calendar.get(Calendar.DAY_OF_MONTH);

	    checkCertiCodeByBirthday(_certiCode, year, month, day);
	  }*/

	/**
	 * 身份证性别校验
	 * @param _certiCode 身份证
	 * @param _gender 性别，1－男；2－女
	 * @throw java.lnag.IllegalArgumentException 身份证无效时掷出
	 */
	public static void checkCertiCodeByGender(String _certiCode, int _gender) {
		checkParamNull("_certiCode", _certiCode);

		int gendarNumID = -1;

		if (_certiCode.length() == 15) {
			gendarNumID = Integer.parseInt(_certiCode.substring(14));
		} else if (_certiCode.length() == 18) {
			gendarNumID = Integer.parseInt(_certiCode.substring(16, 17));
		} else {
			throw new IllegalArgumentException("身份证:[" + _certiCode +
					"]位数不正确");
		}

		if (gendarNumID % 2 == 0) {
			gendarNumID = 2;
		} else {
			gendarNumID = 1;
		}

		if (_gender != gendarNumID) {
			throw new IllegalArgumentException("身份证[" + _certiCode + "]校验错误，与性别[" +
					_gender + "]不符");
		}
	}
	/**
	 * Description:判断对象是否为空
	 * @param _obj
	 * @return 为空返回true,否则返回false
	 */
	public static boolean CheckNull(Object _obj) {
		return _obj==null;
	}
	/**
	 * 检查Email地址的有效性
	 * @param _emailStr Emial地址
	 * @throws IllegalArgumentException 当Email地址无效时掷出
	 * @since JDK1.4
	 */
	public static boolean validateEmail(String _emailStr)
	{
		boolean lb_Ret = true;
		String regexEmailGroup = "^(.+)@(.+)$";                                                                         // sam@com
		String regexEmailUserValidChar = "[^\\s\\(\\)><@,:;'\"\\.\\{\\}\\[\\]]+";                                       // 空格 \ ( ) < > @ , : ; ' " . [ ] { }
		String regexEmailDomainValidChar = ".*(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum)$";  // 合法域名
		String regexEmailIPAddress = "^\\[(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\]$";                       // IP地址
		// Domaim类别，1：友好名，2：IP地址
		int  domainStyle = 0;
		//分组
		Pattern pattern = Pattern.compile(regexEmailGroup);
		Matcher matcher = pattern.matcher(_emailStr);
		if(!matcher.matches())
		{
			lb_Ret = false;
			return lb_Ret;
		}
		//用户名部分
		String group1 = matcher.group(1);
		Pattern pattern1 = pattern.compile(regexEmailUserValidChar);
		Matcher matcher1 = pattern1.matcher(group1);
		if( !matcher1.matches())
		{
			lb_Ret = false;
			return lb_Ret;
		}
		//域部分，IP检查
		String group2 = matcher.group(2);
		Pattern pattern2 = Pattern.compile(regexEmailIPAddress);
		Matcher matcher2 = pattern2.matcher(group2);
		// 使用了IP
		if(matcher2.matches())
		{
			domainStyle = 2;
			// 解析 IP地址，255检查
			StringTokenizer st = new StringTokenizer(group2.substring(1, group2.length()-1), ".");
			while(st.hasMoreTokens())
			{
				int i = Integer.parseInt(st.nextToken());
				if(i > 255 )
				{
					lb_Ret = false;
					return lb_Ret;
				}
			}
		}
		return lb_Ret;
	}

	public static void main(String[] args)
	{
		String lS_Email = "@www";
		System.out.println(CheckData.validateEmail(lS_Email));
	}
}
