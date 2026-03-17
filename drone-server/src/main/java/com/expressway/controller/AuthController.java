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
        User user = userService.register(registerDTO);
        java.util.Map<String, Object> data = new java.util.HashMap<>();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("name", user.getName());
        return Result.success(data);
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
        User user = userService.selectById(userId);
        java.util.Map<String, Object> data = new java.util.HashMap<>();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("name", user.getName());
        data.put("role", user.getRoleName());
        data.put("avatar", user.getAvatar());
        data.put("email", user.getEmail());
        data.put("phone", user.getPhone());
        return Result.success(data);
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/user")
    public Result<?> updateUserInfo(@RequestBody java.util.Map<String, Object> userData) {
        // 从上下文获取用户ID
        Long userId = com.expressway.context.BaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("未登录");
        }
        User user = new User();
        user.setId(userId);
        if (userData.containsKey("name")) {
            user.setName((String) userData.get("name"));
        }
        if (userData.containsKey("email")) {
            user.setEmail((String) userData.get("email"));
        }
        if (userData.containsKey("phone")) {
            user.setPhone((String) userData.get("phone"));
        }
        if (userData.containsKey("avatar")) {
            user.setAvatar((String) userData.get("avatar"));
        }
        // 更新用户信息
        userService.update(user);
        // 返回更新后的用户信息
        User updatedUser = userService.selectById(userId);
        java.util.Map<String, Object> data = new java.util.HashMap<>();
        data.put("id", updatedUser.getId());
        data.put("username", updatedUser.getUsername());
        data.put("name", updatedUser.getName());
        data.put("email", updatedUser.getEmail());
        data.put("phone", updatedUser.getPhone());
        data.put("avatar", updatedUser.getAvatar());
        return Result.success(data);
    }
}