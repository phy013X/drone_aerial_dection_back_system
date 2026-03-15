package com.expressway.dto;

import lombok.Data;

/**
 * 用户登录DTO
 */
@Data
public class UserLoginDTO {
    private String username; // 用户名/邮箱/手机号
    private String password; // 密码
}
