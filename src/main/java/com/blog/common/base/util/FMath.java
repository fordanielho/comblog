package com.blog.common.base.util;


/**
 * 计算公共类
 * @author Ray
 *
 */
public class FMath {
	
	/**
	 * 取整
	 * @param v		待取整的值
	 * @param scale 取整到的位数，如0表示个位，1表示十位
	 */
	public static double roundInteger(double v, int scale){
		double iMul = Math.pow(10, scale);
		double v2 = Arith.div(v, iMul);
		double v3 = Math.ceil(v2);
		double v4 = Arith.mul(v3, iMul);
		return v4;
	}
	/**
	 * 获取数字的千分比表示字符串
	 * @param preNum 待处理的数字
	 * @return
	 */
	public static String getThousandStr(double preNum){
		double n = Arith.mul(preNum, 1000);
		return n + " ‰";
	}
	
	public static void main(String[] args){
		System.err.println(getThousandStr(0.0005));
	}
}
