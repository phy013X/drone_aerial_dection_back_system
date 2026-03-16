package com.expressway.service.impl;

import com.expressway.entity.Role;
import com.expressway.result.PageResult;
import com.expressway.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 角色服务实现
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    // 角色缓存
    private final Map<Long, Role> roles = new ConcurrentHashMap<>();
    private long roleNextId = 1;
    
    // 角色权限映射
    private final Map<Long, List<Long>> rolePermissions = new ConcurrentHashMap<>();

    // 初始化默认角色
    public RoleServiceImpl() {
        Role adminRole = new Role();
        adminRole.setId(1L);
        adminRole.setName("超级管理员");
        adminRole.setCode("admin");
        adminRole.setDescription("拥有系统所有权限");
        adminRole.setCreateTime(new Date());
        adminRole.setUpdateTime(new Date());
        roles.put(1L, adminRole);
        
        Role operatorRole = new Role();
        operatorRole.setId(2L);
        operatorRole.setName("操作员");
        operatorRole.setCode("operator");
        operatorRole.setDescription("拥有设备操作和检测权限");
        operatorRole.setCreateTime(new Date());
        operatorRole.setUpdateTime(new Date());
        roles.put(2L, operatorRole);
        
        Role userRole = new Role();
        userRole.setId(3L);
        userRole.setName("普通用户");
        userRole.setCode("user");
        userRole.setDescription("拥有基本查看权限");
        userRole.setCreateTime(new Date());
        userRole.setUpdateTime(new Date());
        roles.put(3L, userRole);
        
        roleNextId = 4;
        
        // 初始化角色权限
        rolePermissions.put(1L, List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L));
        rolePermissions.put(2L, List.of(1L, 2L, 3L, 4L, 5L));
        rolePermissions.put(3L, List.of(1L, 2L, 3L));
    }

    @Override
    public PageResult<Role> getRoleList(int page, int pageSize) {
        List<Role> items = new ArrayList<>(roles.values());
        int total = items.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<Role> pageItems = items.subList(start, end);
        return new PageResult<>(total, pageItems);
    }

    @Override
    public Role getRoleById(Long roleId) {
        return roles.get(roleId);
    }

    @Override
    public Role createRole(Role role) {
        long roleId = roleNextId++;
        role.setId(roleId);
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        roles.put(roleId, role);
        log.info("创建角色：roleId={}, roleName={}, roleCode={}", roleId, role.getName(), role.getCode());
        return role;
    }

    @Override
    public Role updateRole(Role role) {
        Role existingRole = roles.get(role.getId());
        if (existingRole != null) {
            existingRole.setName(role.getName());
            existingRole.setCode(role.getCode());
            existingRole.setDescription(role.getDescription());
            existingRole.setUpdateTime(new Date());
            log.info("更新角色：roleId={}, roleName={}, roleCode={}", role.getId(), role.getName(), role.getCode());
        }
        return existingRole;
    }

    @Override
    public void deleteRole(Long roleId) {
        roles.remove(roleId);
        rolePermissions.remove(roleId);
        log.info("删除角色：roleId={}", roleId);
    }

    @Override
    public List<Long> getRolePermissions(Long roleId) {
        return rolePermissions.getOrDefault(roleId, new ArrayList<>());
    }

    @Override
    public void assignPermissions(Long roleId, List<Long> permissionIds) {
        rolePermissions.put(roleId, permissionIds);
        log.info("为角色分配权限：roleId={}, permissionIds={}", roleId, permissionIds);
    }
}