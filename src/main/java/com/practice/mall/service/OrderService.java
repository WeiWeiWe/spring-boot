package com.practice.mall.service;

import com.github.pagehelper.PageInfo;
import com.practice.mall.model.request.CreateOrderReq;
import com.practice.mall.model.vo.OrderVO;

public interface OrderService {

    String create(CreateOrderReq createOrderReq);

    OrderVO detail(String orderNo);

    PageInfo listForCustomer(Integer pageNum, Integer pageSize);

    void cancel(String orderNo);

    String qrcode(String orderNo);

    void pay(String orderNo);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);
}
