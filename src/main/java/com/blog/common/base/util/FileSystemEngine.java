package com.blog.common.base.util;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 上传工具类
 */
public class FileSystemEngine {

    private Logger logger = LoggerFactory.getLogger(FileSystemEngine.class);
    /**
     * 保存文件到本地路径
     * @param path 上传路径 例如:UploadConfig.getInvoicePath()，如果要新建路径，参照UploadConfig.getInvoicePath新建模式
     * @param uploadFile 要上传的文件
     * @return
     * @throws Exception
     */
    public static String saveLocalFile(String path,MultipartFile uploadFile) {
        String originName = uploadFile.getOriginalFilename();
        // 获取文件后缀名
        String ext = FilenameUtils.getExtension(originName);
        //logger.info("ext-----"+ext);
        String filename = TextUtil.getUUID() +"."+ ext;
        path = path+File.separator+filename;
        File source = new File( path );
        try {
            uploadFile.transferTo(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int indexUpload = path.indexOf("upload");

        return path.substring(indexUpload-1);
    }
}
