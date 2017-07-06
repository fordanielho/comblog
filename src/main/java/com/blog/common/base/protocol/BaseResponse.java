package com.blog.common.base.protocol;

public class BaseResponse {
	private String returnMsg; // 返回说明
	private String recode; // 状态码
	private Object resultData; // 返回信息

	public BaseResponse() {
		super();
		this.recode = ReturnCode.cSucc; // 默认成功
		this.returnMsg = null;
		this.resultData = null;
	}

	public BaseResponse(String retCode, String msg, Object resultData) {
		super();
		this.recode = retCode;
		this.returnMsg = msg;
		this.resultData = resultData;
	}

	public Object getResultData() {
		return resultData;
	}

	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getRecode() {
		return recode;
	}

	public void setRecode(String recode) {
		this.recode = recode;
	}

	@Override
	public String toString() {
		return "BaseResponse [returnMsg=" + returnMsg + ", recode=" + recode + ", resultData=" + resultData + "]";
	}

}
