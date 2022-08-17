package com.xs.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
/**
 * 菜品及套餐分类(Category)表实体类
 *
 * @author makejava
 * @since 2022-08-11 07:19:02
 */
@Data
public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;
    //类型   1 菜品分类 2 套餐分类
    private Integer type;
    //分类名称
    private String name;
    //顺序
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

}

