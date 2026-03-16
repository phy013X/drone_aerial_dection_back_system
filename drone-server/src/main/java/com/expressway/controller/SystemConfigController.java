package com.expressway.controller;

import com.expressway.result.Result;
import com.expressway.service.SystemConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统配置控制器
 */
@RestController
@RequestMapping("/system")
@Tag(name = "系统配置", description = "系统配置与管理相关接口")
@Slf4j
public class SystemConfigController {

    @Resource
    private SystemConfigService systemConfigService;

    /**
     * 获取系统参数配置
     */
    @GetMapping("/param/{key}")
    @Operation(summary = "获取系统参数配置", description = "根据键获取系统参数配置值")
    public Result<?> getSystemParam(@PathVariable String key) {
        log.info("获取系统参数：key={}", key);
        String value = systemConfigService.getSystemParam(key);
        return Result.success(value);
    }

    /**
     * 设置系统参数配置
     */
    @PutMapping("/param/{key}")
    @Operation(summary = "设置系统参数配置", description = "设置系统参数配置")
    public Result<?> setSystemParam(
            @PathVariable String key,
            @RequestParam String value,
            @RequestParam(value = "description", required = false) String description) {
        log.info("设置系统参数：key={}, value={}, description={}", key, value, description);
        systemConfigService.setSystemParam(key, value, description);
        return Result.success("设置成功");
    }

    /**
     * 获取所有系统参数配置
     */
    @GetMapping("/params")
    @Operation(summary = "获取所有系统参数配置", description = "获取所有系统参数配置")
    public Result<?> getAllSystemParams(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取所有系统参数：page={}, pageSize={}", page, pageSize);
        return Result.success(systemConfigService.getAllSystemParams(page, pageSize));
    }

    /**
     * 删除系统参数配置
     */
    @DeleteMapping("/param/{key}")
    @Operation(summary = "删除系统参数配置", description = "删除系统参数配置")
    public Result<?> deleteSystemParam(@PathVariable String key) {
        log.info("删除系统参数：key={}", key);
        systemConfigService.deleteSystemParam(key);
        return Result.success("删除成功");
    }

    /**
     * 获取设备类型配置
     */
    @GetMapping("/device-types")
    @Operation(summary = "获取设备类型配置", description = "获取设备类型配置列表")
    public Result<?> getDeviceTypes(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取设备类型：page={}, pageSize={}", page, pageSize);
        return Result.success(systemConfigService.getDeviceTypes(page, pageSize));
    }

    /**
     * 添加设备类型配置
     */
    @PostMapping("/device-types")
    @Operation(summary = "添加设备类型配置", description = "添加设备类型配置")
    public Result<?> addDeviceType(
            @RequestParam String typeName,
            @RequestParam(value = "description", required = false) String description,
            @RequestBody(required = false) Map<String, Object> params) {
        log.info("添加设备类型：typeName={}, description={}, params={}", typeName, description, params);
        systemConfigService.addDeviceType(typeName, description, params);
        return Result.success("添加成功");
    }

    /**
     * 更新设备类型配置
     */
    @PutMapping("/device-types/{typeId}")
    @Operation(summary = "更新设备类型配置", description = "更新设备类型配置")
    public Result<?> updateDeviceType(
            @PathVariable Long typeId,
            @RequestParam String typeName,
            @RequestParam(value = "description", required = false) String description,
            @RequestBody(required = false) Map<String, Object> params) {
        log.info("更新设备类型：typeId={}, typeName={}, description={}, params={}", typeId, typeName, description, params);
        systemConfigService.updateDeviceType(typeId, typeName, description, params);
        return Result.success("更新成功");
    }

    /**
     * 删除设备类型配置
     */
    @DeleteMapping("/device-types/{typeId}")
    @Operation(summary = "删除设备类型配置", description = "删除设备类型配置")
    public Result<?> deleteDeviceType(@PathVariable Long typeId) {
        log.info("删除设备类型：typeId={}", typeId);
        systemConfigService.deleteDeviceType(typeId);
        return Result.success("删除成功");
    }

