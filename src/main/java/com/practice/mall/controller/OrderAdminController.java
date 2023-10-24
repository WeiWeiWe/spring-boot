package com.practice.mall.controller;

import com.github.pagehelper.PageInfo;
import com.practice.mall.common.ApiRestResponse;
import com.practice.mall.model.vo.OrderStatisticsVO;
import com.practice.mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class OrderAdminController {

    @Autowired
    OrderService orderService;

    @ApiOperation("管理員訂單列表")
    @GetMapping("admin/order/list")
    public ApiRestResponse listForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = orderService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("管理員發貨")
    @PostMapping("admin/order/delivered")
    public ApiRestResponse delivered(@RequestParam String orderNo) {
        /**
         * 訂單狀態流程 => 0: 用戶已取消 ; 10: 未付款 ; 20: 已付款 ; 30: 已發貨 ; 40: 交易完成
         */
        orderService.deliver(orderNo);
        return ApiRestResponse.success();
    }

    @ApiOperation("完結訂單(普通用戶和管理員都能使用)")
    @PostMapping("order/finish")
    public ApiRestResponse finish(@RequestParam String orderNo) {
        /**
         * 訂單狀態流程 => 0: 用戶已取消 ; 10: 未付款 ; 20: 已付款 ; 30: 已發貨 ; 40: 交易完成
         */
        orderService.finish(orderNo);
        return ApiRestResponse.success();
    }

    @ApiOperation("每日訂單量統計")
    @GetMapping("admin/order/statistics")
    public ApiRestResponse statistics(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<OrderStatisticsVO> statistics = orderService.statistics(startDate, endDate);
        return ApiRestResponse.success(statistics);
    }
}
