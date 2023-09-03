package com.practice.mall.service.impl;

import com.practice.mall.common.Constant;
import com.practice.mall.exception.MallException;
import com.practice.mall.exception.MallExceptionEnum;
import com.practice.mall.model.dao.CartMapper;
import com.practice.mall.model.dao.ProductMapper;
import com.practice.mall.model.pojo.Cart;
import com.practice.mall.model.pojo.Product;
import com.practice.mall.model.vo.CartVO;
import com.practice.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;

    @Override
    public List<CartVO> list(Integer userId) {
        List<CartVO> cartVOS = cartMapper.selectList(userId);
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO =  cartVOS.get(i);
            cartVO.setTotalPrice(cartVO.getPrice() * cartVO.getQuantity());
        }
        return cartVOS;
    }

    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count) {
        validProduct(productId, count);

        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            // 商品先前就不存在購物車裡，需要新增
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.insertSelective(cart);
        } else {
            // 商品已在購物車，與當前數量相加即可
            count = cart.getQuantity() + count;
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> update(Integer userId, Integer productId, Integer count) {
        validProduct(productId, count);

        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            // 商品不存在於購物車裡，無法更新
            throw new MallException(MallExceptionEnum.UPDATE_FAILED);
        } else {
            // 商品已存在於購物車裡，需更新數量
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }

    @Override
    public List<CartVO> delete(Integer userId, Integer productId) {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            // 商品不存在於購物車裡，無法刪除
            throw new MallException(MallExceptionEnum.DELETE_FAILED);
        } else {
            // 商品已存在於購物車裡，則可以刪除
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return this.list(userId);
    }

    private void validProduct(Integer productId, Integer count) {
        Product product = productMapper.selectByPrimaryKey(productId);

        // 判斷商品是否存在，或是否下架
        if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
            throw new MallException(MallExceptionEnum.NOT_SALE);
        }

        // 判斷商品庫存
        if (count > product.getStock()) {
            throw new MallException(MallExceptionEnum.NOT_ENOUGH);
        }
    }
}
