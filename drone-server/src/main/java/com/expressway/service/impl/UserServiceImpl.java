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
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        // 1. 根据用户名/邮箱/手机号查询用户
        User user = userMapper.login(username);

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
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setPhone(registerDTO.getPhone());
        user.setRoleId(3L); // 默认普通用户角色
        user.setStatus(1); // 默认启用

        // 5. 保存用户
        userMapper.register(user);

        return user;
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
}