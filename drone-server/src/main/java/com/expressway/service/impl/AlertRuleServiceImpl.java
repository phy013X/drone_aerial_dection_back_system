package com.expressway.service.impl;

import com.expressway.entity.AlertRule;
import com.expressway.exception.BusinessException;
import com.expressway.mapper.AlertRuleMapper;
import com.expressway.service.AlertRuleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 告警规则服务实现
 */
@Service
@Slf4j
public class AlertRuleServiceImpl implements AlertRuleService {

    @Resource
    private AlertRuleMapper alertRuleMapper;

    @Override
    public List<AlertRule> getAlertRuleList(String type, Integer enabled) {
        return alertRuleMapper.getAlertRuleList(type, enabled);
    }

    @Override
    public AlertRule getAlertRuleById(Long id) {
        AlertRule alertRule = alertRuleMapper.getAlertRuleById(id);
        if (alertRule == null) {
            throw new BusinessException("告警规则不存在");
        }
        return alertRule;
    }

    @Override
    public AlertRule createAlertRule(AlertRule alertRule) {
        // 创建设置默认值
        if (alertRule.getEnabled() == null) {
            alertRule.setEnabled(1);
        }
        if (alertRule.getLevel() == null) {
            alertRule.setLevel(2); // 默认中级
        }
        if (alertRule.getThreshold() == null) {
            alertRule.setThreshold(1);
        }
        if (alertRule.getAreaType() == null) {
            alertRule.setAreaType("all");
        }
        if (alertRule.getTimeType() == null) {
            alertRule.setTimeType("all");
        }
        // 创建规则
        alertRuleMapper.createAlertRule(alertRule);
        return alertRule;
    }

    @Override
    public AlertRule updateAlertRule(Long id, AlertRule alertRule) {
        // 检查规则是否存在
        if (alertRuleMapper.getAlertRuleById(id) == null) {
            throw new BusinessException("告警规则不存在");
        }
        // 更新规则
        alertRule.setId(id);
        alertRuleMapper.updateAlertRule(alertRule);
        return alertRuleMapper.getAlertRuleById(id);
    }

    @Override
    public void deleteAlertRule(Long id) {
        // 检查规则是否存在
        if (alertRuleMapper.getAlertRuleById(id) == null) {
            throw new BusinessException("告警规则不存在");
        }
        // 删除规则
        alertRuleMapper.deleteAlertRule(id);
    }

    @Override
    public AlertRule enableAlertRule(Long id, Integer enabled) {
        // 检查规则是否存在
        if (alertRuleMapper.getAlertRuleById(id) == null) {
            throw new BusinessException("告警规则不存在");
        }
        // 启用/禁用规则
        alertRuleMapper.enableAlertRule(id, enabled);
        return alertRuleMapper.getAlertRuleById(id);
    }
}
