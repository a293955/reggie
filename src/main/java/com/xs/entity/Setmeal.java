package com.xs.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
/**
 * 套餐(Setmeal)表实体类
 *
 * @author makejava
 * @since 2022-08-11 07:20:56
 */
@Data
public class Setmeal implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;
    //菜品分类id
    private Long categoryId;
    //套餐名称
    private String name;
    //套餐价格
    private Double price;
    //状态 0:停用 1:启用
    private Integer status;
    //编码
    private String code;
    //描述信息
    private String description;
    //图片
    private String image;
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

