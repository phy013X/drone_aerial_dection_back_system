package com.expressway.entity;

import lombok.Data;
import java.util.Date;

/**
 * 权限实体（对应permission表）
 */
@Data
public class Permission {
    private Long id; // 主键ID
    private String name; // 权限名称
    private String code; // 权限编码
    private Integer type; // 类型：1-菜单，2-按钮，3-接口
    private Long parentId; // 父权限ID
    private String path; // 路径
    private String icon; // 图标
    private Integer sort; // 排序
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
}
