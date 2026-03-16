package com.expressway.service;

import com.expressway.entity.Permission;
import com.expressway.result.PageResult;

import java.util.List;

/**
 * 权限服务
 */
public interface PermissionService {
    /**
     * 获取权限列表
     * @param page 页码
     * @param pageSize 每页数量
     * @return 权限列表
     */
    PageResult<Permission> getPermissionList(int page, int pageSize);

    /**
     * 获取权限详情
     * @param permissionId 权限ID
     * @return 权限详情
     */
    Permission getPermissionById(Long permissionId);

    /**
     * 创建权限
     * @param permission 权限信息
     * @return 创建的权限
     */
    Permission createPermission(Permission permission);

    /**
     * 更新权限
     * @param permission 权限信息
     * @return 更新后的权限
     */
    Permission updatePermission(Permission permission);

    /**
     * 删除权限
     * @param permissionId 权限ID
     */
    void deletePermission(Long permissionId);

    /**
     * 获取权限树
     * @return 权限树
     */
    List<Permission> getPermissionTree();

    /**
     * 获取用户的权限列表
     * @param userId 用户ID
     * @return 权限列表
     */
    List<Permission> getUserPermissions(Long userId);

    /**
     * 检查用户是否有权限
     * @param userId 用户ID
     * @param permissionCode 权限编码
     * @return 是否有权限
     */
    boolean checkPermission(Long userId, String permissionCode);
}