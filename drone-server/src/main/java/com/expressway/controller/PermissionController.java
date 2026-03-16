package com.expressway.controller;

import com.expressway.entity.Permission;
import com.expressway.result.Result;
import com.expressway.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 权限管理控制器
 */
@RestController
@RequestMapping("/permissions")
@Tag(name = "权限管理", description = "权限管理相关接口")
@Slf4j
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 获取权限列表
     */
    @GetMapping
    @Operation(summary = "获取权限列表", description = "获取权限列表")
    public Result<?> getPermissionList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取权限列表：page={}, pageSize={}", page, pageSize);
        return Result.success(permissionService.getPermissionList(page, pageSize));
    }

    /**
     * 获取权限详情
     */
    @GetMapping("/{permissionId}")
    @Operation(summary = "获取权限详情", description = "获取权限详情")
    public Result<?> getPermissionById(@PathVariable Long permissionId) {
        log.info("获取权限详情：permissionId={}", permissionId);
        return Result.success(permissionService.getPermissionById(permissionId));
    }

    /**
     * 创建权限
     */
    @PostMapping
    @Operation(summary = "创建权限", description = "创建权限")
    public Result<?> createPermission(@RequestBody Permission permission) {
        log.info("创建权限：{}", permission);
        return Result.success(permissionService.createPermission(permission));
    }

    /**
     * 更新权限
     */
    @PutMapping("/{permissionId}")
    @Operation(summary = "更新权限", description = "更新权限")
    public Result<?> updatePermission(@PathVariable Long permissionId, @RequestBody Permission permission) {
        permission.setId(permissionId);
        log.info("更新权限：{}", permission);
        return Result.success(permissionService.updatePermission(permission));
    }

    /**
     * 删除权限
     */
    @DeleteMapping("/{permissionId}")
    @Operation(summary = "删除权限", description = "删除权限")
    public Result<?> deletePermission(@PathVariable Long permissionId) {
        log.info("删除权限：permissionId={}", permissionId);
        permissionService.deletePermission(permissionId);
        return Result.success("删除成功");
    }

    /**
     * 获取权限树
     */
    @GetMapping("/tree")
    @Operation(summary = "获取权限树", description = "获取权限树")
    public Result<?> getPermissionTree() {
        log.info("获取权限树");
        return Result.success(permissionService.getPermissionTree());
    }

    /**
     * 获取用户的权限列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户的权限列表", description = "获取用户的权限列表")
    public Result<?> getUserPermissions(@PathVariable Long userId) {
        log.info("获取用户的权限列表：userId={}", userId);
        return Result.success(permissionService.getUserPermissions(userId));
    }

    /**
     * 检查用户是否有权限
     */
    @GetMapping("/check")
    @Operation(summary = "检查用户是否有权限", description = "检查用户是否有权限")
    public Result<?> checkPermission(
            @RequestParam Long userId,
            @RequestParam String permissionCode) {
        log.info("检查用户是否有权限：userId={}, permissionCode={}", userId, permissionCode);
        boolean hasPermission = permissionService.checkPermission(userId, permissionCode);
        return Result.success(hasPermission);
    }
}