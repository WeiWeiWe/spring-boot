package com.practice.mall.service;

import com.practice.mall.model.pojo.Category;
import com.practice.mall.model.request.AddCategoryReq;

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);
}
