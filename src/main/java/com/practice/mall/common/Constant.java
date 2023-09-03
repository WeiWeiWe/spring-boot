package com.practice.mall.common;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

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

    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price desc", "price asc");
    }

    public interface SaleStatus {
        int NOT_SALE = 0; // 商品下架
        int SALE = 1; // 商品上架
    }

    public interface Cart {
        int UN_CHECKED = 0; // 購物車未選狀態
        int CHECKED = 1; // 購物車已選狀態
    }
}
