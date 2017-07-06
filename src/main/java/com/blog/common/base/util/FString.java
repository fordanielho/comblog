package com.blog.common.base.util;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * <p>Title :雅普兰Web开发框架</p>
 * <p>Description:</p>
 * <p>Created on 2004-3-4</p>
 * <p>Company :YPL</p>
 *  @author : wangs
 *  @version : 1.0
 */
public class FString {
  
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
   * Double类型的值保留两位小数
   */
  public static final int DOUBLE_TWO = 0;
  /**
   * 物流系统迁移图片地址
   */
  public static final String PICTURE_SOURCE = "http://img01.opsteel.cn";
	
	  /**
	   * 根据字符串，转化为Timestamp类型,便于DB插入处理
	   * @param _sDate 格式为：yyyy-mm-dd hh:mm:ss
	   * @return Timestamp的时间格式
	   */
	  public static Timestamp toTimestamp(String _sDate) {
	    Timestamp ts = null;
	    if (_sDate == null || "".equals(_sDate)) {
	      return null;
	    }
	    ts = Timestamp.valueOf(_sDate);
	    return ts;
	  }
	 
	  /**
	   * 根据字符串，转化为Time类型
	   * @param _sTime 格式为：hh:mm:ss
	   * @return Time的时间格式
	   */
	  public static Time toTime(String _sTime) {
	    Time ts = null;
	    if (_sTime == null || "".equals(_sTime)) {
	      return null;
	    }
	    ts = Time.valueOf(_sTime);
	    return ts;
	  }	  
	  
	/**
	 * 将时间串转变为时间对象，输入参数<b>_dateStr</b>必须遵循格式{@link #DATE_FORMAT_YMD}
	 * @param _dateStr 时间串
	 * @return 时间对象
	 */	
	public static Date toJavaDate(String _dateStr) {
	    return toJavaDate(_dateStr, DATE_FORMAT_YMD);
	}

	/**
	 * Description:将时间串转变为时间对象
	 * @param _dateStr 时间串
	 * @param _pattern 时间串使用的模式
	 * @return 时间对象
	 */
	public static Date toJavaDate(String _dateStr, String _pattern) {
	    Date date = null;
	    if (_dateStr == null || _dateStr.trim().equals("")) {
	      return null;
	    }
	    if (_pattern == DATE_FORMAT_YMD_LONG || _pattern.equals(DATE_FORMAT_YMD_LONG)){
	    	_pattern = DATE_FORMAT_YMD;
	    	String str = replaceString("-","",_dateStr);
	    	_dateStr = str;
	    }
	    SimpleDateFormat format = new SimpleDateFormat(_pattern);
	    try {
	      date = format.parse(_dateStr);
	    } catch (java.text.ParseException pe) {
	      throw new IllegalArgumentException("不能使用模式:[" + _pattern +
	          "]格式化时间串:[" + _dateStr + "]");
	    }
	    return date;
	}

	  /**
	   * 将时间串转变为数据库时间对象
	   * @param _dateStr 时间串
	   * @param _pattern 时间串使用的模式
	   * @return 时间对象
	   * @throw java.lang.IllegalArgumentException 当输入的时间串和它使用的模式不匹配时掷出
	   */
	  public static java.sql.Date toDate(String _dateStr, String _pattern) {
	    return FDate.changeToDBDate(toJavaDate(_dateStr, _pattern));
	  }

	  /**
	   * 将时间串转变为数据库时间对象，输入参数<b>_dateStr</b>必须遵循格式{@link #DATE_FORMAT_YMD}
	   * @param _dateStr 时间串
	   * @return 数据库时间对象
	   */
	  public static java.sql.Date toDate(String _dateStr) {
	    return FDate.changeToDBDate(toJavaDate(_dateStr, DATE_FORMAT_YMD_LONG));
	  }

	  /**
	   * 替换Html文档中的"&nbsp"为" ", "&lt"为"<", "&gt"为">"，"<br>"为"\r\n"
	   * @param _rawStr 原始Html文档
	   * @return 替换后的Html文档
	   */
	  public static String changeHtmlStr(String _rawStr) {
	    String str = null;
	    if (_rawStr != null) {
	      str = replaceString(" ", "&nbsp;", _rawStr);
	      str = replaceString("<", "&lt;", str);
	      str = replaceString(">", "&gt;", str);
	      str = replaceString("\r\n", "<br>", str);
	      str = replaceString("\"", "&quot;", str);
	      str = replaceString("\'", "&apos;", str);
	    }
	    return str;
	  }
	  /**
	   * 替换Html文档中的"&nbsp"为" ", "&lt"为"<", "&gt"为">"，"<br>"为"\r\n"
	   * 忽略了 空格和 回车的转换
	   * @param _rawStr 原始Html文档
	   * @return 替换后的Html文档
	   */
	  public static String changeHtmlStr_1(String _rawStr) {
		  String str = null;
		  if (_rawStr != null) {
			  //str = replaceString(" ", "&nbsp;", _rawStr);
			  str = replaceString("<", "&lt;", _rawStr);
			  str = replaceString(">", "&gt;", str);
			  //str = replaceString("\r\n", "<br>", str);
			  str = replaceString("\"", "&quot;", str);
			  //str = replaceString("\'", "&apos;", str);
		  }
		  return str;
	  }

