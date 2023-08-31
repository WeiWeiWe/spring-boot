package com.practice.mall.controller;

import com.practice.mall.common.ApiRestResponse;
import com.practice.mall.common.Constant;
import com.practice.mall.exception.MallExceptionEnum;
import com.practice.mall.model.pojo.User;
import com.practice.mall.model.request.AddCategoryReq;
import com.practice.mall.service.CategoryService;
import com.practice.mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class CategoryController {
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @ApiOperation("後台添加商品分類目錄")
    @PostMapping("admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session, @Valid @RequestBody AddCategoryReq addCategoryReq) {
        User currentUser = (User)session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }

        // 是否是管理員
        boolean adminRole = userService.checkAdminRole(currentUser);
        if (adminRole) {
            categoryService.add(addCategoryReq);
            return ApiRestResponse.success();
        } else {
            return ApiRestResponse.error(MallExceptionEnum.NEED_ADMIN);
        }
    }
}
