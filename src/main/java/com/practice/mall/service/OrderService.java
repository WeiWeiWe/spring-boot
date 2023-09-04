package com.practice.mall.service;

import com.practice.mall.model.request.CreateOrderReq;
import com.practice.mall.model.vo.OrderVO;

public interface OrderService {

    String create(CreateOrderReq createOrderReq);

    OrderVO detail(String orderNo);
}
