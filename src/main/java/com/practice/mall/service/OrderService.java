package com.practice.mall.service;

import com.practice.mall.model.request.CreateOrderReq;

public interface OrderService {

    String create(CreateOrderReq createOrderReq);
}
