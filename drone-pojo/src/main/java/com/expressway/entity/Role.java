package com.expressway.entity;

import lombok.Data;
import java.util.Date;

/**
 * 角色实体（对应role表）
 */
@Data
public class Role {
    private Long id; // 主键ID
    private String name; // 角色名称
    private String code; // 角色编码
    private String description; // 角色描述
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
}
