package com.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.common.R;
import com.xs.entity.ShoppingCart;

import java.util.List;

/**
 * 购物车(ShoppingCart)表服务接口
 *
 * @author makejava
 * @since 2022-08-11 07:21:22
 */
public interface ShoppingCartService extends IService<ShoppingCart> {

    R<List<ShoppingCart>> getList(ShoppingCart shoppingCart);

    R<ShoppingCart> addShoppingCart(ShoppingCart shoppingCart);

    R<String> clean();

    R<ShoppingCart> subShoppingCart(ShoppingCart shoppingCart);
}

