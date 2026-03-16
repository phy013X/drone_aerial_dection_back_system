package com.expressway.service;

import java.util.Map;

/**
 * 配置服务
 */
public interface ConfigService {
    /**
     * 获取AI配置
     * @return AI配置
     */
    Map<String, Object> getAiConfig();

    /**
     * 更新AI配置
     * @param aiConfig AI配置
     * @return 更新后的配置
     */
    Map<String, Object> updateAiConfig(Map<String, Object> aiConfig);

    /**
     * 测试AI模型
     * @return 测试结果
     */
    Map<String, Object> testAiModel();

    /**
     * 获取告警配置
     * @return 告警配置
     */
    Map<String, Object> getAlertConfig();

    /**
     * 更新告警配置
     * @param alertConfig 告警配置
     * @return 更新后的配置
     */
    Map<String, Object> updateAlertConfig(Map<String, Object> alertConfig);

    /**
     * 获取系统参数
     * @return 系统参数
     */
    Map<String, Object> getSystemConfig();

    /**
     * 更新系统参数
     * @param systemConfig 系统参数
     * @return 更新后的配置
     */
    Map<String, Object> updateSystemConfig(Map<String, Object> systemConfig);

    /**
     * 获取系统信息
     * @return 系统信息
     */
    Map<String, Object> getSystemInfo();

    /**
     * 重置配置
     * @param type 配置类型
     * @return 重置结果
     */
    Map<String, Object> resetConfig(String type);
}