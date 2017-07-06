package com.blog.common.base.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {

    private static Environment env;

    @Autowired
    public  void setEnv(Environment env) {
        SystemConfig.env = env;
    }

    /**
     * 获取属性
     * @param key
     * @return
     */
    public static String getProperty(String key){

        return env.getProperty(key);

    }

    /**
     * 获取属性
     * @param key 属性key
     * @param defaultValue 属性value
     * @return
     */
    public static String getProperty(String key,String defaultValue){

         return env.getProperty(key,defaultValue);

    }

}