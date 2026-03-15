package com.expressway.controller;

import com.expressway.entity.AlertRule;
import com.expressway.result.Result;
import com.expressway.service.AlertRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 告警规则控制器
 */
@RestController
@RequestMapping("/alert-rules")
@Tag(name = "告警规则管理", description = "告警规则配置接口")
@Slf4j
public class AlertRuleController {

    @Resource
    private AlertRuleService alertRuleService;

    /**
     * 获取告警规则列表
     */
    @GetMapping
    @Operation(summary = "获取告警规则列表", description = "获取告警规则列表，支持类型和启用状态筛选")
    public Result<List<AlertRule>> getAlertRuleList(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "enabled", required = false) Integer enabled) {
        log.info("获取告警规则列表：type={}, enabled={}", type, enabled);
        List<AlertRule> alertRuleList = alertRuleService.getAlertRuleList(type, enabled);
        return Result.success(alertRuleList);
    }

    /**
     * 获取告警规则详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取告警规则详情", description = "根据规则ID获取告警规则详细信息")
    public Result<AlertRule> getAlertRuleById(@PathVariable Long id) {
        log.info("获取告警规则详情：id={}", id);
        AlertRule alertRule = alertRuleService.getAlertRuleById(id);
        return Result.success(alertRule);
    }

    /**
     * 创建告警规则
     */
    @PostMapping
    @Operation(summary = "创建告警规则", description = "创建新的告警规则")
    public Result<?> createAlertRule(@Validated @RequestBody AlertRule alertRule) {
        log.info("创建告警规则：{}", alertRule);
        alertRuleService.createAlertRule(alertRule);
        return Result.success("创建成功");
    }

    /**
     * 更新告警规则
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新告警规则", description = "更新告警规则信息")
    public Result<?> updateAlertRule(@PathVariable Long id, @Validated @RequestBody AlertRule alertRule) {
        log.info("更新告警规则：id={}, {}", id, alertRule);
        alertRuleService.updateAlertRule(id, alertRule);
        return Result.success("更新成功");
    }

    /**
     * 删除告警规则
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除告警规则", description = "删除指定告警规则")
    public Result<?> deleteAlertRule(@PathVariable Long id) {
        log.info("删除告警规则：id={}", id);
        alertRuleService.deleteAlertRule(id);
        return Result.success("删除成功");
    }

    /**
     * 启用/禁用告警规则
     */
    @PostMapping("/{id}/enable")
    @Operation(summary = "启用/禁用告警规则", description = "启用或禁用指定告警规则")
    public Result<?> enableAlertRule(@PathVariable Long id, @RequestParam(value = "enabled") Integer enabled) {
        log.info("启用/禁用告警规则：id={}, enabled={}", id, enabled);
        alertRuleService.enableAlertRule(id, enabled);
        return Result.success(enabled == 1 ? "启用成功" : "禁用成功");
    }
}