    /**
     * 获取检测模型配置
     */
    @GetMapping("/detection-models")
    @Operation(summary = "获取检测模型配置", description = "获取检测模型配置列表")
    public Result<?> getDetectionModels(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取检测模型：page={}, pageSize={}", page, pageSize);
        return Result.success(systemConfigService.getDetectionModels(page, pageSize));
    }

    /**
     * 添加检测模型配置
     */
    @PostMapping("/detection-models")
    @Operation(summary = "添加检测模型配置", description = "添加检测模型配置")
    public Result<?> addDetectionModel(
            @RequestParam String modelName,
            @RequestParam String modelPath,
            @RequestParam String modelType,
            @RequestParam(value = "description", required = false) String description,
            @RequestBody(required = false) Map<String, Object> params) {
        log.info("添加检测模型：modelName={}, modelPath={}, modelType={}, description={}, params={}", modelName, modelPath, modelType, description, params);
        systemConfigService.addDetectionModel(modelName, modelPath, modelType, description, params);
        return Result.success("添加成功");
    }

    /**
     * 更新检测模型配置
     */
    @PutMapping("/detection-models/{modelId}")
    @Operation(summary = "更新检测模型配置", description = "更新检测模型配置")
    public Result<?> updateDetectionModel(
            @PathVariable Long modelId,
            @RequestParam String modelName,
            @RequestParam String modelPath,
            @RequestParam String modelType,
            @RequestParam(value = "description", required = false) String description,
            @RequestBody(required = false) Map<String, Object> params) {
        log.info("更新检测模型：modelId={}, modelName={}, modelPath={}, modelType={}, description={}, params={}", modelId, modelName, modelPath, modelType, description, params);
        systemConfigService.updateDetectionModel(modelId, modelName, modelPath, modelType, description, params);
        return Result.success("更新成功");
    }

    /**
     * 删除检测模型配置
     */
    @DeleteMapping("/detection-models/{modelId}")
    @Operation(summary = "删除检测模型配置", description = "删除检测模型配置")
    public Result<?> deleteDetectionModel(@PathVariable Long modelId) {
        log.info("删除检测模型：modelId={}", modelId);
        systemConfigService.deleteDetectionModel(modelId);
        return Result.success("删除成功");
    }

    /**
     * 获取告警规则配置
     */
    @GetMapping("/alert-rules")
    @Operation(summary = "获取告警规则配置", description = "获取告警规则配置列表")
    public Result<?> getAlertRules(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取告警规则：page={}, pageSize={}", page, pageSize);
        return Result.success(systemConfigService.getAlertRules(page, pageSize));
    }

    /**
     * 添加告警规则配置
     */
    @PostMapping("/alert-rules")
    @Operation(summary = "添加告警规则配置", description = "添加告警规则配置")
    public Result<?> addAlertRule(
            @RequestParam String ruleName,
            @RequestParam String ruleType,
            @RequestParam String level,
            @RequestParam String condition,
            @RequestParam String action,
            @RequestParam(value = "description", required = false) String description) {
        log.info("添加告警规则：ruleName={}, ruleType={}, level={}, condition={}, action={}, description={}", ruleName, ruleType, level, condition, action, description);
        systemConfigService.addAlertRule(ruleName, ruleType, level, condition, action, description);
        return Result.success("添加成功");
    }

    /**
     * 更新告警规则配置
     */
    @PutMapping("/alert-rules/{ruleId}")
    @Operation(summary = "更新告警规则配置", description = "更新告警规则配置")
    public Result<?> updateAlertRule(
            @PathVariable Long ruleId,
            @RequestParam String ruleName,
            @RequestParam String ruleType,
            @RequestParam String level,
            @RequestParam String condition,
            @RequestParam String action,
            @RequestParam(value = "description", required = false) String description) {
        log.info("更新告警规则：ruleId={}, ruleName={}, ruleType={}, level={}, condition={}, action={}, description={}", ruleId, ruleName, ruleType, level, condition, action, description);
        systemConfigService.updateAlertRule(ruleId, ruleName, ruleType, level, condition, action, description);
        return Result.success("更新成功");
    }

