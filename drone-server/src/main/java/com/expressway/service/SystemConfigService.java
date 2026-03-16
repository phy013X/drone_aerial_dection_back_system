package com.expressway.service;

import com.expressway.result.PageResult;

import java.util.Map;

/**
 * 系统配置服务
 */
public interface SystemConfigService {
    /**
     * 获取系统参数配置
     * @param key 配置键
     * @return 配置值
     */
    String getSystemParam(String key);

    /**
     * 设置系统参数配置
     * @param key 配置键
     * @param value 配置值
     * @param description 配置描述
     */
    void setSystemParam(String key, String value, String description);

    /**
     * 获取所有系统参数配置
     * @return 系统参数配置列表
     */
    PageResult<Map<String, Object>> getAllSystemParams(int page, int pageSize);

    /**
     * 删除系统参数配置
     * @param key 配置键
     */
    void deleteSystemParam(String key);

    /**
     * 获取设备类型配置
     * @return 设备类型配置列表
     */
    PageResult<Map<String, Object>> getDeviceTypes(int page, int pageSize);

    /**
     * 添加设备类型配置
     * @param typeName 类型名称
     * @param description 描述
     * @param params 配置参数
     */
    void addDeviceType(String typeName, String description, Map<String, Object> params);

    /**
     * 更新设备类型配置
     * @param typeId 类型ID
     * @param typeName 类型名称
     * @param description 描述
     * @param params 配置参数
     */
    void updateDeviceType(Long typeId, String typeName, String description, Map<String, Object> params);

    /**
     * 删除设备类型配置
     * @param typeId 类型ID
     */
    void deleteDeviceType(Long typeId);

    /**
     * 获取检测模型配置
     * @return 检测模型配置列表
     */
    PageResult<Map<String, Object>> getDetectionModels(int page, int pageSize);

    /**
     * 添加检测模型配置
     * @param modelName 模型名称
     * @param modelPath 模型路径
     * @param modelType 模型类型
     * @param description 描述
     * @param params 配置参数
     */
    void addDetectionModel(String modelName, String modelPath, String modelType, String description, Map<String, Object> params);

    /**
     * 更新检测模型配置
     * @param modelId 模型ID
     * @param modelName 模型名称
     * @param modelPath 模型路径
     * @param modelType 模型类型
     * @param description 描述
     * @param params 配置参数
     */
    void updateDetectionModel(Long modelId, String modelName, String modelPath, String modelType, String description, Map<String, Object> params);

    /**
     * 删除检测模型配置
     * @param modelId 模型ID
     */
    void deleteDetectionModel(Long modelId);

    /**
     * 获取告警规则配置
     * @return 告警规则配置列表
     */
    PageResult<Map<String, Object>> getAlertRules(int page, int pageSize);

    /**
     * 添加告警规则配置
     * @param ruleName 规则名称
     * @param ruleType 规则类型
     * @param level 告警级别
     * @param condition 告警条件
     * @param action 告警动作
     * @param description 描述
     */
    void addAlertRule(String ruleName, String ruleType, String level, String condition, String action, String description);

    /**
     * 更新告警规则配置
     * @param ruleId 规则ID
     * @param ruleName 规则名称
     * @param ruleType 规则类型
     * @param level 告警级别
     * @param condition 告警条件
     * @param action 告警动作
     * @param description 描述
     */
    void updateAlertRule(Long ruleId, String ruleName, String ruleType, String level, String condition, String action, String description);

    /**
     * 删除告警规则配置
     * @param ruleId 规则ID
     */
    void deleteAlertRule(Long ruleId);

    /**
     * 启用/禁用告警规则
     * @param ruleId 规则ID
     * @param enabled 是否启用
     */
    void enableAlertRule(Long ruleId, boolean enabled);

    /**
     * 获取系统状态
     * @return 系统状态信息
     */
    Map<String, Object> getSystemStatus();

    /**
     * 获取系统日志配置
     * @return 系统日志配置
     */
    Map<String, Object> getLogConfig();

    /**
     * 更新系统日志配置
     * @param config 日志配置
     */
    void updateLogConfig(Map<String, Object> config);

    /**
     * 系统重启
     */
    void restartSystem();

    /**
     * 系统关闭
     */
    void shutdownSystem();

    /**
     * 系统备份
     * @param backupName 备份名称
     * @return 备份文件路径
     */
    String backupSystem(String backupName);

    /**
     * 系统恢复
     * @param backupPath 备份文件路径
     */
    void restoreSystem(String backupPath);

    /**
     * 获取系统备份列表
     * @return 备份列表
     */
    PageResult<Map<String, Object>> getSystemBackups(int page, int pageSize);

    /**
     * 删除系统备份
     * @param backupId 备份ID
     */
    void deleteSystemBackup(Long backupId);
}