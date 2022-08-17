package com.xs.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
/**
 * 菜品管理(Dish)表实体类
 *
 * @author makejava
 * @since 2022-08-11 07:19:19
 */
@Data
public class Dish implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;
    //菜品名称
    private String name;
    //菜品分类id
    private Long categoryId;
    //菜品价格
    private Double price;
    //商品码
    private String code;
    //图片
    private String image;
    //描述信息
    private String description;
    //0 停售 1 起售
    private Integer status;
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
    //是否删除
    private Integer isDeleted;

}

