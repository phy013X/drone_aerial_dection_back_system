package com.expressway.controller;

import com.expressway.dto.UserLoginDTO;
import com.expressway.dto.UserRegisterDTO;
import com.expressway.entity.User;
import com.expressway.result.Result;
import com.expressway.service.UserService;
import com.expressway.vo.UserLoginVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 认证相关控制器
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@Validated @RequestBody UserLoginDTO loginDTO) {
        log.info("用户登录：{}", loginDTO);
        UserLoginVO loginResponseVO = userService.login(loginDTO);
        return Result.success(loginResponseVO);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<?> register(@Validated @RequestBody UserRegisterDTO registerDTO) {
        log.info("用户注册：{}", registerDTO);
        userService.register(registerDTO);
        return Result.success("注册成功");
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/user")
    public Result<?> getUserInfo() {
        // 从上下文获取用户ID
        Long userId = com.expressway.context.BaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("未登录");
        }
        return Result.success(userService.selectById(userId));
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/user")
    public Result<?> updateUserInfo(@RequestBody User user) {
        // 从上下文获取用户ID
        Long userId = com.expressway.context.BaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("未登录");
        }
        user.setId(userId);
        // 更新用户信息
        userService.update(user);
        return Result.success("更新成功");
    }
}