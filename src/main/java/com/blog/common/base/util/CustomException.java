package com.blog.common.base.util;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 自定义异常，用于抛出mybatis的增删改失败的异常
 * @author 王明昌
 * @since 2017年4月12日
 * 
 */
public class CustomException extends Exception{

	private static final long serialVersionUID = 1L;
	/**
	 * 检查新增是否成功
	 * @param lineNum 返回的影响行数（1为成功；0为失败）
	 * @return boolean
	 */
	public static Boolean checkInster(int lineNum){
		boolean flag = false;
		if(lineNum != 1){
			throw new RuntimeException("自定义新增异常");
		}
		flag = true;
		return flag;
	}
	/**
	 * 检查修改是否成功
	 * @param lineNum 返回的影响行数（1为成功；0为失败）
	 * @return boolean
	 */
	public static Boolean checkUpdate(int lineNum){
		boolean flag = false;
		if(lineNum != 1){
			throw new RuntimeException("自定义修改异常");
		}
		flag = true;
		return flag;
	}
	/**
	 * 检查删除是否成功
	 * @param lineNum 返回的影响行数（1为成功；0为失败）
	 * @return boolean
	 */
	public static Boolean checkDelete(int lineNum){
		boolean flag = false;
		if(lineNum != 1){
			throw new RuntimeException("自定义删除异常");
		}
		flag = true;
		return flag;
	}
	/**
	 * 检查删除多条是否成功
	 * @param lineNum 返回的影响行数（小于等于0为失败）
	 * @return boolean
	 */
	public static Boolean checkDeleteList(int lineNum){
		boolean flag = false;
		if(lineNum <= 0){
			throw new RuntimeException("自定义删除异常");
		}
		flag = true;
		return flag;
	}
	/**
	 * 手动回滚事务
	 * @return
	 */
	public static String rollBackTransaction(){
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return "事务回滚";
	}

}
