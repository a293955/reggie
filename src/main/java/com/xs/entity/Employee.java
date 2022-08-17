package com.xs.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
/**
 * 员工信息(Employee)表实体类
 *
 * @author makejava
 * @since 2022-08-11 07:20:15
 */
@Data
public class Employee implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;
    //姓名
    private String name;
    //用户名
    private String username;
    //密码
    private String password;
    //手机号
    private String phone;
    //性别
    private String sex;
    //身份证号
    private String idNumber;
    //状态 0:禁用，1:正常
    private Integer status;
    @TableField(fill = FieldFill.INSERT) //自动填充
    //创建时间
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE) //自动填充
    //更新时间
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT) //自动填充
    //创建人
    private Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE) //自动填充
    //修改人
    private Long updateUser;

}

