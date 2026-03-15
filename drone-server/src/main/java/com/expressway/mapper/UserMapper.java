package com.expressway.mapper;

import com.expressway.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper
 */
@Mapper
public interface UserMapper {
    /**
     * 用户登录
     * @param username 用户名/邮箱/手机号
     * @return 用户信息
     */
    User login(@Param("username") String username);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);

    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return 用户信息
     */
    User findByEmail(String email);

    /**
     * 根据手机号查询用户
     * @param phone 手机号
     * @return 用户信息
     */
    User findByPhone(String phone);

    /**
     * 注册用户
     * @param user 用户信息
     */
    void register(User user);

    /**
     * 更新最后登录时间
     * @param id 用户ID
     */
    void updateLastLoginTime(Long id);

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户信息
     */
    User selectById(Long id);

    /**
     * 更新用户信息
     * @param user 用户信息
     */
    void update(User user);
}
