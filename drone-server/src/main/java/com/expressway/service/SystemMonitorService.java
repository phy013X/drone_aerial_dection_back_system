package com.expressway.service;

import com.expressway.result.PageResult;

import java.util.Map;

/**
 * 系统监控服务
 */
public interface SystemMonitorService {
    /**
     * 获取系统运行状态
     * @return 系统运行状态
     */
    Map<String, Object> getSystemStatus();

    /**
     * 获取系统资源使用情况
     * @return 系统资源使用情况
     */
    Map<String, Object> getSystemResources();

    /**
     * 获取系统运行统计
     * @return 系统运行统计
     */
    Map<String, Object> getSystemStats();

    /**
     * 获取设备状态列表
     * @param page 页码
     * @param pageSize 每页数量
     * @return 设备状态列表
     */
    PageResult<Map<String, Object>> getDeviceStatusList(int page, int pageSize);

    /**
     * 获取设备详细状态
     * @param deviceId 设备ID
     * @return 设备详细状态
     */
    Map<String, Object> getDeviceStatusDetail(Long deviceId);

    /**
     * 获取任务执行状态列表
     * @param type 任务类型
     * @param status 任务状态
     * @param page 页码
     * @param pageSize 每页数量
     * @return 任务执行状态列表
     */
    PageResult<Map<String, Object>> getTaskStatusList(String type, Integer status, int page, int pageSize);

    /**
     * 获取任务详细状态
     * @param taskId 任务ID
     * @return 任务详细状态
     */
    Map<String, Object> getTaskStatusDetail(Long taskId);

    /**
     * 获取系统异常列表
     * @param level 异常级别
     * @param type 异常类型
     * @param page 页码
     * @param pageSize 每页数量
     * @return 系统异常列表
     */
    PageResult<Map<String, Object>> getSystemExceptionList(String level, String type, int page, int pageSize);

    /**
     * 获取异常详细信息
     * @param exceptionId 异常ID
     * @return 异常详细信息
     */
    Map<String, Object> getSystemExceptionDetail(Long exceptionId);

    /**
     * 处理系统异常
     * @param exceptionId 异常ID
     * @param handler 处理人
     * @param solution 解决方案
     */
    void handleSystemException(Long exceptionId, String handler, String solution);

    /**
     * 获取系统日志列表
     * @param level 日志级别
     * @param type 日志类型
     * @param keyword 关键字
     * @param page 页码
     * @param pageSize 每页数量
     * @return 系统日志列表
     */
    PageResult<Map<String, Object>> getSystemLogList(String level, String type, String keyword, int page, int pageSize);

    /**
     * 获取系统预警列表
     * @param level 预警级别
     * @param type 预警类型
     * @param page 页码
     * @param pageSize 每页数量
     * @return 系统预警列表
     */
    PageResult<Map<String, Object>> getSystemAlertList(String level, String type, int page, int pageSize);

    /**
     * 处理系统预警
     * @param alertId 预警ID
     * @param handler 处理人
     * @param solution 解决方案
     */
    void handleSystemAlert(Long alertId, String handler, String solution);

    /**
     * 获取系统性能指标
     * @param metric 指标类型
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param interval 时间间隔
     * @return 系统性能指标
     */
    Map<String, Object> getSystemMetrics(String metric, String startDate, String endDate, String interval);

    /**
     * 获取系统健康检查结果
     * @return 系统健康检查结果
     */
    Map<String, Object> getSystemHealthCheck();

    /**
     * 手动触发系统健康检查
     * @return 检查结果
     */
    Map<String, Object> triggerHealthCheck();

    /**
     * 获取系统监控配置
     * @return 系统监控配置
     */
    Map<String, Object> getMonitorConfig();

    /**
     * 更新系统监控配置
     * @param config 监控配置
     */
    void updateMonitorConfig(Map<String, Object> config);
}