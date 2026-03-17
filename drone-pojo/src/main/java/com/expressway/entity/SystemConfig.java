package com.expressway.entity;

import lombok.Data;

import java.util.Date;

/**
 * 系统配置实体
 */
@Data
public class SystemConfig {
    /**
     * 配置ID
     */
    private Long id;
    
    /**
     * 配置键
     */
    private String key;
    
    /**
     * 配置值
     */
    private String value;
    
    /**
     * 配置类型：string、number、boolean、json
     */
    private String type;
    
    /**
     * 配置描述
     */
    private String description;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
}
