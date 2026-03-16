package com.expressway.service;

import com.expressway.entity.Role;
import com.expressway.result.PageResult;

import java.util.List;

/**
 * 角色服务
 */
public interface RoleService {
    /**
     * 获取角色列表
     * @param page 页码
     * @param pageSize 每页数量
     * @return 角色列表
     */
    PageResult<Role> getRoleList(int page, int pageSize);

    /**
     * 获取角色详情
     * @param roleId 角色ID
     * @return 角色详情
     */
    Role getRoleById(Long roleId);

    /**
     * 创建角色
     * @param role 角色信息
     * @return 创建的角色
     */
    Role createRole(Role role);

    /**
     * 更新角色
     * @param role 角色信息
     * @return 更新后的角色
     */
    Role updateRole(Role role);

    /**
     * 删除角色
     * @param roleId 角色ID
     */
    void deleteRole(Long roleId);

    /**
     * 获取角色的权限列表
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Long> getRolePermissions(Long roleId);

    /**
     * 为角色分配权限
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     */
    void assignPermissions(Long roleId, List<Long> permissionIds);
}