    /**
     * 删除告警规则配置
     */
    @DeleteMapping("/alert-rules/{ruleId}")
    @Operation(summary = "删除告警规则配置", description = "删除告警规则配置")
    public Result<?> deleteAlertRule(@PathVariable Long ruleId) {
        log.info("删除告警规则：ruleId={}", ruleId);
        systemConfigService.deleteAlertRule(ruleId);
        return Result.success("删除成功");
    }

    /**
     * 启用/禁用告警规则
     */
    @PutMapping("/alert-rules/{ruleId}/enable")
    @Operation(summary = "启用/禁用告警规则", description = "启用或禁用告警规则")
    public Result<?> enableAlertRule(
            @PathVariable Long ruleId,
            @RequestParam boolean enabled) {
        log.info("{}告警规则：ruleId={}", enabled ? "启用" : "禁用", ruleId);
        systemConfigService.enableAlertRule(ruleId, enabled);
        return Result.success("操作成功");
    }

    /**
     * 获取系统日志配置
     */
    @GetMapping("/log-config")
    @Operation(summary = "获取系统日志配置", description = "获取系统日志配置")
    public Result<?> getLogConfig() {
        log.info("获取系统日志配置");
        return Result.success(systemConfigService.getLogConfig());
    }

    /**
     * 更新系统日志配置
     */
    @PutMapping("/log-config")
    @Operation(summary = "更新系统日志配置", description = "更新系统日志配置")
    public Result<?> updateLogConfig(@RequestBody Map<String, Object> config) {
        log.info("更新系统日志配置：{}", config);
        systemConfigService.updateLogConfig(config);
        return Result.success("更新成功");
    }

    /**
     * 系统重启
     */
    @PostMapping("/restart")
    @Operation(summary = "系统重启", description = "重启系统")
    public Result<?> restartSystem() {
        log.info("系统重启");
        systemConfigService.restartSystem();
        return Result.success("重启指令已发送");
    }

    /**
     * 系统关闭
     */
    @PostMapping("/shutdown")
    @Operation(summary = "系统关闭", description = "关闭系统")
    public Result<?> shutdownSystem() {
        log.info("系统关闭");
        systemConfigService.shutdownSystem();
        return Result.success("关闭指令已发送");
    }

    /**
     * 系统备份
     */
    @PostMapping("/backup")
    @Operation(summary = "系统备份", description = "备份系统数据")
    public Result<?> backupSystem(@RequestParam String backupName) {
        log.info("系统备份：backupName={}", backupName);
        String backupPath = systemConfigService.backupSystem(backupName);
        return Result.success(backupPath);
    }

    /**
     * 系统恢复
     */
    @PostMapping("/restore")
    @Operation(summary = "系统恢复", description = "从备份恢复系统数据")
    public Result<?> restoreSystem(@RequestParam String backupPath) {
        log.info("系统恢复：backupPath={}", backupPath);
        systemConfigService.restoreSystem(backupPath);
        return Result.success("恢复指令已发送");
    }

    /**
     * 获取系统备份列表
     */
    @GetMapping("/backups")
    @Operation(summary = "获取系统备份列表", description = "获取系统备份列表")
    public Result<?> getSystemBackups(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取系统备份列表：page={}, pageSize={}", page, pageSize);
        return Result.success(systemConfigService.getSystemBackups(page, pageSize));
    }

    /**
     * 删除系统备份
     */
    @DeleteMapping("/backups/{backupId}")
    @Operation(summary = "删除系统备份", description = "删除系统备份")
    public Result<?> deleteSystemBackup(@PathVariable Long backupId) {
        log.info("删除系统备份：backupId={}", backupId);
        systemConfigService.deleteSystemBackup(backupId);
        return Result.success("删除成功");
    }
}