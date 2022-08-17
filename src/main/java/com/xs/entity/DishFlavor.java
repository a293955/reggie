package com.xs.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
/**
 * 菜品口味关系表(DishFlavor)表实体类
 *
 * @author makejava
 * @since 2022-08-11 07:19:37
 */
@Data
public class DishFlavor implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;
    //菜品
    private Long dishId;
    //口味名称
    private String name;
    //口味数据list
    private String value;
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

