package com.practice.mall.service;

import com.github.pagehelper.PageInfo;
import com.practice.mall.model.pojo.Category;
import com.practice.mall.model.request.AddCategoryReq;
import com.practice.mall.model.vo.CategoryVO;

import java.util.List;

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listCategoryForCustomer();
}
