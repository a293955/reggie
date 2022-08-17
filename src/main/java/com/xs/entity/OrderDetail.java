package com.xs.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 订单明细表(OrderDetail)表实体类
 *
 * @author makejava
 * @since 2022-08-11 07:20:29
 */
@Data
public class OrderDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;
    //名字
    private String name;
    //图片
    private String image;
    //订单id
    private Long orderId;
    //菜品id
    private Long dishId;
    //套餐id
    private Long setmealId;
    //口味
    private String dishFlavor;
    //数量
    private Integer number;
    //金额
    private Double amount;

}

