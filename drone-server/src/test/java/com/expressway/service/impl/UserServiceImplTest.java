package com.expressway.service.impl;

import com.expressway.context.BaseContext;
import com.expressway.dto.UserLoginDTO;
import com.expressway.dto.UserRegisterDTO;
import com.expressway.entity.User;
import com.expressway.mapper.UserMapper;
import com.expressway.properties.JwtProperties;
import com.expressway.utils.JwtUtils;
import com.expressway.vo.UserLoginVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private JwtProperties jwtProperties;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setPassword("password");
        user.setEmail("test@example.com");
        user.setPhone("1234567890");
        
        // 设置当前用户ID
        BaseContext.setCurrentId(1L);
    }

    @AfterEach
    public void tearDown() {
        // 清除当前用户ID
        BaseContext.setCurrentId(null);
    }

    @Test
    public void testLogin() {
        // 模拟userMapper.login返回用户
        when(userMapper.login(eq("test"), anyString())).thenReturn(user);
        // 模拟userMapper.updateLastLoginTime
        doNothing().when(userMapper).updateLastLoginTime(1L);
        // 模拟jwtProperties的getUserSecretKey和getUserTtl方法
        when(jwtProperties.getUserSecretKey()).thenReturn("secret");

        // 调用login方法
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setUsername("test");
        loginDTO.setPassword("password");
        var result = userService.login(loginDTO);

        // 验证结果
        assertNotNull(result);
        assertNotNull(result.getToken());
        verify(userMapper, times(1)).login(eq("test"), anyString());
        verify(userMapper, times(1)).updateLastLoginTime(1L);
    }

    @Test
    public void testRegister() {
        // 模拟userMapper.findByUsername返回null
        when(userMapper.findByUsername("test")).thenReturn(null);
        // 模拟userMapper.findByEmail返回null
        when(userMapper.findByEmail("test@example.com")).thenReturn(null);
        // 模拟userMapper.findByPhone返回null
        when(userMapper.findByPhone("1234567890")).thenReturn(null);
        // 模拟userMapper.register
        doNothing().when(userMapper).register(any(User.class));

        // 调用register方法
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("test");
        registerDTO.setPassword("password");
        registerDTO.setName("Test User");
        registerDTO.setEmail("test@example.com");
        registerDTO.setPhone("1234567890");
        var result = userService.register(registerDTO);

        // 验证结果
        assertNotNull(result);
        verify(userMapper, times(1)).findByUsername("test");
        verify(userMapper, times(1)).findByEmail("test@example.com");
        verify(userMapper, times(1)).findByPhone("1234567890");
        verify(userMapper, times(1)).register(any(User.class));
    }

    @Test
    public void testUpdate() {
        // 模拟userMapper.selectById返回用户
        when(userMapper.selectById(1L)).thenReturn(user);
        // 模拟userMapper.findByEmail返回null
        when(userMapper.findByEmail("test@example.com")).thenReturn(null);
        // 模拟userMapper.findByPhone返回null
        when(userMapper.findByPhone("1234567890")).thenReturn(null);
        // 模拟userMapper.update
        doNothing().when(userMapper).update(any(User.class));
        // 模拟userMapper.selectById返回更新后的用户
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("test");
        when(userMapper.selectById(1L)).thenReturn(updatedUser);

        // 调用update方法
        var result = userService.update(user);

        // 验证结果
        assertNotNull(result);
        verify(userMapper, times(2)).selectById(1L);
        verify(userMapper, times(1)).update(any(User.class));
    }

    @Test
    public void testGetUserByUsername() {
        // 模拟userMapper.findByUsername返回用户
        when(userMapper.findByUsername("test")).thenReturn(user);

        // 调用getUserByUsername方法
        var result = userService.findByUsername("test");

        // 验证结果
        assertNotNull(result);
        verify(userMapper, times(1)).findByUsername("test");
    }
}
