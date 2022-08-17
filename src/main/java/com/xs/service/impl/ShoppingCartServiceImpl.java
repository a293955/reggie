package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.BaseContext;
import com.xs.common.R;
import com.xs.dao.ShoppingCartDao;
import com.xs.entity.ShoppingCart;
import com.xs.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 购物车(ShoppingCart)表服务实现类
 *
 * @author makejava
 * @since 2022-08-11 07:21:22
 */
@Service("shoppingCartService")
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartDao, ShoppingCart> implements ShoppingCartService {

    @Resource
    private ShoppingCartDao shoppingCartDao;

    @Override
    public R<List<ShoppingCart>> getList(ShoppingCart shoppingCart) {
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        lqw.orderByDesc(ShoppingCart::getCreateTime);

        List<ShoppingCart> shoppingCarts = shoppingCartDao.selectList(lqw);

        return R.success(shoppingCarts);
    }

    @Override
    public R<ShoppingCart> addShoppingCart(ShoppingCart shoppingCart) {
        //设置用户id,指定哪个用户的购物车数据
        shoppingCart.setUserId(BaseContext.getCurrentId());
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        //查询当前菜品或套餐是否在购物车中
        if (Objects.nonNull(shoppingCart.getDishId())) {
            //添加到购物车是菜品
            lqw.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        }else {
            //添加到购物车是套餐
            lqw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        //如果已经存在，在原有数量上加1
        ShoppingCart cart = shoppingCartDao.selectOne(lqw);
        if (Objects.nonNull(cart)) {
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartDao.updateById(cart);
        }else {
            //不存在，默认数量为1
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartDao.insert(shoppingCart);
            cart = shoppingCart;
        }
        
        return R.success(cart);
    }

    @Override
    public R<String> clean() {
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());

        shoppingCartDao.delete(lqw);
        return R.success("清空购物车成功");
    }

    @Override
    public R<ShoppingCart> subShoppingCart(ShoppingCart shoppingCart) {
        //设置用户id,指定哪个用户的购物车数据
        shoppingCart.setUserId(BaseContext.getCurrentId());
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        //查询当前菜品或套餐是否在购物车中
        if (Objects.nonNull(shoppingCart.getDishId())) {
            //添加到购物车是菜品
            lqw.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        }else {
            //添加到购物车是套餐
            lqw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        //如果已经存在，在原有数量上加1
        ShoppingCart cart = shoppingCartDao.selectOne(lqw);
        if (Objects.nonNull(cart)) {
            cart.setNumber(cart.getNumber() - 1);
            shoppingCartDao.updateById(cart);
        }

        return R.success(cart);
    }
}

