package com.practice.mall.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量值
 */
@Component
public class Constant {
    public static final String MALL_USER = "mall_user";
    public static final String SALT = "8312fjbvdfdq.msa,[12";

    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }
}
