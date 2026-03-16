package com.expressway.service.impl;

import com.expressway.entity.Permission;
import com.expressway.result.PageResult;
import com.expressway.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 权限服务实现
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    // 权限缓存
    private final Map<Long, Permission> permissions = new ConcurrentHashMap<>();
    private long permissionNextId = 1;
    
    // 角色权限映射
    private final Map<Long, List<Long>> rolePermissions = new ConcurrentHashMap<>();

    // 初始化默认权限
    public PermissionServiceImpl() {
        // 系统管理
        Permission sysManage = new Permission();
        sysManage.setId(1L);
        sysManage.setName("系统管理");
        sysManage.setCode("sys:manage");
        sysManage.setType(1);
        sysManage.setParentId(0L);
        sysManage.setPath("/system");
        sysManage.setIcon("system");
        sysManage.setSort(1);
        sysManage.setCreateTime(new Date());
        sysManage.setUpdateTime(new Date());
        permissions.put(1L, sysManage);
        
        // 用户管理
        Permission userManage = new Permission();
        userManage.setId(2L);
        userManage.setName("用户管理");
        userManage.setCode("user:manage");
        userManage.setType(1);
        userManage.setParentId(1L);
        userManage.setPath("/system/users");
        userManage.setIcon("user");
        userManage.setSort(1);
        userManage.setCreateTime(new Date());
        userManage.setUpdateTime(new Date());
        permissions.put(2L, userManage);
        
        // 角色管理
        Permission roleManage = new Permission();
        roleManage.setId(3L);
        roleManage.setName("角色管理");
        roleManage.setCode("role:manage");
        roleManage.setType(1);
        roleManage.setParentId(1L);
        roleManage.setPath("/system/roles");
        roleManage.setIcon("role");
        roleManage.setSort(2);
        roleManage.setCreateTime(new Date());
        roleManage.setUpdateTime(new Date());
        permissions.put(3L, roleManage);
        
        // 权限管理
        Permission permManage = new Permission();
        permManage.setId(4L);
        permManage.setName("权限管理");
        permManage.setCode("permission:manage");
        permManage.setType(1);
        permManage.setParentId(1L);
        permManage.setPath("/system/permissions");
        permManage.setIcon("permission");
        permManage.setSort(3);
        permManage.setCreateTime(new Date());
        permManage.setUpdateTime(new Date());
        permissions.put(4L, permManage);
        
        // 设备管理
        Permission deviceManage = new Permission();
        deviceManage.setId(5L);
        deviceManage.setName("设备管理");
        deviceManage.setCode("device:manage");
        deviceManage.setType(1);
        deviceManage.setParentId(0L);
        deviceManage.setPath("/devices");
        deviceManage.setIcon("device");
        deviceManage.setSort(2);
        deviceManage.setCreateTime(new Date());
        deviceManage.setUpdateTime(new Date());
        permissions.put(5L, deviceManage);
        
        // 检测管理
        Permission detectionManage = new Permission();
        detectionManage.setId(6L);
        detectionManage.setName("检测管理");
        detectionManage.setCode("detection:manage");
        detectionManage.setType(1);
        detectionManage.setParentId(0L);
        detectionManage.setPath("/detection");
        detectionManage.setIcon("detection");
        detectionManage.setSort(3);
        detectionManage.setCreateTime(new Date());
        detectionManage.setUpdateTime(new Date());
        permissions.put(6L, detectionManage);
        
        // 视频管理
        Permission videoManage = new Permission();
        videoManage.setId(7L);
        videoManage.setName("视频管理");
        videoManage.setCode("video:manage");
        videoManage.setType(1);
        videoManage.setParentId(0L);
        videoManage.setPath("/video");
        videoManage.setIcon("video");
        videoManage.setSort(4);
        videoManage.setCreateTime(new Date());
        videoManage.setUpdateTime(new Date());
        permissions.put(7L, videoManage);
        
        // 数据分析
        Permission analysisManage = new Permission();
        analysisManage.setId(8L);
        analysisManage.setName("数据分析");
        analysisManage.setCode("analysis:manage");
        analysisManage.setType(1);
        analysisManage.setParentId(0L);
        analysisManage.setPath("/analysis");
        analysisManage.setIcon("analysis");
        analysisManage.setSort(5);
        analysisManage.setCreateTime(new Date());
        analysisManage.setUpdateTime(new Date());
        permissions.put(8L, analysisManage);
        
        // 系统监控
        Permission monitorManage = new Permission();
        monitorManage.setId(9L);
        monitorManage.setName("系统监控");
        monitorManage.setCode("monitor:manage");
        monitorManage.setType(1);
        monitorManage.setParentId(0L);
        monitorManage.setPath("/monitor");
        monitorManage.setIcon("monitor");
        monitorManage.setSort(6);
        monitorManage.setCreateTime(new Date());
        monitorManage.setUpdateTime(new Date());
        permissions.put(9L, monitorManage);
        
        // 告警管理
        Permission alertManage = new Permission();
        alertManage.setId(10L);
        alertManage.setName("告警管理");
        alertManage.setCode("alert:manage");
        alertManage.setType(1);
        alertManage.setParentId(0L);
        alertManage.setPath("/alerts");
        alertManage.setIcon("alert");
        alertManage.setSort(7);
        alertManage.setCreateTime(new Date());
        alertManage.setUpdateTime(new Date());
        permissions.put(10L, alertManage);
        
        permissionNextId = 11;
        
        // 初始化角色权限
        rolePermissions.put(1L, List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L));
        rolePermissions.put(2L, List.of(5L, 6L, 7L, 8L, 10L));
        rolePermissions.put(3L, List.of(5L, 6L, 7L));
    }

    @Override
    public PageResult<Permission> getPermissionList(int page, int pageSize) {
        List<Permission> items = new ArrayList<>(permissions.values());
        int total = items.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<Permission> pageItems = items.subList(start, end);
        return new PageResult<>(total, pageItems);
    }

    @Override
    public Permission getPermissionById(Long permissionId) {
        return permissions.get(permissionId);
    }

    @Override
    public Permission createPermission(Permission permission) {
        long permissionId = permissionNextId++;
        permission.setId(permissionId);
        permission.setCreateTime(new Date());
        permission.setUpdateTime(new Date());
        permissions.put(permissionId, permission);
        log.info("创建权限：permissionId={}, permissionName={}, permissionCode={}", permissionId, permission.getName(), permission.getCode());
        return permission;
    }

    @Override
    public Permission updatePermission(Permission permission) {
        Permission existingPermission = permissions.get(permission.getId());
        if (existingPermission != null) {
            existingPermission.setName(permission.getName());
            existingPermission.setCode(permission.getCode());
            existingPermission.setType(permission.getType());
            existingPermission.setParentId(permission.getParentId());
            existingPermission.setPath(permission.getPath());
            existingPermission.setIcon(permission.getIcon());
            existingPermission.setSort(permission.getSort());
            existingPermission.setUpdateTime(new Date());
            log.info("更新权限：permissionId={}, permissionName={}, permissionCode={}", permission.getId(), permission.getName(), permission.getCode());
        }
        return existingPermission;
    }

    @Override
    public void deletePermission(Long permissionId) {
        permissions.remove(permissionId);
        log.info("删除权限：permissionId={}", permissionId);
    }

    @Override
    public List<Permission> getPermissionTree() {
        List<Permission> rootPermissions = new ArrayList<>();
        for (Permission permission : permissions.values()) {
            if (permission.getParentId() == 0) {
                rootPermissions.add(permission);
                buildPermissionTree(permission);
            }
        }
        return rootPermissions;
    }

    // 构建权限树
    private void buildPermissionTree(Permission parent) {
        List<Permission> children = new ArrayList<>();
        for (Permission permission : permissions.values()) {
            if (permission.getParentId().equals(parent.getId())) {
                children.add(permission);
                buildPermissionTree(permission);
            }
        }
        // 这里需要在Permission实体中添加children字段
        // 由于当前Permission实体没有children字段，这里只是模拟实现
    }

    @Override
    public List<Permission> getUserPermissions(Long userId) {
        // 这里需要根据用户ID获取用户角色，然后根据角色获取权限
        // 由于当前没有用户角色关联表，这里只是模拟实现
        List<Long> permissionIds = rolePermissions.getOrDefault(3L, new ArrayList<>());
        List<Permission> userPermissions = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            Permission permission = permissions.get(permissionId);
            if (permission != null) {
                userPermissions.add(permission);
            }
        }
        return userPermissions;
    }

    @Override
    public boolean checkPermission(Long userId, String permissionCode) {
        // 这里需要根据用户ID获取用户角色，然后根据角色获取权限，再检查是否有指定权限
        // 由于当前没有用户角色关联表，这里只是模拟实现
        List<Permission> userPermissions = getUserPermissions(userId);
        for (Permission permission : userPermissions) {
            if (permission.getCode().equals(permissionCode)) {
                return true;
            }
        }
        return false;
    }
}