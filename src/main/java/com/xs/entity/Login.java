package com.xs.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Login implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //手机号
    private String phone;
    //验证码
    private String code;
}
