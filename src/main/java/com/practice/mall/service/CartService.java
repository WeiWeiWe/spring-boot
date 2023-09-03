package com.practice.mall.service;

import com.practice.mall.model.vo.CartVO;

import java.util.List;

public interface CartService {

    List<CartVO> list(Integer userId);

    List<CartVO> add(Integer userId, Integer productId, Integer count);
}
