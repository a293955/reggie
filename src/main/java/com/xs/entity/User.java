package com.xs.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息(User)表实体类
 *
 * @author makejava
 * @since 2022-08-11 07:21:36
 */
@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;
    //姓名
    private String name;
    //手机号
    private String phone;
    //性别
    private String sex;
    //身份证号
    private String idNumber;
    //头像
    private String avatar;
    //状态 0:禁用，1:正常
    private Integer status;

}

