package com.expressway.dto;

import lombok.Data;

/**
 * 用户注册DTO
 */
@Data
public class UserRegisterDTO {
    private String username; // 用户名
    private String password; // 密码
    private String name; // 姓名
    private String email; // 邮箱
    private String phone; // 手机号
}
