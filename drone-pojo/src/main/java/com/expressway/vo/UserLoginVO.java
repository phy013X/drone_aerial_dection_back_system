package com.expressway.vo;

import lombok.Data;

/**
 * 用户登录VO
 */
@Data
public class UserLoginVO {
    private String token; // JWT令牌
    private UserInfo user; // 用户信息

    @Data
    public static class UserInfo {
        private Long id; // 用户ID
        private String username; // 用户名
        private String name; // 姓名
        private String role; // 角色名称
    }
}
