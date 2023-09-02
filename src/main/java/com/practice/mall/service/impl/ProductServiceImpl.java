package com.practice.mall.service.impl;

import com.practice.mall.exception.MallException;
import com.practice.mall.exception.MallExceptionEnum;
import com.practice.mall.model.dao.ProductMapper;
import com.practice.mall.model.pojo.Product;
import com.practice.mall.model.request.AddProductReq;
import com.practice.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;

    @Override
    public void add(AddProductReq addProductReq) {
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq, product);
        Product productOld = productMapper.selectByName(addProductReq.getName());

        if (productOld != null) {
            throw new MallException(MallExceptionEnum.NAME_EXISTED);
        }

        int count = productMapper.insertSelective(product);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.CREATE_FAILED);
        }
    }
}
