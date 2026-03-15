package com.expressway.mapper;

import com.expressway.entity.AlertRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 告警规则Mapper
 */
@Mapper
public interface AlertRuleMapper {
    /**
     * 获取告警规则列表
     * @param type 告警类型
     * @param enabled 是否启用
     * @return 规则列表
     */
    List<AlertRule> getAlertRuleList(@Param("type") String type, @Param("enabled") Integer enabled);

    /**
     * 获取告警规则详情
     * @param id 规则ID
     * @return 规则详情
     */
    AlertRule getAlertRuleById(Long id);

    /**
     * 创建告警规则
     * @param alertRule 规则信息
     */
    void createAlertRule(AlertRule alertRule);

    /**
     * 更新告警规则
     * @param alertRule 规则信息
     */
    void updateAlertRule(AlertRule alertRule);

    /**
     * 删除告警规则
     * @param id 规则ID
     */
    void deleteAlertRule(Long id);

    /**
     * 启用/禁用告警规则
     * @param id 规则ID
     * @param enabled 是否启用
     */
    void enableAlertRule(@Param("id") Long id, @Param("enabled") Integer enabled);
}
