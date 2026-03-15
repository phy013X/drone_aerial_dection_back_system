package com.expressway.service;

import com.expressway.entity.AlertRule;

import java.util.List;

/**
 * 告警规则服务
 */
public interface AlertRuleService {
    /**
     * 获取告警规则列表
     * @param type 告警类型
     * @param enabled 是否启用
     * @return 规则列表
     */
    List<AlertRule> getAlertRuleList(String type, Integer enabled);

    /**
     * 获取告警规则详情
     * @param id 规则ID
     * @return 规则详情
     */
    AlertRule getAlertRuleById(Long id);

    /**
     * 创建告警规则
     * @param alertRule 规则信息
     * @return 规则信息
     */
    AlertRule createAlertRule(AlertRule alertRule);

    /**
     * 更新告警规则
     * @param id 规则ID
     * @param alertRule 规则信息
     * @return 规则信息
     */
    AlertRule updateAlertRule(Long id, AlertRule alertRule);

    /**
     * 删除告警规则
     * @param id 规则ID
     */
    void deleteAlertRule(Long id);

    /**
     * 启用/禁用告警规则
     * @param id 规则ID
     * @param enabled 是否启用
     * @return 规则信息
     */
    AlertRule enableAlertRule(Long id, Integer enabled);
}
