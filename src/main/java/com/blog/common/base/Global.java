package com.blog.common.base;

import java.util.Map;
import java.util.TreeMap;

/**
 * 系统常量
 */
public class Global {
    public static final int USER_STATUS_NORMAL = 0;//用户状态正常
    public static final int USER_STATUS_FREEZE = 1;//用户状态冻结
    //返回码
    public static final String RETURN_CODE_SUCCESS = "0000";
    public static final String RETURN_CODE_UNDIFINE_ERR = "-1";
    public static final String RETURN_CODE_PARAM_ERR = "-2";  //参数有误
    public static final String RETURN_CODE_SERVER_ERR = "500";
    /**
     * 返回码 没登录
     */
    public static final int RETURN_CODE_UNAUTHENTICATED = 401;
    /**
     * 返回码 无权限
     */
    public static final int RETURN_CODE_UNAUTHORIZED = 403;

    public static final String WCYL_APP_REDIS_COMPANY_KEY = "WCYL_APP_REDIS_COMPANY";

    public static final String WCYL_APP_TOKEN_NAME = "appToken";

    public static final String WCYL_SESSION_LOGON_INFO_NAME = "logonInfo";

    public static final String WCYL_SESSION_OLOGON_INFO_NAME = "oLogon";

    public static final String WCYL_SYS_ID="WCYL";

    public static final String SUPER_ROLE_ID="4A4A4ABAE1956EA5E0532012090AEFDE";

    /**
     * 菜单类型
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年11月15日 下午1:24:29
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2),
        /**
         * 按钮
         */
        FUNCTION(3);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static Map<String, String> bsMachineTypeMap = new TreeMap<String, String>();

    public static final int JG_bs_MACHINE_TYPE_F = 1; //分条
    public static final int JG_bs_MACHINE_TYPE_P = 2; //平直

    static{
        bsMachineTypeMap.put(String.valueOf(JG_bs_MACHINE_TYPE_F),	"分条");
        bsMachineTypeMap.put(String.valueOf(JG_bs_MACHINE_TYPE_P), 	"平直");
    }
}
