package com.blog.common.base;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;

@PropertySource("classpath:upload.properties")
@Component
@ConfigurationProperties(prefix = "upload")
public class UploadConfig {

    public static String basePath;//基础路径
    public static String invoicePath;//发票路径

    public void setBasePath(String basePath) {
        UploadConfig.basePath = basePath;
    }

    public void setInvoicePath(String invoicePath) {
        UploadConfig.invoicePath = invoicePath;
    }

    public static String getInvoicePath(){
        File dir = new File(checkBasePath().getAbsolutePath()+UploadConfig.basePath+UploadConfig.invoicePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }

    public static File checkBasePath(){
        //获取跟目录
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()){
                path = new File("");
            }
            //如果上传目录为/static/images/upload/，则可以如下获取：
            if(path.getPath().indexOf("classes")>1){
                File parentFile = path.getParentFile();
                path = parentFile;
            }
            if(!path.exists()){
                path.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
