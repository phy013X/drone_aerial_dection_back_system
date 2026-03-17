package com.expressway.entity;

import lombok.Data;

import java.util.Date;

/**
 * 系统日志实体
 */
@Data
public class SystemLog {
    /**
     * 日志ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 操作名称
     */
    private String operation;
    
    /**
     * 模块名称
     */
    private String module;
    
    /**
     * 方法名称
     */
    private String method;
    
    /**
     * 请求参数
     */
    private String params;
    
    /**
     * 返回结果
     */
    private String result;
    
    /**
     * IP地址
     */
    private String ip;
    
    /**
     * 浏览器信息
     */
    private String userAgent;
    
    /**
     * 执行时间（毫秒）
     */
    private Integer executeTime;
    
    /**
     * 状态：1-成功，0-失败
     */
    private Integer status;
    
    /**
     * 错误信息
     */
    private String errorMessage;
    
    /**
     * 创建时间
     */
    private Date createTime;
}
