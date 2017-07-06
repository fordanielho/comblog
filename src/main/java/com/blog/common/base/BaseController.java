package com.blog.common.base;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 基础控制器类
 */
public class BaseController {

    @Autowired(required=false)
    protected HttpServletRequest request;
    @Autowired(required=false)
    protected HttpServletResponse response;
    @Autowired(required=false)
    protected HttpSession session;


    Logger log = Logger.getLogger(BaseController.class);

    /**
     * 调用存储过程是否有错
     * @param map
     * @return
     */
    public Boolean procedureError(Map map){
        if(map!=null){
            if(map.get("OFLAG")!=null){
                int OFLAG = NumberUtils.toInt(map.get("OFLAG").toString());
                if(OFLAG==1){
                    return false;
                }

            }
        }
        return true;
    }

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Date.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                    .parse(value));
                        } catch (Exception e) {
                            try {
                                setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm")
                                        .parse(value));
                            } catch (ParseException e1) {
                                try{
                                    setValue(new SimpleDateFormat("yyyy-MM-dd")
                                            .parseObject(value));
                                }catch (ParseException e2) {
                                    setValue(null);
                                }
                            }
                        }
                    }

                    public String getAsText() {
                        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .format((Date) getValue());
                    }

                });

        dataBinder.registerCustomEditor(Long.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(Long.parseLong(value));
                        } catch (Exception e) {
                            setValue(-1L);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });

        dataBinder.registerCustomEditor(long.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(Long.parseLong(value));
                        } catch (Exception e) {
                            setValue(0L);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });

        dataBinder.registerCustomEditor(Integer.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(new Double(value).intValue());
                        } catch (Exception e) {
                            setValue(null);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });

        dataBinder.registerCustomEditor(int.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(new Double(value).intValue());
                        } catch (Exception e) {
                            setValue(0);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });

        dataBinder.registerCustomEditor(Double.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(new Double(value));
                        } catch (Exception e) {
                            setValue(null);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });

        dataBinder.registerCustomEditor(double.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(new Double(value));
                        } catch (Exception e) {
                            setValue(0);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });
    }

/*    *//**
     * 取得后台登录用户
     * @return
     *//*
    protected UserDetails getSecurityUser() {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean b = o instanceof UserDetails;
        if (b) {
            log.info(o.toString());
            return (UserDetails) o;
        }
        return null;
    }

    protected SysAdmin getCurSysAdmin() {
        UserDetails userDetails = getSecurityUser();
        if (userDetails != null) {
            return SpringContextHolder.getBean(ISysAdminService.class).findByAdminLoginId(userDetails.getUsername());
        }
        return null;
    }

    protected String sysAdminId() {
        SysAdmin sysAdmin = getCurSysAdmin();
        if(sysAdmin==null){
            return null;
        }
        return sysAdmin.getSysAdminId();
    }

    protected Boolean checkToken(String token){
        return RedisUtil.exists(token);
    }

    *//**
     * 在redis取companyId
     * @param token
     * @return
     *//*
    protected Long getCompanyId(String token){
        if(StringUtils.isBlank(token) || !checkToken(token)){
            return null;
        }
        return FString.toLong((String) RedisUtil.get(token));
    }

    *//**
     * app登录用户获得信息
     * @return
     *//*
    protected LoginVo getAppLoginVo(){
        if(request.getSession().getAttribute(Global.WCYL_SESSION_LOGON_INFO_NAME)!=null){
            return (LoginVo)request.getSession().getAttribute(Global.WCYL_SESSION_LOGON_INFO_NAME);
        }
        return null;
    }*/
}
