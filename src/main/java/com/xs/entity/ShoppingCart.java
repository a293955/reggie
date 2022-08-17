package com.xs.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
/**
 * 购物车(ShoppingCart)表实体类
 *
 * @author makejava
 * @since 2022-08-11 07:21:22
 */
@Data
public class ShoppingCart implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;
    //名称
    private String name;
    //图片
    private String image;
    //主键
    private Long userId;
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
    //创建时间
    private LocalDateTime createTime;

}

