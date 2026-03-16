package com.expressway.controller;

import com.expressway.context.BaseContext;
import com.expressway.entity.User;
import com.expressway.result.PageResult;
import com.expressway.result.Result;
import com.expressway.service.UserService;
import com.expressway.utils.AliOssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户相关控制器
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户资料、密码、登录历史等接口")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private AliOssUtil aliOssUtil;

    /**
     * 获取用户资料
     */
    @GetMapping("/profile")
    @Operation(summary = "获取用户资料", description = "获取当前用户资料")
    public Result<?> getUserProfile() {
        log.info("获取用户资料");
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("未登录");
        }
        User user = userService.selectById(userId);
        Map<String, Object> profile = new HashMap<>();
        profile.put("id", user.getId());
        profile.put("username", user.getUsername());
        profile.put("name", user.getName());
        profile.put("email", user.getEmail());
        profile.put("phone", user.getPhone());
        profile.put("avatar", user.getAvatar());
        profile.put("roleId", user.getRoleId());
        profile.put("status", user.getStatus());
        profile.put("createTime", user.getCreateTime());
        profile.put("lastLoginTime", user.getLastLoginTime());
        return Result.success(profile);
    }

    /**
     * 更新用户资料
     */
    @PutMapping("/profile")
    @Operation(summary = "更新用户资料", description = "更新当前用户资料")
    public Result<?> updateUserProfile(@RequestBody Map<String, Object> userProfile) {
        log.info("更新用户资料：{}", userProfile);
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("未登录");
        }
        User user = new User();
        user.setId(userId);
        if (userProfile.containsKey("name")) {
            user.setName((String) userProfile.get("name"));
        }
        if (userProfile.containsKey("email")) {
            user.setEmail((String) userProfile.get("email"));
        }
        if (userProfile.containsKey("phone")) {
            user.setPhone((String) userProfile.get("phone"));
        }
        User updatedUser = userService.update(user);
        return Result.success("更新成功");
    }

    /**
     * 更新头像
     */
    @PostMapping("/avatar")
    @Operation(summary = "更新头像", description = "更新用户头像")
    public Result<?> updateAvatar(@RequestParam("file") MultipartFile file) {
        log.info("更新头像：{}", file.getOriginalFilename());
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("未登录");
        }
        try {
            String avatarUrl = aliOssUtil.upload(file.getBytes(), file.getOriginalFilename());
            userService.updateAvatar(userId, avatarUrl);
            Map<String, String> result = new HashMap<>();
            result.put("avatar", avatarUrl);
            return Result.success(result);
        } catch (Exception e) {
            log.error("上传头像失败", e);
            return Result.error("上传头像失败");
        }
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    @Operation(summary = "修改密码", description = "修改用户密码")
    public Result<?> changePassword(@RequestBody Map<String, String> passwordData) {
        log.info("修改密码");
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("未登录");
        }
        String oldPassword = passwordData.get("oldPassword");
        String newPassword = passwordData.get("newPassword");
        if (oldPassword == null || newPassword == null) {
            return Result.error("密码不能为空");
        }
        try {
            userService.updatePassword(userId, oldPassword, newPassword);
            return Result.success("修改成功");
        } catch (Exception e) {
            log.error("修改密码失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 绑定邮箱
     */
    @PostMapping("/email")
    @Operation(summary = "绑定邮箱", description = "绑定用户邮箱")
    public Result<?> bindEmail(@RequestBody Map<String, String> emailData) {
        log.info("绑定邮箱");
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("未登录");
        }
        String email = emailData.get("email");
        if (email == null) {
            return Result.error("邮箱不能为空");
        }
        try {
            userService.updateEmail(userId, email);
            return Result.success("绑定成功");
        } catch (Exception e) {
            log.error("绑定邮箱失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 绑定手机
     */
    @PostMapping("/phone")
    @Operation(summary = "绑定手机", description = "绑定用户手机")
    public Result<?> bindPhone(@RequestBody Map<String, String> phoneData) {
        log.info("绑定手机");
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("未登录");
        }
        String phone = phoneData.get("phone");
        if (phone == null) {
            return Result.error("手机号不能为空");
        }
        try {
            userService.updatePhone(userId, phone);
            return Result.success("绑定成功");
        } catch (Exception e) {
            log.error("绑定手机失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 切换双因素认证
     */
    @PutMapping("/two-factor")
    @Operation(summary = "切换双因素认证", description = "切换用户双因素认证状态")
    public Result<?> toggleTwoFactor(@RequestBody Map<String, Object> twoFactorData) {
        log.info("切换双因素认证");
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("未登录");
        }
        return Result.success("切换成功");
    }

    /**
     * 获取登录历史
     */
    @GetMapping("/login-history")
    @Operation(summary = "获取登录历史", description = "获取用户登录历史记录")
    public Result<?> getLoginHistory(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取登录历史：page={}, pageSize={}", page, pageSize);
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("未登录");
        }
        PageResult<?> loginHistory = userService.getLoginHistory(userId, page, pageSize);
        return Result.success(loginHistory);
    }

    /**
     * 获取操作日志
     */
    @GetMapping("/operation-logs")
    @Operation(summary = "获取操作日志", description = "获取用户操作日志记录")
    public Result<?> getOperationLogs(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取操作日志：page={}, pageSize={}", page, pageSize);
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("未登录");
        }
        PageResult<?> operationLogs = userService.getOperationLogs(userId, page, pageSize);
        return Result.success(operationLogs);
    }

    /**
     * 获取用户统计
     */
    @GetMapping("/stats")
    @Operation(summary = "获取用户统计", description = "获取用户统计数据")
    public Result<?> getUserStats() {
        log.info("获取用户统计");
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("未登录");
        }
        Object stats = userService.getUserStats(userId);
        return Result.success(stats);
    }
}