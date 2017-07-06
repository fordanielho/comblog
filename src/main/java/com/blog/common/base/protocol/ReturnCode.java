package com.blog.common.base.protocol;

public class ReturnCode
{
	/*
0000	成功
0001	操作失败
1001	没有数据
1002	参数不正确
2001	数据库失败
2002	不能对自己进行关注！
2003	不能对非签约会员进行关注！
3001	登录失败
3002	登录令牌超时
3003	<注册接口> 账号已经存在，请重新输入！
3004	<注册接口> 请输入姓名（限4个字）！
3005	<注册接口> 请正确输入一个手机号码！
3006	<注册接口> 姓名 + 公司名已被注册，请重新输入姓名、公司名！
3007	<注册接口> 联系人名称已经存在，请重新输入！
3008	<注册接口> 客户编码重复，请重新输入！
3009	<注册接口> 不能设置自身公司为父公司
3010	<注册接口> 不能设置此公司为父公司，因其已经是别的公司的子公司
9998	出现异常
9999	其他错误
增加一个公共错误代码10000，此版本后的每一个接口的返回中，10000都对应一个rmsg(错误信息提示文本)，在下文接口中不再另述
	 */
	public static String cSucc = "0000";
	public static String cError = "0001";
	public static String cNoData = "1001";
	public static String cParaError = "1002";
	public static String cDbError = "2001";
	public static String cAttentionMyselfError = "2002";
	public static String cAttentionNoMemberError = "2003";
	public static String cLoginError = "3001";
	public static String cTokenError = "3002";
	public static String cRegist_1 = "3003";
	public static String cRegist_2 = "3004";
	public static String cRegist_3 = "3005";
	public static String cRegist_4 = "3006";
	public static String cRegist_5 = "3007";
	public static String cRegist_6 = "3008";
	public static String cRegist_7 = "3009";
	public static String cRegist_8 = "3010";
	public static String cmall_1 = "4001";
	public static String cmall_2 = "4002";
	public static String cMobileNoBind = "4003";
	public static String cmall_closed = "4004";
	public static String cException = "9998";
	public static String cOther = "9999";
	public static String cRmsg = "10000";
	
	public static String SLOGIN_TOKEN_FAILURE = "登陆超时，请重新登录";
	public static String SCOMPANY_INFO_NOEXIST = "公司信息不存在";
	public static String SSIGNER_NO = "您还未成为签约商";
	public static String SPARMER_NO_CORRECT = "参数不正确";
	
}
