package com.expressway.service;

import com.expressway.entity.SystemLog;
import com.expressway.entity.SystemConfig;
import com.expressway.result.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 系统服务接口
 */
public interface SystemService {

    /**
     * 获取系统配置
     * @param key 配置键
     * @return 系统配置
     */
    SystemConfig getSystemConfig(String key);

    /**
     * 获取所有系统配置
     * @return 系统配置列表
     */
    List<SystemConfig> getAllSystemConfigs();

    /**
     * 更新系统配置
     * @param key 配置键
     * @param value 配置值
     * @param description 配置描述
     * @return 系统配置
     */
    SystemConfig updateSystemConfig(String key, String value, String description);

    /**
     * 获取系统日志
     * @param type 日志类型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param page 页码
     * @param pageSize 每页数量
     * @return 系统日志分页结果
     */
    PageResult<SystemLog> getSystemLogs(String type, String startDate, String endDate, Integer page, Integer pageSize);

    /**
     * 获取系统状态
     * @return 系统状态
     */
    Map<String, Object> getSystemStatus();

    /**
     * 创建系统日志
     * @param type 日志类型
     * @param content 日志内容
     * @param operator 操作人
     */
    void createSystemLog(String type, String content, String operator);
}
