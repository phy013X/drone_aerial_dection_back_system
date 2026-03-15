package com.expressway.service;

import com.expressway.dto.UserLoginDTO;
import com.expressway.dto.UserRegisterDTO;
import com.expressway.entity.User;
import com.expressway.vo.UserLoginVO;

/**
 * 用户服务
 */
public interface UserService {
    /**
     * 用户登录
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    UserLoginVO login(UserLoginDTO loginDTO);

    /**
     * 用户注册
     * @param registerDTO 注册信息
     * @return 注册结果
     */
    User register(UserRegisterDTO registerDTO);

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户信息
     */
    User selectById(Long id);

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
     * 更新用户信息
     * @param user 用户信息
     * @return 更新后的用户信息
     */
    User update(User user);
}