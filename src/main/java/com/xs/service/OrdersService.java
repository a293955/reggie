package com.xs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.common.R;
import com.xs.dto.OrdersDto;
import com.xs.entity.Orders;

import java.time.LocalDateTime;

/**
 * 订单表(Orders)表服务接口
 *
 * @author makejava
 * @since 2022-08-11 07:20:42
 */
public interface OrdersService extends IService<Orders> {

    R<Page<Orders>> getOrdersByPage(Integer page, Integer pageSize, Long number, LocalDateTime beginTime, LocalDateTime endTime);

    R<Page<OrdersDto>> getUserPage(Integer page, Integer pageSize);

    R<String> submit(Orders orders);

    R<Orders> updateStatus(Orders orders);

    R<String> again(Orders orders);
}

