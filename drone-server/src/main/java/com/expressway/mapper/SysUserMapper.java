package com.expressway.mapper;

import com.expressway.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户数据访问接口
 */
@Mapper
public interface SysUserMapper {
    /**
     * 根据用户名查询用户（关联部门、角色名称）
     */
    User selectByUsername(@Param("username") String username);

    /**
     * 根据用户ID查询用户
     */
    User selectById(@Param("id") Long id);
}