package com.practice.mall.service.impl;

import com.practice.mall.common.Constant;
import com.practice.mall.exception.MallException;
import com.practice.mall.exception.MallExceptionEnum;
import com.practice.mall.filter.UserFilter;
import com.practice.mall.model.dao.CartMapper;
import com.practice.mall.model.dao.OrderItemMapper;
import com.practice.mall.model.dao.OrderMapper;
import com.practice.mall.model.dao.ProductMapper;
import com.practice.mall.model.pojo.Order;
import com.practice.mall.model.pojo.OrderItem;
import com.practice.mall.model.pojo.Product;
import com.practice.mall.model.request.CreateOrderReq;
import com.practice.mall.model.vo.CartVO;
import com.practice.mall.model.vo.OrderItemVO;
import com.practice.mall.model.vo.OrderVO;
import com.practice.mall.service.CartService;
import com.practice.mall.service.OrderService;
import com.practice.mall.util.OrderCodeFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    CartService cartService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String create(CreateOrderReq createOrderReq) {
        // 拿到用戶ID
        Integer userId = UserFilter.currentUser.getId();

        // 從購物車查找已勾選的商品
        List<CartVO> cartVOList = cartService.list(userId);
        List<CartVO> cartVOListTemp = new ArrayList<>();
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO =  cartVOList.get(i);
            if (cartVO.getSelected().equals(Constant.Cart.CHECKED)) {
                cartVOListTemp.add(cartVO);
            }
        }
        cartVOList = cartVOListTemp;
        // 如果購物車已勾選的商品為空，需要返回錯誤
        if (CollectionUtils.isEmpty(cartVOList)) {
            throw new MallException(MallExceptionEnum.CART_EMPTY);
        }

        // 判斷商品是否存在、上下架狀態、庫存
        validSaleStatusAndStock(cartVOList);
        // 把購物車對象轉成訂單item對象
        List<OrderItem> orderItemList = cartVOListToOrderItemList(cartVOList);
        // 扣庫存
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem =  orderItemList.get(i);
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            int newStock = product.getStock() - orderItem.getQuantity();
            if (newStock < 0) {
                // 可能有商品已被搶先購買的情況
                throw new MallException(MallExceptionEnum.NOT_ENOUGH);
            }
            product.setStock(newStock);
            productMapper.updateByPrimaryKeySelective(product);
        }

        // 把購物車中的已勾選商品刪除
        cleanCart(cartVOList);
        // 生成訂單
        Order order = new Order();
        // 生成訂單號，有獨立的規則
        String orderNo = OrderCodeFactory.getOrderCode(Long.valueOf(userId));
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setTotalPrice(totalPrice(orderItemList));
        order.setReceiverName(createOrderReq.getReceiverName());
        order.setReceiverMobile(createOrderReq.getReceiverMobile());
        order.setReceiverAddress(createOrderReq.getReceiverAddress());
        order.setOrderStatus(Constant.OrderStatusEnum.NOT_PAID.getCode());
        order.setPostage(0);
        order.setPaymentType(1);

        // 插入到 Order 表
        orderMapper.insertSelective(order);

        // 循環保存每個商品到 order_item 表
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            orderItem.setOrderNo(order.getOrderNo());
            orderItemMapper.insertSelective(orderItem);
        }

        // 把結果返回
        return orderNo;
    }

    private Integer totalPrice(List<OrderItem> orderItemList) {
        Integer totalPrice = 0;
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem =  orderItemList.get(i);
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    private void cleanCart(List<CartVO> cartVOList) {
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO = cartVOList.get(i);
            cartMapper.deleteByPrimaryKey(cartVO.getId());
        }
    }

    private List<OrderItem> cartVOListToOrderItemList(List<CartVO> cartVOList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO =  cartVOList.get(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartVO.getProductId());
            // 記錄商品快照訊息
            orderItem.setProductName(cartVO.getProductName());
            orderItem.setProductImg(cartVO.getProductImage());
            orderItem.setUnitPrice(cartVO.getPrice());
            orderItem.setQuantity(cartVO.getQuantity());
            orderItem.setTotalPrice(cartVO.getTotalPrice());
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    private void validSaleStatusAndStock(List<CartVO> cartVOList) {
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO =  cartVOList.get(i);

            Product product = productMapper.selectByPrimaryKey(cartVO.getProductId());

            // 判斷商品是否存在，或是否下架
            if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
                throw new MallException(MallExceptionEnum.NOT_SALE);
            }

            // 判斷商品庫存
            if (cartVO.getQuantity() > product.getStock()) {
                throw new MallException(MallExceptionEnum.NOT_ENOUGH);
            }
        }
    }

    @Override
    public OrderVO detail(String orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);

        // 判斷訂單是否存在
        if (order == null) {
            throw new MallException(MallExceptionEnum.NO_ORDER);
        }

        // 判斷訂單是否屬於此用戶
        Integer userId = UserFilter.currentUser.getId();
        if (!order.getUserId().equals(userId)) {
            throw new MallException(MallExceptionEnum.NOT_YOUR_ORDER);
        }

        OrderVO orderVO = getOrderVO(order);
        return orderVO;
    }

    private OrderVO getOrderVO(Order order) {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);

        // 獲取訂單對應的 orderItemVOList
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(order.getOrderNo());
        List<OrderItemVO> orderItemVOList = new ArrayList<>();
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem =  orderItemList.get(i);
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(orderItem, orderItemVO);
            orderItemVOList.add(orderItemVO);
        }
        orderVO.setOrderItemVOList(orderItemVOList);
        orderVO.setOrderStatusName(Constant.OrderStatusEnum.codeOf(orderVO.getOrderStatus()).getValue());
        return orderVO;
    }
}
