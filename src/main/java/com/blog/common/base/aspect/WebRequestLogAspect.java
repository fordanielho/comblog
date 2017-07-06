package com.blog.common.base.aspect;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * aop 日志输出
 * @author  hejj
 *
 */

@Aspect
@Component
public class WebRequestLogAspect {
    private static Logger logger = LoggerFactory.getLogger(WebRequestLogAspect.class);
    private JoinPoint joinPoint;

//    private ThreadLocal<MtRequestLog> mtReqLog = new ThreadLocal<MtRequestLog>();
//
//    @Autowired
//    private IMtRequestLogService iMtRequestLogService;

    @Pointcut("execution(public * org.opsteel.wcyl.module.*.controller..*.*(..))")
    public void webRequestLog() {}

    // @Order(5)
    @Before("webRequestLog()")
    public void doBefore(JoinPoint joinPoint) {

        try {
            long beginTime = System.currentTimeMillis();

            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            String beanName = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            String uri = request.getRequestURI();
            String remoteAddr = getIpAddr(request);
            String sessionId = request.getSession().getId();
            String user = (String) request.getSession().getAttribute("user");
            String method = request.getMethod();
            String params = "";
            if ("POST".equals(method)) {
                Object[] paramsArray = joinPoint.getArgs();
                params = argsArrayToString(paramsArray);
            } else {
                Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                params = paramsMap.toString();
            }

            Map<?,?> inputParamMap = request.getParameterMap();
            String queryString = request.getQueryString();
            logger.debug("uri=" + uri + "; beanName=" + beanName + "; remoteAddr=" + remoteAddr + "; user=" + user
                    + "; methodName=" + methodName + "; params=" + params);
            // 记录下请求内容
            logger.info("URL : " + request.getRequestURL().toString());
            logger.info("HTTP_METHOD : " + request.getMethod());
            logger.info("IP : " + request.getRemoteAddr());
            logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

            logger.info("ARGS : " + params);


//            MtRequestLog  mtRequestLog = new MtRequestLog();
//            mtRequestLog.setUrl(uri);
//            mtRequestLog.setBeanName(beanName);
//            mtRequestLog.setMethodName(methodName);
//            mtRequestLog.setHttpMethod(request.getMethod());
//            mtRequestLog.setParams(params != null ? params.toString() : "");
//            mtRequestLog.setIp(remoteAddr);
//            mtReqLog.set(mtRequestLog);
            //iMtRequestLogService.insert(mtReqLog.get());


        } catch (Exception e) {
            logger.error("***操作请求日志记录失败doBefore()***", e);
        }
    }

    // @Order(5)
    @AfterReturning(returning = "result", pointcut = "webRequestLog()")
    public void doAfterReturning(Object result) {
        try {
           // 处理完请求，返回内容
            ObjectMapper om = new ObjectMapper();
            logger.info("RESPONSE : " +  om.writeValueAsString(result));

            //optLogService.saveLog(optLog);
        } catch (Exception e) {
            logger.error("***操作请求日志记录失败doAfterReturning()***", e);
        }
    }


    /**
     * 获取登录用户远程主机ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 请求参数拼装
     *
     * @param paramsArray
     * @return
     */
    private String argsArrayToString(Object[] paramsArray) throws Exception {
        String params = "";

        ObjectMapper om = new ObjectMapper();
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {

                params+=om.writeValueAsString(paramsArray[i])+"";
            }
        }
        return params.trim();
    }

}
