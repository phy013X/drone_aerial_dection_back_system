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
     * @param userIdentifier 用户名/邮箱/手机号
     * @return 用户信息
     */
    User login(@Param("userIdentifier") String userIdentifier);

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

    /**
     * 获取用户登录历史
     * @param userId 用户ID
     * @param offset 偏移量
     * @param limit 每页数量
     * @return 登录历史列表
     */
    java.util.List<Object> getLoginHistory(@Param("userId") Long userId, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 获取用户登录历史总数
     * @param userId 用户ID
     * @return 登录历史总数
     */
    Integer getLoginHistoryCount(@Param("userId") Long userId);

    /**
     * 获取用户操作日志
     * @param userId 用户ID
     * @param offset 偏移量
     * @param limit 每页数量
     * @return 操作日志列表
     */
    java.util.List<Object> getOperationLogs(@Param("userId") Long userId, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 获取用户操作日志总数
     * @param userId 用户ID
     * @return 操作日志总数
     */
    Integer getOperationLogsCount(@Param("userId") Long userId);
}
