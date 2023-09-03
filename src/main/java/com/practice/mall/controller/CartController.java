package com.practice.mall.controller;

import com.practice.mall.common.ApiRestResponse;
import com.practice.mall.filter.UserFilter;
import com.practice.mall.model.vo.CartVO;
import com.practice.mall.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @ApiOperation("購物車列表")
    @GetMapping("/list")
    public ApiRestResponse list() {
        // 內部獲取用戶ID，防止橫向越權
        List<CartVO> cartList = cartService.list(UserFilter.currentUser.getId());
        return ApiRestResponse.success(cartList);
    }

    @ApiOperation("添加商品到購物車")
    @PostMapping("/add")
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count) {
        List<CartVO> cartVOList = cartService.add(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(cartVOList);
    }

    @ApiOperation("更新購物車")
    @PostMapping("/update")
    public ApiRestResponse update(@RequestParam Integer productId, @RequestParam Integer count) {
        List<CartVO> cartVOList = cartService.update(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(cartVOList);
    }

    @ApiOperation("刪除購物車")
    @PostMapping("/delete")
    public ApiRestResponse delete(@RequestParam Integer productId) {
        // userId 與 cartId 需要從內部獲取，不要從外部傳入，避免被攻擊導致其他用戶購物車被刪除
        List<CartVO> cartVOList = cartService.delete(UserFilter.currentUser.getId(), productId);
        return ApiRestResponse.success(cartVOList);
    }
}
