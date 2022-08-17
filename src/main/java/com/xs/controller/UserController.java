package com.xs.controller;

import com.xs.common.R;
import com.xs.entity.Login;
import com.xs.entity.User;
import com.xs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 发送手机短信验证码
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        return userService.sendMsg(user, session);
    }

    /**
     * 移动端用户登录
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Login login, HttpSession session){
        return userService.login(login, session);
    }

    /**
     * 移动端用户登出
     */
    @PostMapping("/loginout")
    public R<String> loginout(HttpSession session) {
        return userService.loginout(session);
    }
}