	  /**
	   * 使用新串替换原有字符串重老串
	   * @param _oldStr 待替换的字符串
	   * @param _newStr 新字符串
	   * @param _wholeStr 整个字符串
	   * @return 替换后新串
	   */
	  public static String replaceString(String _oldStr, String _newStr,
	                                     String _wholeStr) {
	    if (_wholeStr == null) {
	      return "";
	    }
	    if (_oldStr == null) {
	      return _wholeStr;
	    }
	    if (_newStr == null) {
	      return _wholeStr;
	    }
	    int start, end;
	    start = 0;
	    /*
	         //jdk 1.4
	         StringBuffer result=new StringBuffer();
	         result=result.append(_wholeStr);
	         while ( result.indexOf(_oldStr, start)>-1) {
	       start=result.indexOf(_oldStr, start);
	       end=start+_oldStr.length();
	       result.replace(start,end,_newStr);
	       start += _newStr.length();
	         }
	     */
	    String result = new String(_wholeStr);
	    while (result.indexOf(_oldStr, start) > -1) {
	      start = result.indexOf(_oldStr, start);
	      end = start + _oldStr.length();
	      result = result.substring(0, start) + _newStr + result.substring(end);
	      start += _newStr.length();
	    }
	    return result.toString();
	  }

	  /**
	   * 将一个使用token分隔符分隔的字符串，转变为一个字符串数组。
	   * @param _str 用token分隔符分隔的字符串
	   * @param _token 字符串的分隔符
	   * @return 字符串数组
	   */
	  public static String[] toArray(String _str, String _token) {
	    if (_str == null) {
	      return null;
	    } else {
	      Vector v = toVector(_str, _token);
	      String[] strArray = new String[v.size()];
	      int i = 0;
	      for (Enumeration em = v.elements(); em.hasMoreElements(); i++) {
	        strArray[i] = (String) em.nextElement();
	      }
	      return strArray;
	    }
	  }

	  /**
	   * 将一个使用","分隔符分隔的字符串，转变为一个字符串数组。
	   * @param _str 用token分隔符分隔的字符串
	   * @return 字符串数组
	   */
	  public static String[] toArray(String _str) {
	    CheckData.checkParamNull("_str", _str);

	    return toArray(_str, ",");
	  }

	  /**
	   * 将一个以字符串token分割的字符串，转换为一个Vector对象。如"姓名[token]年龄"被转换为一个Vector，该Vector包含两个元素，第一个是"姓名"，第二个是"年龄"。
	   * @param _str 需要转换的字符串
	   * @param _token 字符串中分割的token。如空格" "，或":"等。
	   * @return 包含了字符串中元素的Vector对象。
	   */
	  public static Vector toVector(String _str, String _token) {
	    CheckData.checkParamNull("_str", _str);
	    CheckData.checkParamNull("_token", _token);

	    Vector temp = new Vector();
	    StringTokenizer st = new StringTokenizer(_str, _token);
	    while (st.hasMoreTokens()) {
	      temp.add(st.nextToken());
	    }
	    return temp;
	  }

	  /**
	   * 将一个以','分割的字符串，转换为一个Vector对象。这是changeStringToVector(String str, String token)的简化版本。
	   * @param _str 需要转换的字符串
	   * @return 包含了字符串中元素的Vector对象。
	   * @see #
	   */
	  public static Vector toVector(String _str) {
	    return toVector(_str, ",");
	  }

	  /**
	   * 将字符串转换为HTML形式，以便在JavaScript中使用
	   * @param sourceStr 原字符串
	   * @return 转换后的字符串
	   */
	  public static String toHTMLStr(String sourceStr) {
	    if (sourceStr == null) {
	      return null;
	    }
	    StringBuffer buff = new StringBuffer(1024);
	    int n = sourceStr.length();
	    char c;
	    for (int i = 0; i < n; i++) {
	      c = sourceStr.charAt(i);
	      if (c == '"') {
	        buff.append('\\');
	        buff.append(c);
	      } else if (c == '\\') {
	        buff.append('\\');
	        buff.append(c);
	      } else if (c == '\r') {
	        buff.append("\\r");
	      } else if (c == '\n') {
	        buff.append("\\n");
	      } else {
	        buff.append(c);
	      }
	    }
	    return buff.toString();
	  }

	  /**
	   * 字符串处理， 当<b>_str</b>为null时用<b>_replaceStr</b>替代
	   * @param _str 原始字符串
	   * @param _replaceStr 替代null值的字符串
	   * @return 处理后的字符串
	   */
	  public static String toString(String _str, String _replaceStr) {
	    if (_str == null) {
	      return _replaceStr;
	    } else {
	      return _str.trim();
	    }
	  }

	  /**
	   * 字符串处理， 当<b>_str</b>为null时用<b>""</b>替代
	   * @param _str 原始字符串
	   * @return 处理后的字符串
	   */
	  public static String toString(String _str) {
	    return toString(_str, "");
	  }

	  /**
	   * 根据输入的String返回BigDecimal，或者若String非数字串，返回null
	   * @param _str  待转化的字符串
	   * @return BigDecimal对象
	   */
	  public static BigDecimal toBigDecimal(String _str) {
	    BigDecimal bd = null;
	    if (_str != null) {
	      try {
	        bd = new BigDecimal(_str);
	      } catch (Exception e) {
	        return null;
	      }
	    }
	    return bd;
	  }

	  /**
	   * 格式化双精浮点数
	   * @param inStr 双精浮点数字串
	   * @return 双精度浮点数，
	   */
	  public static double toDouble(String inStr) {
	    if (inStr == null || inStr.trim().equals("")) {
	      return 0;
	    } else {
	      return Double.valueOf(inStr).doubleValue();
	    }
	  }

