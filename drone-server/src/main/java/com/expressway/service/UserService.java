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

    /**
     * 更新用户密码
     * @param id 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 更新后的用户信息
     */
    User updatePassword(Long id, String oldPassword, String newPassword);

    /**
     * 更新用户头像
     * @param id 用户ID
     * @param avatar 头像URL
     * @return 更新后的用户信息
     */
    User updateAvatar(Long id, String avatar);

    /**
     * 更新用户邮箱
     * @param id 用户ID
     * @param email 邮箱
     * @return 更新后的用户信息
     */
    User updateEmail(Long id, String email);

    /**
     * 更新用户手机号
     * @param id 用户ID
     * @param phone 手机号
     * @return 更新后的用户信息
     */
    User updatePhone(Long id, String phone);

    /**
     * 获取用户登录历史
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 登录历史列表
     */
    com.expressway.result.PageResult<Object> getLoginHistory(Long userId, Integer page, Integer pageSize);

    /**
     * 获取用户操作日志
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 操作日志列表
     */
    com.expressway.result.PageResult<Object> getOperationLogs(Long userId, Integer page, Integer pageSize);

    /**
     * 获取用户统计信息
     * @param userId 用户ID
     * @return 统计信息
     */
    Object getUserStats(Long userId);
}