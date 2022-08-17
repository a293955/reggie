package com.xs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xs.common.R;
import com.xs.dto.OrdersDto;
import com.xs.entity.Orders;
import com.xs.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {

    @Resource
    private OrdersService ordersService;

    /**
     * 再来一单
     */
    @PostMapping("/again")
    public R<String> again(@RequestBody Orders orders) {
        return ordersService.again(orders);
    }
    /**
     * 更新订单状态
     */
    @PutMapping
    public R<Orders> updateStatus(@RequestBody Orders orders) {
        return ordersService.updateStatus(orders);
    }

    /**
     * 用户下单
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        return ordersService.submit(orders);
    }

    /**
     * 订单信息按分页、订单号、创建时间查询
     */
    @GetMapping("/page")
    public R<Page<Orders>> getOrdersByPage(Integer page, Integer pageSize, Long number, LocalDateTime beginTime, LocalDateTime endTime) {
        return ordersService.getOrdersByPage(page, pageSize, number, beginTime, endTime);
    }

    /**
     * 查询用户个人和订单信息
     */
    @GetMapping("/userPage")
    public R<Page<OrdersDto>> getUserPage(Integer page, Integer pageSize) {
        return ordersService.getUserPage(page, pageSize);
    }

}
