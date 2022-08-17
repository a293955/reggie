package com.xs.controller;

import com.xs.common.R;
import com.xs.entity.ShoppingCart;
import com.xs.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;

    /**
     * 清空购物车
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        return shoppingCartService.clean();
    }

    /**
     * 查看购物车
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> getList(ShoppingCart shoppingCart) {
        return shoppingCartService.getList(shoppingCart);
    }

    @PostMapping("/add")
    public R<ShoppingCart> addShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.addShoppingCart(shoppingCart);
    }

    @PostMapping("/sub")
    public R<ShoppingCart> subShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.subShoppingCart(shoppingCart);
    }
}