	  /**
	   * 格式化双精浮点数,小数点后两位
	   * @param inStr 双精浮点数字串
	   * @return 双精度浮点数，
	   */
	  public static double toDouble(String inStr,int _type) {
	  	double d = toDouble(inStr);
	  	if (_type==DOUBLE_TWO){
	  		DecimalFormat df2 = new DecimalFormat("##.00");
	  		return toDouble(df2.format(d));
	  	}else{
	  		return d;
	  	}
	  }

	  /**
	   * 格式化浮点数
	   * @param inStr 浮点数字串
	   * @return 浮点数，如果数据格式错误，或字串为空，这返回0
	   */
	  public static float toFloat(String inStr) {
	    if (inStr == null || inStr.trim().equals("")) {
	      return 0;
	    } else {
	      return Float.valueOf(inStr).floatValue();
	    }
	  }

	  /**
	   * 格式化整形数
	   * @param inStr 整形数字串
	   * @return 整形数
	   */
	  public static int toInt(String inStr) {
	    if (inStr == null || inStr.trim().equals("")) {
	      return 0;
	    } else {
	      return new Integer(inStr).intValue();
	    }
	  }

	  /**
	   * 格式化整形数
	   * @param inStr 整形数字串
	   * @return 整形数
	   */
	  public static int[] toInts(String [] inStr) {

	  	int[] toint=null;
	    if (inStr != null )
	    { toint=new int[inStr.length];
	      for(int i=0;i<inStr.length;i++)
	      {
	      	toint[i]=FString.toInt(inStr[i]);
	      }
	    }
	    return toint;
	  }


