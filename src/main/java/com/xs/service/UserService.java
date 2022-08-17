package com.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.common.R;
import com.xs.entity.Login;
import com.xs.entity.User;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 用户信息(User)表服务接口
 *
 * @author makejava
 * @since 2022-08-11 07:21:36
 */
public interface UserService extends IService<User> {

    R<String> sendMsg(User user, HttpSession session);

    R<User> login(Login login, HttpSession session);

    R<String> loginout(HttpSession session);
}

