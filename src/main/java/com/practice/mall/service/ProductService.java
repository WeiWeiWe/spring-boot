package com.practice.mall.service;

import com.github.pagehelper.PageInfo;
import com.practice.mall.model.pojo.Product;
import com.practice.mall.model.request.AddProductReq;

public interface ProductService {

    void add(AddProductReq addProductReq);

    void update(Product updateProduct);

    void delete(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);
}