	  /**
	   * 格式化长整形数
	   * @param inStr 长整形字串
	   * @return 长整形数
	   */
	  public static long toLong(String inStr) {
	    if (inStr == null || inStr.trim().equals("")) {
	      return 0;
	    } else {
	      return Long.valueOf(inStr).longValue();
	    }
	  }
	  /**
	   * 判断是否为数字
	   * @param
	   * @return
	   */
	  public static boolean isNumeric(String str){
		  for (int i = str.length();--i>=0;){
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
		 }
	  /**
	    * <p>Description:改变编码方式为本地编码</p>
	    * @param _str
	    * @return
	   */

	  public static String toLocalEncoding(String _str) {
	    String isoStr = null;
	    try {
	      isoStr = new String(_str.getBytes("GBK"), System.getProperties().getProperty("file.encoding"));
	    } catch (java.io.UnsupportedEncodingException ue) {
	      ue.printStackTrace();
	      throw new RuntimeException(ue.getMessage());
	    }
	    return isoStr;

	  }

	  /**
	   * 改变字符串的编码方式(GBK)为(ISO8859_1)
	   * @param _str 待转变的字符串
	   * @return 采用ISO8859_1编码的字符串
	   */
	  public static String changeFromGBK(String _str) {
	    String isoStr = null;
	    if (_str==null) return null;
	    try {
	      isoStr = new String(_str.getBytes("GBK"), "ISO8859_1");
	    } catch (java.io.UnsupportedEncodingException ue) {
	      ue.printStackTrace();
	      throw new RuntimeException(ue.getMessage());
	    }
	    return isoStr;
	  }

	  /**
	   * 改变字符串的编码方式(ISO8859_1)为(GBK)，以支持中文
	   * @param _str 待转变的字符串
	   * @return 采用GBK编码的字符串
	   */
	  public static String toGBK(String _str) {
	    String gbStr = null;
	    if (_str==null) return null;
	    try {
	      gbStr = new String(_str.getBytes("ISO8859_1"), "GBK");
	    } catch (java.io.UnsupportedEncodingException ue) {
	      ue.printStackTrace();
	      throw new RuntimeException(ue.getMessage());
	    }
	    return gbStr;
	  }
	  public static String toUtf8(String s)
	   {

	      StringBuffer sb = new StringBuffer();
	      for (int i = 0; i < s.length(); i++)
	      {
	         char c = s.charAt(i);
	         if (c >= 0 && c <= 255)
	         {
	            sb.append(c);
	         }
	         else
	         {
	            byte[] b;
	            try
	            {
	               b = Character.toString(c).getBytes("utf-8");
	            }
	            catch (Exception ex)
	            {
	               System.out.println(ex);
	               b = new byte[0];
	            }
	            for (int j = 0; j < b.length; j++)
	            {
	               int k = b[j];
	               if (k < 0)
	                  k += 256;
	               sb.append("%" + Integer.toHexString(k).toUpperCase());
	            }
	         }
	      }
	      return sb.toString();
	   }

	  /**
	   * 将字符串转化为字节，字符串必须是3位的数字串，并且代表的数字范围为[0,256]，如234<br>
	   * 该方法是{@link #(byte)}方法的逆操作。
	   * @param _str 待转化字符串
	   * @return 字节
	   */
	  public static byte toByte(String _str) {
	    CheckData.checkParamNull("_str", _str);
	    if (_str.length() != 3) {
	      throw new IllegalArgumentException(
	          "Parameter _str Must Be 3 Bits");
	    }
	    return (byte) Integer.parseInt(_str);
	  }

	  /**
	   * 将字符串转化为字节数组，字符串的长度必须是3的倍数，并且每3位代表的数字范围为[0,256]，如234001<br>
	   * 该方法是{@link #(byte[])}方法的逆操作。
	   * @param _str 待转化字符串
	   * @return 字节数组
	   */
	  public static byte[] toBytes(String _str) {
	    String s = null;
	    String tStr = new String(_str);
	    byte[] bytes = new byte[ (int) (_str.length() / 3)];
	    for (int i = 0; i < bytes.length; i++) {
	      s = tStr.substring(i * 3, (i + 1) * 3);
	      bytes[i] = toByte(s);
	    }
	    return bytes;

	  }

	  /**
	   * 将字符串采用编码<b>_encoding</b>转化为字节数组
	   * @param _str 字符串
	   * @param _encoding 编码方式
	   * @throws IllegalArgumentException 如果编码方式不支持时掷出
	   * @return 字节数组
	   */
	  public static byte[] toBytes(String _str, String _encoding) {
	    byte[] b = null;
	    try {
	      b = _str.getBytes(_encoding);
	    } catch (Exception e) {
	      throw new IllegalArgumentException("no support encoding:" + _encoding);
	    }
	    return b;
	  }

	  /*
	   * 得到当前日期时间字符串（yyyymmddhhmiss）
	   */
	  public static String getCurDTString(){
	  	String str = FDate.getCurTime().toString();
		str = FString.replaceString(" ","",str);
		str = FString.replaceString(":","",str);
		str = FString.replaceString("-","",str);
		str = str.substring(0,14);

	  	return str;
	  }
	  /*
	   * 格式化BigDecimal型数据，去掉小数两位后数据
	   */
	  public static String formatBDToString(BigDecimal aBD_Source)
	  {
		  String lS_Ret = "";
		  String lS_Temp = String.valueOf(aBD_Source);
		  if (lS_Temp.indexOf(".") > 0)
		  {
			  int li_Position = lS_Temp.indexOf(".");
			  if (lS_Temp.length() == li_Position + 3)
			  {
				  lS_Ret = lS_Temp;
			  }
			  else if (lS_Temp.length() > li_Position + 3)
			  {
				  lS_Ret = lS_Temp.substring(0,li_Position + 3);
			  }
			  else if(lS_Temp.length() == li_Position + 2)
			  {
				  lS_Ret = lS_Temp + "0";
			  }
		  }
		  else
		  {
			  lS_Ret = lS_Temp + ".00";
		  }
		  return lS_Ret;
	  }

	  public static String InputStreamToString(InputStream _in)
	  {
		  String lS_Ret = null;
		  try
		  {
			  OutputStream out = new ByteArrayOutputStream();
			  byte[] buffer = new byte[1];
			  while (_in.read(buffer) != -1)
			  {
				  out.write(buffer);
				  out.flush();
			  }
			  lS_Ret = out.toString();
		  }
		  catch(IOException IEx)
		  {
			  IEx.getMessage();
		  }
		  return toString(lS_Ret,"");
	  }

	  public static String getFirstString(String aS_OrignalStr)
	  {
		  String lS_Ret = "";
		  if (aS_OrignalStr == null)
		  {
			  return lS_Ret;
		  }
		  int li_StartPos = aS_OrignalStr.indexOf(",");
		  if (li_StartPos < 0)
		  {
			  lS_Ret = aS_OrignalStr;
		  }
		  else
		  {
			  lS_Ret = aS_OrignalStr.substring(0,li_StartPos);
		  }
		  return lS_Ret;
	  }

	  public static String getStandardTime(Date aD_OrignalTime)
	  {
		  String lS_Ret = "";
		  if (aD_OrignalTime == null)
		  {
			  return lS_Ret;
		  }
		  else
		  {
			  String lS_OrignalTime = String.valueOf(aD_OrignalTime);
			  if (lS_OrignalTime.length() > 2)
			  {
				  lS_Ret = lS_OrignalTime.substring(0,lS_OrignalTime.length()-2);
			  }
		  }
		  return lS_Ret;
	  }

	  public static String numberToChinese(double d)
	  {
	      DecimalFormat decimalformat = new DecimalFormat("############0.00");
	      String s = decimalformat.format(d);
	      int i = s.indexOf(".");
	      if(s.substring(i).compareTo(".00") == 0)
	      s = s.substring(0, i);
	      String s1 = "";
	      String as[] = new String[4];
	      String as1[] = new String[4];
	      String as2[] = new String[2];
	      String s2 = "";
	      String s4 = "";
	      String s6 = "";
	      int j = 0;
	      int k = 0;
	      boolean flag = false;
	      as[0] = "";
	      as[1] = "\u62FE";
	      as[2] = "\u4F70";
	      as[3] = "\u4EDF";
	      as1[0] = "";
	      as1[1] = "\u4E07";
	      as1[2] = "\u4EBF";
	      as1[3] = "\u4E07";
	      as2[0] = "\u5206";
	      as2[1] = "\u89D2";
	      if(s.compareTo("0") == 0 || s.compareTo("0.0") == 0 || s.compareTo("0.00") == 0)
	      {
	      s6 = "\u96F6\u5143\u6574";
	      return s6;
	      }
	      if(s.indexOf(".") > 0)
	      s1 = s.substring(0, s.indexOf("."));
	      else
	      s1 = s;
	      j = s1.length() % 4 == 0 ? s1.length() / 4 : s1.length() / 4 + 1;
	      for(int i1 = j; i1 >= 1; i1--)
	      {
	      int l;
	      if(i1 == j && s1.length() % 4 != 0)
	      l = s1.length() % 4;
	      else
	      l = 4;
	      String s3 = s1.substring(k, k + l);
	      for(int j1 = 0; j1 < s3.length(); j1++)
	      if(Integer.parseInt(s3.substring(j1, j1 + 1)) != 0)
	      switch(Integer.parseInt(s3.substring(j1, j1 + 1)))
	      {
	      case 1: // "\001"
	      s6 = s6 + "\u58F9" + as[s3.length() - j1 - 1];
	      break;

	      case 2: // "\002"
	      s6 = s6 + "\u8D30" + as[s3.length() - j1 - 1];
	      break;

	      case 3: // "\003"
	      s6 = s6 + "\u53C1" + as[s3.length() - j1 - 1];
	      break;

	      case 4: // "\004"
	      s6 = s6 + "\u8086" + as[s3.length() - j1 - 1];
	      break;

	      case 5: // "\005"
	      s6 = s6 + "\u4F0D" + as[s3.length() - j1 - 1];
	      break;

	      case 6: // "\006"
	      s6 = s6 + "\u9646" + as[s3.length() - j1 - 1];
	      break;

	      case 7: // "\007"
	      s6 = s6 + "\u67D2" + as[s3.length() - j1 - 1];
	      break;

	      case 8: // "\b"
	      s6 = s6 + "\u634C" + as[s3.length() - j1 - 1];
	      break;

	      case 9: // "\t"
	      s6 = s6 + "\u7396" + as[s3.length() - j1 - 1];
	      break;
	      }
	      else
	      if(j1 + 1 < s3.length() && s3.charAt(j1 + 1) != '0')
	      s6 = s6 + "\u96F6";

	      k += l;
	      if(i1 < j)
	      {
	      if(Integer.parseInt(s3.substring(s3.length() - 1, s3.length())) != 0 || Integer.parseInt(s3.substring(s3.length() - 2, s3.length() - 1)) != 0 || Integer.parseInt(s3.substring(s3.length() - 3, s3.length() - 2)) != 0 || Integer.parseInt(s3.substring(s3.length() - 4, s3.length() - 3)) != 0)
	      s6 = s6 + as1[i1 - 1];
	      } else
	      {
	      s6 = s6 + as1[i1 - 1];
	      }
	      }

	      if(s6.length() > 0)
	      s6 = s6 + "\u5143";
	      if(s.indexOf(".") > 0)
	      {
	      String s5 = s.substring(s.indexOf(".") + 1);
	      for(int k1 = 0; k1 < 2; k1++)
	      if(Integer.parseInt(s5.substring(k1, k1 + 1)) != 0)
	      switch(Integer.parseInt(s5.substring(k1, k1 + 1)))
	      {
	      case 1: // "\001"
	      s6 = s6 + "\u58F9" + as2[1 - k1];
	      break;

	      case 2: // "\002"
	      s6 = s6 + "\u8D30" + as2[1 - k1];
	      break;

	      case 3: // "\003"
	      s6 = s6 + "\u53C1" + as2[1 - k1];
	      break;

	      case 4: // "\004"
	      s6 = s6 + "\u8086" + as2[1 - k1];
	      break;

	      case 5: // "\005"
	      s6 = s6 + "\u4F0D" + as2[1 - k1];
	      break;

	      case 6: // "\006"
	      s6 = s6 + "\u9646" + as2[1 - k1];
	      break;

	      case 7: // "\007"
	      s6 = s6 + "\u67D2" + as2[1 - k1];
	      break;

	      case 8: // "\b"
	      s6 = s6 + "\u634C" + as2[1 - k1];
	      break;

	      case 9: // "\t"
	      s6 = s6 + "\u7396" + as2[1 - k1];
	      break;
	      }
	      else
	      if(s6.length() > 0)
	      s6 = s6 + "\u96F6";

	      } else
	      {
	      s6 = s6 + "\u6574";
	      }
	      if(s6.substring(s6.length() - 1).compareTo("\u96F6") == 0)
	      s6 = s6.substring(0, s6.length() - 1);
	      return s6;
	  }

	  public static String formatVolume(double weight)
	  {
	      DecimalFormat dfWeight = new DecimalFormat("#,###");
	      String fweight = dfWeight.format(weight);
	      return fweight;
	  }
	  /**
	   * 格式化为3位小数,Ray 2011-1-10
	   * @param weight
	   * @return
	   */
	  public static String formatWeight(double weight)
	  {
	      weight = round(weight,3);
	      DecimalFormat dfWeight = new DecimalFormat("#,###.000");
	      String fweight = dfWeight.format(weight);
	      if (fweight !=null && fweight.length()>1 && fweight.substring(0,1).equals("."))
	      {
	      	fweight = "0"+fweight;
	      }
	      if (fweight !=null && fweight.length()>1 && fweight.substring(0,2).equals("-."))
	      {
	      	fweight = fweight.replaceAll("-.","-0.");
	      }
	      return fweight;
	  }

	  public static String formatNumAddZero(double iNum,int scale)
	  {
		  String fweight = formatNum(iNum,scale);
		  if (fweight !=null && fweight.length()>1 && fweight.substring(0,1).equals("."))
	      {
	      	fweight = "0"+fweight;
	      }
	      if (fweight !=null && fweight.length()>1 && fweight.substring(0,2).equals("-."))
	      {
	      	fweight = fweight.replaceAll("-.","-0.");
	      }
	      return fweight;
	  }

	  public static String formatAmount(double amount)
	  {
	      amount = round(amount,2);
	      DecimalFormat dfAmount = new DecimalFormat("#,###.00");
	      String famount = dfAmount.format(amount);
	      if (famount !=null && famount.length()>1 && famount.substring(0,1).equals("."))
	      {
	      	famount = "0"+famount;
	      }
	      if (famount !=null && famount.length()>1 && famount.substring(0,2).equals("-."))
	      {
	      	famount = famount.replaceAll("-.","-0.");
	      }
	      return famount;
	  }

	  public static String formatAmountWithNull(double amount)
	  {
	      amount = round(amount,2);
	      DecimalFormat dfAmount = new DecimalFormat("#,###.00");
	      String famount = dfAmount.format(amount);
	      if (famount !=null && famount.length()>1 && famount.substring(0,1).equals("."))
	      {
	    	  if (amount>0){
	  	      	famount = "0"+famount;
	    	  }else{
	    		  famount = "";
	    	  }
	      }
	      if (famount !=null && famount.length()>1 && famount.substring(0,2).equals("-."))
	      {
	      	famount = famount.replaceAll("-.","-0.");
	      }
	      return famount;
	  }

	  public static String formatPrice(double amount)
	  {
	      amount = round(amount,2);
	      DecimalFormat dfAmount = new DecimalFormat("####.00");
	      return dfAmount.format(amount);
	  }


	  public static String formatNum(double iNum,int scale)
	  {
	  	iNum = round(iNum,scale);
	  	String strTemp = "";
	  	for(int i = 0;i<scale;i++)
	  	{
	  		strTemp = strTemp + "0";
	  	}
	  	strTemp = "#,###." + strTemp;
	      DecimalFormat dfTemp = new DecimalFormat(strTemp);
	  	return dfTemp.format(iNum);
	  }

	  public static double round(double v,int scale)
	  {
	  	BigDecimal b = new BigDecimal(Double.toString(v));
	  	BigDecimal one = new BigDecimal("1");
	  	return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();

	  }
	  /**
	   * 判断输入内容是否含有敏感字符
	   * @param aS_BanWord
	   * @param aS_Content
	   * @return
	   */
/*	  public static boolean isBanWord(String aS_BanWord , String aS_Content)
	  {
		  boolean lb_Ret = false;
		  java.util.regex.Matcher   m = java.util.regex.Pattern.compile(aS_BanWord).matcher(aS_Content);
		  if(m.find())
		  {
			  lb_Ret = true;
		  }
		  else
		  {
			  lb_Ret = false;
		  }
		  return lb_Ret;
	  }*/
	  /**
	   * 判断输入内容是否含有敏感字眼,
	   * 如：敏感字眼："开发票"，那么就进行类似PLSQL中的 like '%开%发%票%' 的判断。
	   * @param aS_BanWord 敏感字眼
	   * @param aS_Content 要判断的文本
	   * @return 返回true - 是包含敏感字眼，返回false - 不包含
	   */
	   public static boolean isBanWord(String aS_BanWord , String aS_Content){
		   //使用正则表达式 判断：
		   boolean result = false;

		   if(aS_BanWord != null && aS_BanWord.length() > 0
				   && aS_Content != null  && aS_Content.length() > 0){
			   /*//敏感字眼变正则
			   StringBuffer aS_BanWordSB = new StringBuffer(128);
			   char[]  aS_BanWord_char = aS_BanWord.toCharArray();
			   aS_BanWordSB.append("(.*)");
			   for(int i = 0 ; i < aS_BanWord_char.length ; i ++ ){
				   aS_BanWordSB.append(aS_BanWord_char[i]+"(.*)");
			   }*/

			   //判断是否包含
			   result =	aS_Content.matches(aS_BanWord.toString());
		   }

		   return result;
	   }


	  /**
	   * 判断输入内容是否含有敏感字眼
	   * @param listBanWord 一批 敏感字眼 须正则编码好以后 见opsteel.info.member.CompaniesManager.jgetBanWord()
	   * @param listContent 一批 要判断的文本
	   * @return 返回true - 是包含敏感字眼，返回false - 不包含
	   */
	   public static boolean isBanWord(List<String> listBanWord , List<String> listContent){
		   //循环调用isBanWord(String aS_BanWord , String aS_Content) 来判断。
		   boolean result = false ;
		   boolean retult_temp = false ;
		   if( listBanWord != null && listBanWord.size() > 0
				   &&  listContent != null &&  listContent.size() > 0 ){

			   for( int i = 0 ; i < listContent.size() ; i ++ ){//对判断文本进行循环
				   for( int j =0 ; j < listBanWord.size() ; j ++ ){//对敏感字眼进行循环

					   retult_temp = isBanWord(listBanWord.get(j) , listContent.get(i));
					   if( retult_temp ){//如果包含敏感字眼，结束循环
						   result = retult_temp ;
						   break;
					   }

				   }
			   }

		   }

		   return result;
	   }

	  /**
	   * 把数组转化成字符串
	   * @param aArr_origin
	   * @return
	   */
	  public static String toStringFromArray(String[] aArr_origin)
	  {
		  String aS_Ret = "";
		  if (aArr_origin != null)
		  {
			  for (int i = 0 ; i < aArr_origin.length ; i ++)
			  {
				  aS_Ret = aS_Ret + "'" + aArr_origin[i] + "',";
			  }
		  }
		  if (aS_Ret.length() > 0)
		  {
			  aS_Ret = aS_Ret.substring(0, aS_Ret.length() - 1);
		  }
		  return aS_Ret;
	  }

	  /**
	     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
	     *
	     * @param  c, 需要判断的字符
	     * @return boolean, 返回true,Ascill字符
	  */
	    public static boolean isLetter(char c) {
	        int k = 0x80;
	        return c / k == 0 ? true : false;
	    }

	   public static int length(String s)
	   {
		   if (s == null) return 0;
		   char[] c = s.toCharArray();
		   int len = 0;
		   for (int i = 0; i < c.length; i++)
		   {
			   len++;
			   if (!isLetter(c[i]))
			   {
				   len++;
			   }
		   }
		   return len;
	   }

	   /**
	     * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位
	     * @param String
	     *            origin, 原始字符串
	     * @param int
	     *            len, 截取长度(一个汉字长度按2算的)
	     * @return String, 返回的字符串
	     */
	/**    public static String substring(String origin, int length) {
	    	char [] t = origin.toCharArray();
		       String tt ="";
		       int len =0;
		       for (int i=0;i<t.length;i++)
		       {
		    	   len++;
		    	   if (!isLetter(t[i]))
				   {
		    		   len++;
				   }

		    	   if (len==length)
	    		   {
		    		   tt+=t[i];
	    			   break;
	    		   }else if (len>length)
	    		   {
	    			   break;
	    		   }

		    	   tt+=t[i];
		       }
		        return tt;
	    }

	    **/
	    /**
	     * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位
	     * @param String
	     *            origin, 原始字符串
	     * @param int
	     *            len, 截取长度(一个汉字长度按2算的)
	     * @return String, 返回的字符串
	     *//**
	    public static String substring(String origin, int length,int flag) {
	    	origin = changeB2Q(origin);
	    	char [] t = origin.toCharArray();
		       String tt ="";
		       int len =0;
		       if (origin==null) return "";

		       for (int i=0;i<t.length;i++)
		       {
		    	   len++;
		    	   if (!isLetter(t[i]))
				   {
		    		   len++;
				   }

		    	   if (len==length)
	    		   {
		    		   tt+=t[i];
	    			   break;
	    		   }else if (len>length)
	    		   {
	    			   break;
	    		   }

		    	   tt+=t[i];
		       }
		       if (flag==1)
		    	   if (length(origin)>length) tt+="...";
		        return tt;
	    }
	    **/
	   /**
	    * 由于 原本的substring 方法在新的服务器上 会出现部分乱码 所以更改了substring方法
	    */
	   public static String substring(String origin, int length) {
		   if(origin.length()>length/2){
			   return origin.substring(0,length/2);
		   }else
			   return origin;
	   }
	   public static String substring(String origin, int length,int flag) {
		   if(origin.length()>length/2&&flag==1){
			   return origin.substring(0,length/2)+"...";
		   }
		   else
			   return substring(origin,length);
	   }
	    /**
	     * 从全角转换到半角
	     * @param
	     *            QJstr, 全角字符串
	     * @return String, 返回的字符串
	     */
	    public static String changeQ2B(String QJstr)
	    {
	    	  String outStr = "";
	    	  String Tstr = "";
	    	  byte[] b = null;

	    	  for (int i = 0; i < QJstr.length(); i++)
	    	  {
	    	       try
	    	       {
	    	          Tstr = QJstr.substring(i, i + 1);
	    	          b = Tstr.getBytes("unicode");
	    	       }
	    	       catch(java.io.UnsupportedEncodingException e)
	    	       {
	    	          e.printStackTrace();
	    	       }

	    	       if (b[3] == -1)
	    	       {
	    	          b[2] = (byte) (b[2] + 32);
	    	          b[3] = 0;

	    	          try
	    	          {
	    	             outStr = outStr + new String(b, "unicode");
	    	          }catch(java.io.UnsupportedEncodingException e)
	    	          {
	    	             e.printStackTrace();
	    	          }
	    	        }
	    	       else
	    	           outStr = outStr + Tstr;
	    	     }

	       return outStr;
	   }


	    /**
	     * 从半角转换到全角
	     * @param  QJstr, 半角字符串
	     * @return String, 返回的字符串
	     */
	    public static String changeB2Q(String QJstr) {
		String outStr = "";
		String Tstr = "";
		byte[] b = null;

		for (int i = 0; i < QJstr.length(); i++) {
			try {
				Tstr = QJstr.substring(i, i + 1);
				b = Tstr.getBytes("unicode");
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}


			if (b[3] == 0) {
				//排除数字、英文大小写、\r\n
				if (!( (b[2]>=48 && b[2]<=57) || (b[2]>=97 && b[2]<=122) || (b[2]>=65 && b[2]<=90) || (b[2]==32) || b[2]==10 || b[2]==13))
				{
					b[2] = (byte) (b[2] - 32);
					b[3] = -1;

					try {
						outStr = outStr + new String(b, "unicode");
					} catch (java.io.UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}else
				{
					outStr = outStr + Tstr;
				}
			} else
				outStr = outStr + Tstr;
		}

		return outStr;
	}

	 /**
		 * 把输入日期转化为只有年月的格式
		 *
		 * @param aD_FromDate
		 * @return
		 */
	  public static String formatDateToYM(Date aD_FromDate)
	  {
		  String lS_Ret = "";
		  if (aD_FromDate != null)
		  {
			  String lS_FromDate = FDate.formatDate(aD_FromDate , FDate.DATE_FORMAT_YMD_LONG);
			  String lS_Year = lS_FromDate.substring(0,4);
			  String lS_Month = lS_FromDate.substring(5,7);
			  if (lS_Month.substring(0,1).equals("0"))
			  {
				  lS_Month = lS_Month.substring(1,2);
			  }
			  lS_Ret = lS_Year + "年" +  lS_Month + "月";
	  	  }
		  return lS_Ret;
	  }

	  /**

	   * 将一个字串的首字母大写

	   * @param s String 源字串

	   * @return String 首字母大写后的字串

	   */

	  public static String toUpperCaseFirstLetter(String s) {

		  return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();

	  }

	  public static String getClobStr(Clob cb)
	  {
			String str="";
			try{
				if(cb==null || cb.length()==0){
		    		return str;
		    	}else{
			    	Reader inStream = cb.getCharacterStream();
			        char[] c = new char[(int) cb.length()];
			        inStream.read(c);
			        str = new String(c);
			        inStream.close();
		    	}
			}catch (Exception ep){
			    ep.printStackTrace();
			}
			return str;
	  }

	  /** =========================================================
		//函数：RemoveHtml(s)
		//功能：去除HTML标记
		//参数：s --要去除HTML标记的字符串
		/=========================================================*/
	  public static String RemoveHtml(String s)
	  {
			String str="";
			try{
				if(s==null || s.length()==0){
		    		return str;
		    	}else{
		    		String regEx="<[.[^<]]*>"; //表示一个或多个a <...>
		    		Pattern p=Pattern.compile(regEx);
		    		Matcher m=p.matcher(s);
		    		str=m.replaceAll("");
		    	}
			}catch (Exception ep){
			    ep.printStackTrace();
			}
			return str.replaceAll("&nbsp;", " ");
	  }

	  public static String trimByLength(String s,int len,String replacement){
		  if(s == null || s.equals("")) return s;
		  if(len >= s.length()) return s;
		  if(replacement == null){

			  return s.substring(0, len);
		  }else{
			  return s.substring(0,len - replacement.length() + 1) + replacement;
		  }
	  }
	  private static java.text.Format _numberFormat = new DecimalFormat("0");
	  public static String getNumberStr(Object value){
		  if(value instanceof String ) return (String)value;
		  double a;
		  long b;
		  if(value instanceof Integer){
			  b = ((Integer)value).longValue();
			  a = b;
		  }else if(value instanceof Double){
			  a = ((Double)value).doubleValue();
			  b = ((Double)value).longValue();
		  }else if(value instanceof Long){
			  b = ((Long)value).longValue();
			  a = b;
		  }else if(value instanceof Short){
			  b = ((Short)value).shortValue();
			  a = b;
		  }else if(value instanceof Float){
			  a = ((Float)value).floatValue();
			  b = ((Float)value).longValue();
		  }else if(value instanceof Number){
			  a = ((Number)value).doubleValue();
			  b = ((Number)value).longValue();
		  }else{
			  a = 0; b = 0;
		  }
		  if(a == b){
			  return _numberFormat.format(b);
		  }else{
			  String v = String.valueOf(a - b);
			  return _numberFormat.format(b) + v.substring(1);
		  }
	  }
	  
	  public static String filterHtmlString(String input, int length) {  
	        if (input == null || input.trim().equals("")) {  
	            return "";  
	        }  
	        // 去掉所有html元素,  
	        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(  
	                "<[^>]*>", "");  
	        str = str.replaceAll("[(/>)<]", "");  
	        
	        str = str.replaceAll("&nbsp;", " ");
	        int len = str.length();
	        if (len <= length || length==0) {  
	            return str;  
	        } else {  
	            str = str.substring(0, length);  
	            str += "...";  
	        }  
	        return str;  
	    }  
	  
	  public static String checkWebAdd(String sWebAdd)
	  {
		  if (!sWebAdd.trim().equals("") && sWebAdd!=null)
		  {
			  return sWebAdd.toLowerCase().indexOf("http://")==0?sWebAdd.toLowerCase():"http://"+sWebAdd.toLowerCase();
		  }
		  return toString(sWebAdd);
	  }
	  
	  /**
	   * 清除字符串中的所有 回车换行 符
	   * @return
	   */
	  public static String clearCRLF(String s){
		  if(s == null){
			  return null;
		  }
		  String reg ="[\n-\r]";
		  Pattern p = Pattern.compile(reg);
		  Matcher m = p.matcher(s);
		  String clearStr = m.replaceAll(""); 
		  return clearStr;
	  }
	  
	  public static void main(String[] args)
	  {
		  //Date lD_date = new Date();
		  //System.out.println(FString.substring("最少输入5个汉字、最多输入25个汉字最少输入5个汉字。字",100,1));
		  String str = "\r\n";
		  changeB2Q(str);
		  
		  System.out.println("");
		  System.out.println(clearCRLF(null));
		  System.out.println(clearCRLF("\n   iii \n 44\r5"));
	  }	  	  
	  
	  /**
	   * 将HTML 实体 转换成汉字 如：&#38797;&#38050;
	   * @param str 
	   * @return
	   */
	  public static String HTMLDecode(String str){
		  /*
		  if(str!=null&&str.matches("(-?\\d+)(\\.\\d?)?")){
			  return str;
		  }else{
			  String[] temp=str.split(";&#|&#|;");
			  StringBuffer sb=new StringBuffer();
			  for(String s:temp){
				  if(s.matches("\\d{5}")){
					  sb.append((char)Integer.parseInt(s));
				  }else{
					  sb.append(s);
				  }
			  }
			  return sb.toString();
		  }*/
			String sRegex="&#\\d{5};";
			Pattern pat=Pattern.compile(sRegex);
			Matcher mat=pat.matcher(str);
			StringBuffer sb0=new StringBuffer();
			while(mat.find()){
				String ts=mat.group().substring(2, 7);
				mat.appendReplacement(sb0, String.valueOf((char)Integer.parseInt(ts)));
			}
			mat.appendTail(sb0);
			return sb0.toString();
	  }
	  
	    /**
	     * 格式化数字百份比
	     *
	     * @return
	     */
	    public static String formatPercent(double value) {
	        DecimalFormat decimalFormat = new DecimalFormat("##%");
	        String result = decimalFormat.format(value);
	        return result;
	    }
	    
	    /**
	     * 判断是否数字（包含小数）
	     * @param str
	     * @return
	     */
	    public static boolean isNum(String str){
	    	if(str==null||"".equals(str.trim())){
	    		return false;
	    	}
			return str.matches("^[-+]?[0-9]+(([.]([0-9]+))?|([.]([0-9]+))?)$");
		}
}			  
