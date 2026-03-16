package com.expressway.service.impl;

import com.expressway.context.BaseContext;
import com.expressway.dto.UserLoginDTO;
import com.expressway.dto.UserRegisterDTO;
import com.expressway.entity.User;
import com.expressway.exception.AuthException;
import com.expressway.mapper.UserMapper;
import com.expressway.properties.JwtProperties;
import com.expressway.service.UserService;
import com.expressway.utils.JwtUtils;
import com.expressway.utils.PasswordUtils;
import com.expressway.vo.UserLoginVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public UserLoginVO login(UserLoginDTO loginDTO) {
        String userIdentifier = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        // 1. 根据用户名/邮箱/手机号查询用户
        User user = userMapper.login(userIdentifier);

        // 2. 验证用户是否存在
        if (user == null) {
            throw new AuthException("用户名或密码错误");
        }

        // 3. 验证密码
        if (!PasswordUtils.verify(password, user.getPassword())) {
            throw new AuthException("用户名或密码错误");
        }

        // 4. 更新最后登录时间
        userMapper.updateLastLoginTime(user.getId());

        // 5. 生成JWT令牌
        String token = JwtUtils.generateToken(
                jwtProperties.getUserSecretKey(),
                user.getId().toString()
        );

        // 6. 构建返回对象
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setToken(token);

        UserLoginVO.UserInfo userInfo = new UserLoginVO.UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setName(user.getName());
        userInfo.setRole(user.getRoleName());

        userLoginVO.setUser(userInfo);

        // 7. 记录当前登录用户的ID
        BaseContext.setCurrentId(user.getId());

        return userLoginVO;
    }

    @Override
    public User register(UserRegisterDTO registerDTO) {
        // 1. 检查用户名是否已存在
        if (userMapper.findByUsername(registerDTO.getUsername()) != null) {
            throw new AuthException("用户名已存在");
        }

        // 2. 检查邮箱是否已存在
        if (registerDTO.getEmail() != null && userMapper.findByEmail(registerDTO.getEmail()) != null) {
            throw new AuthException("邮箱已存在");
        }

        // 3. 检查手机号是否已存在
        if (registerDTO.getPhone() != null && userMapper.findByPhone(registerDTO.getPhone()) != null) {
            throw new AuthException("手机号已存在");
        }

        // 4. 创建用户
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(PasswordUtils.encrypt(registerDTO.getPassword()));
        user.setName(registerDTO.getName() != null ? registerDTO.getName() : "");
        user.setEmail(registerDTO.getEmail() != null ? registerDTO.getEmail() : "");
        user.setPhone(registerDTO.getPhone() != null ? registerDTO.getPhone() : "");
        user.setRoleId(3L); // 默认普通用户角色
        user.setStatus(1); // 默认启用

        // 5. 保存用户
        userMapper.register(user);

        return user;
    }

    @Override
    public User updatePassword(Long id, String oldPassword, String newPassword) {
        // 检查用户是否存在
        User existingUser = userMapper.selectById(id);
        if (existingUser == null) {
            throw new AuthException("用户不存在");
        }
        
        // 验证旧密码
        if (!PasswordUtils.verify(oldPassword, existingUser.getPassword())) {
            throw new AuthException("旧密码错误");
        }
        
        // 验证新密码格式
        if (newPassword.length() < 8 || newPassword.length() > 20) {
            throw new AuthException("密码长度必须在8-20个字符之间");
        }
        
        // 更新密码
        User user = new User();
        user.setId(id);
        user.setPassword(PasswordUtils.encrypt(newPassword));
        userMapper.update(user);
        
        return userMapper.selectById(id);
    }

    @Override
    public User updateAvatar(Long id, String avatar) {
        // 检查用户是否存在
        User existingUser = userMapper.selectById(id);
        if (existingUser == null) {
            throw new AuthException("用户不存在");
        }
        
        // 更新头像
        User user = new User();
        user.setId(id);
        user.setAvatar(avatar);
        userMapper.update(user);
        
        return userMapper.selectById(id);
    }

    @Override
    public User selectById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public User findByPhone(String phone) {
        return userMapper.findByPhone(phone);
    }

    @Override
    public User update(User user) {
        // 检查用户是否存在
        User existingUser = userMapper.selectById(user.getId());
        if (existingUser == null) {
            throw new AuthException("用户不存在");
        }
        // 检查邮箱是否已被其他用户使用
        if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())) {
            User userByEmail = userMapper.findByEmail(user.getEmail());
            if (userByEmail != null && !userByEmail.getId().equals(user.getId())) {
                throw new AuthException("邮箱已被其他用户使用");
            }
        }
        // 检查手机号是否已被其他用户使用
        if (user.getPhone() != null && !user.getPhone().equals(existingUser.getPhone())) {
            User userByPhone = userMapper.findByPhone(user.getPhone());
            if (userByPhone != null && !userByPhone.getId().equals(user.getId())) {
                throw new AuthException("手机号已被其他用户使用");
            }
        }
        // 更新用户信息
        userMapper.update(user);
        // 返回更新后的用户信息
        return userMapper.selectById(user.getId());
    }

    @Override
    public User updateEmail(Long id, String email) {
        // 检查用户是否存在
        User existingUser = userMapper.selectById(id);
        if (existingUser == null) {
            throw new AuthException("用户不存在");
        }
        // 检查邮箱格式
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$")) {
            throw new AuthException("邮箱格式不正确");
        }
        // 检查邮箱是否已被其他用户使用
        User userByEmail = userMapper.findByEmail(email);
        if (userByEmail != null && !userByEmail.getId().equals(id)) {
            throw new AuthException("邮箱已被其他用户使用");
        }
        // 更新邮箱
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        userMapper.update(user);
        return userMapper.selectById(id);
    }

    @Override
    public User updatePhone(Long id, String phone) {
        // 检查用户是否存在
        User existingUser = userMapper.selectById(id);
        if (existingUser == null) {
            throw new AuthException("用户不存在");
        }
        // 检查手机号格式
        if (phone == null || !phone.matches("^1[3-9]\\d{9}$")) {
            throw new AuthException("手机号格式不正确");
        }
        // 检查手机号是否已被其他用户使用
        User userByPhone = userMapper.findByPhone(phone);
        if (userByPhone != null && !userByPhone.getId().equals(id)) {
            throw new AuthException("手机号已被其他用户使用");
        }
        // 更新手机号
        User user = new User();
        user.setId(id);
        user.setPhone(phone);
        userMapper.update(user);
        return userMapper.selectById(id);
    }

    @Override
    public com.expressway.result.PageResult<Object> getLoginHistory(Long userId, Integer page, Integer pageSize) {
        // 计算分页偏移量
        int offset = (page - 1) * pageSize;
        // 获取登录历史列表
        java.util.List<Object> loginHistoryList = userMapper.getLoginHistory(userId, offset, pageSize);
        // 获取登录历史总数
        int total = userMapper.getLoginHistoryCount(userId);
        return new com.expressway.result.PageResult<>(total, loginHistoryList);
    }

    @Override
    public com.expressway.result.PageResult<Object> getOperationLogs(Long userId, Integer page, Integer pageSize) {
        // 计算分页偏移量
        int offset = (page - 1) * pageSize;
        // 获取操作日志列表
        java.util.List<Object> operationLogsList = userMapper.getOperationLogs(userId, offset, pageSize);
        // 获取操作日志总数
        int total = userMapper.getOperationLogsCount(userId);
        return new com.expressway.result.PageResult<>(total, operationLogsList);
    }

    @Override
    public Object getUserStats(Long userId) {
        // 获取用户统计信息
        java.util.Map<String, Object> stats = new java.util.HashMap<>();
        // 获取登录次数
        stats.put("loginCount", userMapper.getLoginHistoryCount(userId));
        // 获取操作次数
        stats.put("operationCount", userMapper.getOperationLogsCount(userId));
        // 获取用户信息
        User user = userMapper.selectById(userId);
        if (user != null) {
            stats.put("registerTime", user.getCreateTime());
            stats.put("lastLoginTime", user.getLastLoginTime());
        }
        return stats;
    }
}