package com.expressway.controller;

import com.expressway.entity.Role;
import com.expressway.result.Result;
import com.expressway.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理控制器
 */
@RestController
@RequestMapping("/roles")
@Tag(name = "角色管理", description = "角色管理相关接口")
@Slf4j
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 获取角色列表
     */
    @GetMapping
    @Operation(summary = "获取角色列表", description = "获取角色列表")
    public Result<?> getRoleList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取角色列表：page={}, pageSize={}", page, pageSize);
        return Result.success(roleService.getRoleList(page, pageSize));
    }

    /**
     * 获取角色详情
     */
    @GetMapping("/{roleId}")
    @Operation(summary = "获取角色详情", description = "获取角色详情")
    public Result<?> getRoleById(@PathVariable Long roleId) {
        log.info("获取角色详情：roleId={}", roleId);
        return Result.success(roleService.getRoleById(roleId));
    }

    /**
     * 创建角色
     */
    @PostMapping
    @Operation(summary = "创建角色", description = "创建角色")
    public Result<?> createRole(@RequestBody Role role) {
        log.info("创建角色：{}", role);
        return Result.success(roleService.createRole(role));
    }

    /**
     * 更新角色
     */
    @PutMapping("/{roleId}")
    @Operation(summary = "更新角色", description = "更新角色")
    public Result<?> updateRole(@PathVariable Long roleId, @RequestBody Role role) {
        role.setId(roleId);
        log.info("更新角色：{}", role);
        return Result.success(roleService.updateRole(role));
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{roleId}")
    @Operation(summary = "删除角色", description = "删除角色")
    public Result<?> deleteRole(@PathVariable Long roleId) {
        log.info("删除角色：roleId={}", roleId);
        roleService.deleteRole(roleId);
        return Result.success("删除成功");
    }

    /**
     * 获取角色的权限列表
     */
    @GetMapping("/{roleId}/permissions")
    @Operation(summary = "获取角色的权限列表", description = "获取角色的权限列表")
    public Result<?> getRolePermissions(@PathVariable Long roleId) {
        log.info("获取角色的权限列表：roleId={}", roleId);
        return Result.success(roleService.getRolePermissions(roleId));
    }

    /**
     * 为角色分配权限
     */
    @PutMapping("/{roleId}/permissions")
    @Operation(summary = "为角色分配权限", description = "为角色分配权限")
    public Result<?> assignPermissions(@PathVariable Long roleId, @RequestBody List<Long> permissionIds) {
        log.info("为角色分配权限：roleId={}, permissionIds={}", roleId, permissionIds);
        roleService.assignPermissions(roleId, permissionIds);
        return Result.success("分配成功");
    }
}