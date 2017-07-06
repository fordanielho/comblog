package com.blog.common.base.filter;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by majf on 2016/3/30.
 */
@Configuration
public class FilterConfig {

    /**
     *druid配置filter监控
     * **/
    @Bean
    public FilterRegistrationBean druidStatFilterBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());//druid uri 监控
        bean.setName("druidWebStatFilter");
        bean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");// 忽略资源
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Bean
    public ServletRegistrationBean DruidStatViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean();
        bean.setServlet(new StatViewServlet());
        bean.setName("DruidStatView");
        bean.addUrlMappings("/druid/*");
        return bean;
    }


//    @Bean
//    public FilterRegistrationBean appFilter(){
//        FilterRegistrationBean bean = new FilterRegistrationBean();
//        bean.setFilter(new AppFilter());
//        bean.setName("appFilter");
//        bean.addUrlPatterns("/app/");
//        return bean;
//    }

}
