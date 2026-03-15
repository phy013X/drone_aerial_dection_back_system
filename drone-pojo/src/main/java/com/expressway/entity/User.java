package com.expressway.entity;


import lombok.Data;
import java.util.Date;

/**
 * 用户实体（对应user表）
 */
@Data
public class User {
    private Long id; // 主键ID
    private String username; // 登录用户名（唯一）
    private String password; // 加密后的密码
    private String name; // 姓名
    private String email; // 邮箱
    private String phone; // 手机号
    private String avatar; // 头像URL
    private Long roleId; // 角色ID
    private Integer status; // 状态：1-正常，0-禁用
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private Date lastLoginTime; // 最后登录时间

    // 关联查询字段（非数据库字段）
    private String roleName;
}