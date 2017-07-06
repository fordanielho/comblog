package com.blog.common.base.util;

import java.util.regex.Pattern;

/**
 * 各种校验
 * @Author hejj
 */
public class ValidateUtil {

    public final static String NO_NEGATIVE_INTEGER = "^\\d+$";//非负整数（正整数   +   0）
    public final static String POSITIVE_INTEGER = "^[0-9]*[1-9][0-9]*$";//正整数
    public final static String NO_POSITIVE_INTEGER = "^((-\\d+)|(0+))$";//非正整数（负整数   +   0）
    public final static String NEGATIVE_INTEGER ="^-[0-9]*[1-9][0-9]*$";//负整数
    public final static String INTEGER ="^-?\\d+$";;//整数
    public final static String NO_NEGATIVE_DOUBLE ="^\\d+(\\.\\d+)?$";//非负浮点数（正浮点数   +   0）
    public final static String POSITIVE_DOUBLE ="^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";//正浮点数
    public final static String NO_POSITIVE_DOUBLE ="^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";//非正浮点数（负浮点数   +   0）
    public final static String NEGATIVE_DOUBLE ="^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$";//负浮点数
    public final static String DOUBLE ="^(-?\\d+)(\\.\\d+)?$";//浮点数

    /**
     * 最普通的验证表达式
     * @param macAddress 待验证字符串
     * @param reg 正则表达式
     * @return
     */
    public static boolean isMacAddress(String macAddress,String reg){
        System.out.println(macAddress+"====="+reg);
        return Pattern.compile(reg).matcher(macAddress).find();
    }

    /**
     * 验证多少位数字（正数），由0开始
     * @param num 待验证数字
     * @param limit 几位数
     * @return
     */
    public static boolean validateNum(String num,int limit){
        String reg = "^([1-9]\\d{0,"+(limit-1)+"}|0)?$";
        return isMacAddress(num,reg);
    }

    /**
     * 验证多少位数字数组（正数），由0开始
     * @param arr 数字数组
     * @param limit 几位数
     * @return
     */
    public static boolean validateNumArray(String[] arr,int limit){
        boolean flag = true;
        for(String num:arr){
            flag = validateNum(num,limit);
            if(!flag)
                break;
        }
        return flag;
    }

    /**
     * 验证由数字数组拼接成的数字字符串（正数），由0开始
     * @param arrStr
     * @param limit
     * @return
     */
    public static boolean validateNumArrayStr(String arrStr,int limit){
        return validateNumArray(arrStr.split(","),limit);
    }

    /**
     * 验证金钱
     * @param money 待验证金钱数目
     * @param limit 小数点前的几位数
     * @return
     */
    public static boolean validateMoney(String money,int limit){
        return  validateDouble(money,limit,2);
    }

    /**
     * 验证金钱数组（正数），由0开始
     * @param money 数字数组
     * @param limit 几位数
     * @return
     */
    public static boolean validateMoneyArr(String[] money,int limit){
        boolean flag = true;
        for(String num:money){
            flag =validateDouble(num,limit,2);
            if(!flag)
                break;
        }
        return flag;
    }
    /**
     * 验证由金钱数组拼接成的数字字符串（正数），由0开始
     * @param arrStr
     * @param limit
     * @return
     */
    public static boolean validateMoneyArrayStr(String arrStr,int limit){
        return validateMoneyArr(arrStr.split(","),limit);
    }

    /**
     * 验证小数
     * @param doubleStr 待验证的浮点数
     * @param limit 小数点钱的几位数
     * @param point 小数点后的几位数
     * @return
     */
    public static boolean validateDouble(String doubleStr,int limit,int point){
        String reg = "^(0|[1-9][0-9]{0,"+(limit-1)+"})(\\.[0-9]{1,"+point+"})?$";
        return isMacAddress(doubleStr,reg);
    }


    /**
     * 验证字符串长度，支持中英文（包括全角字符）、数字、下划线和减号 （全角及汉字算两位）,中文按二位计数
     * @param length
     * @param frm
     * @param to
     * @return
     */
    public static boolean validateLength(String length,int frm,int to){
        String validateStr = "^[\\w\\-－＿[０-９]\u4e00-\u9fa5\uFF21-\uFF3A\uFF41-\uFF5A]+$";
        boolean rs = isMacAddress(length,validateStr);
        if (rs) {
            int strLenth = getStrLength(length);
            if (strLenth < frm || strLenth > to) {
                rs = false;
            }
        }
        return rs;
    }

    /**
     * 获取字符串的长度，对双字符（包括汉字）按两位计数
     *
     * @param value
     * @return
     */
    public static int getStrLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }



}
