package com.xs.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
/**
 * 套餐菜品关系(SetmealDish)表实体类
 *
 * @author makejava
 * @since 2022-08-11 07:21:09
 */
@Data
public class SetmealDish implements Serializable {
    //主键
    private Long id;
    //套餐id 
    private Long setmealId;
    //菜品id
    private Long dishId;
    //菜品名称 （冗余字段）
    private String name;
    //菜品原价（冗余字段）
    private Double price;
    //份数
    private Integer copies;
    //排序
    private Integer sort;
    @TableField(fill = FieldFill.INSERT)
    //创建时间
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    //更新时间
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    //创建人
    private Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    //修改人
    private Long updateUser;
    //是否删除
    private Integer isDeleted;

}

