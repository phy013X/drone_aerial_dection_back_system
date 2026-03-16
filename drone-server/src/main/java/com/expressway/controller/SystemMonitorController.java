package com.expressway.controller;

import com.expressway.result.Result;
import com.expressway.service.SystemMonitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统监控控制器
 */
@RestController
@RequestMapping("/monitor")
@Tag(name = "系统监控", description = "系统监控与管理相关接口")
@Slf4j
public class SystemMonitorController {

    @Resource
    private SystemMonitorService systemMonitorService;

    /**
     * 获取系统运行状态
     */
    @GetMapping("/system/status")
    @Operation(summary = "获取系统运行状态", description = "获取系统运行状态")
    public Result<?> getSystemStatus() {
        log.info("获取系统运行状态");
        return Result.success(systemMonitorService.getSystemStatus());
    }

    /**
     * 获取系统资源使用情况
     */
    @GetMapping("/system/resources")
    @Operation(summary = "获取系统资源使用情况", description = "获取系统资源使用情况")
    public Result<?> getSystemResources() {
        log.info("获取系统资源使用情况");
        return Result.success(systemMonitorService.getSystemResources());
    }

    /**
     * 获取系统运行统计
     */
    @GetMapping("/system/stats")
    @Operation(summary = "获取系统运行统计", description = "获取系统运行统计")
    public Result<?> getSystemStats() {
        log.info("获取系统运行统计");
        return Result.success(systemMonitorService.getSystemStats());
    }

    /**
     * 获取设备状态列表
     */
    @GetMapping("/devices")
    @Operation(summary = "获取设备状态列表", description = "获取设备状态列表")
    public Result<?> getDeviceStatusList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取设备状态列表：page={}, pageSize={}", page, pageSize);
        return Result.success(systemMonitorService.getDeviceStatusList(page, pageSize));
    }

    /**
     * 获取设备详细状态
     */
    @GetMapping("/devices/{deviceId}")
    @Operation(summary = "获取设备详细状态", description = "获取设备详细状态")
    public Result<?> getDeviceStatusDetail(@PathVariable Long deviceId) {
        log.info("获取设备详细状态：deviceId={}", deviceId);
        return Result.success(systemMonitorService.getDeviceStatusDetail(deviceId));
    }

    /**
     * 获取任务执行状态列表
     */
    @GetMapping("/tasks")
    @Operation(summary = "获取任务执行状态列表", description = "获取任务执行状态列表")
    public Result<?> getTaskStatusList(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取任务执行状态列表：type={}, status={}, page={}, pageSize={}", type, status, page, pageSize);
        return Result.success(systemMonitorService.getTaskStatusList(type, status, page, pageSize));
    }

    /**
     * 获取任务详细状态
     */
    @GetMapping("/tasks/{taskId}")
    @Operation(summary = "获取任务详细状态", description = "获取任务详细状态")
    public Result<?> getTaskStatusDetail(@PathVariable Long taskId) {
        log.info("获取任务详细状态：taskId={}", taskId);
        return Result.success(systemMonitorService.getTaskStatusDetail(taskId));
    }

    /**
     * 获取系统异常列表
     */
    @GetMapping("/exceptions")
    @Operation(summary = "获取系统异常列表", description = "获取系统异常列表")
    public Result<?> getSystemExceptionList(
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取系统异常列表：level={}, type={}, page={}, pageSize={}", level, type, page, pageSize);
        return Result.success(systemMonitorService.getSystemExceptionList(level, type, page, pageSize));
    }

    /**
     * 获取异常详细信息
     */
    @GetMapping("/exceptions/{exceptionId}")
    @Operation(summary = "获取异常详细信息", description = "获取异常详细信息")
    public Result<?> getSystemExceptionDetail(@PathVariable Long exceptionId) {
        log.info("获取异常详细信息：exceptionId={}", exceptionId);
        return Result.success(systemMonitorService.getSystemExceptionDetail(exceptionId));
    }

    /**
     * 处理系统异常
     */
    @PutMapping("/exceptions/{exceptionId}/handle")
    @Operation(summary = "处理系统异常", description = "处理系统异常")
    public Result<?> handleSystemException(
            @PathVariable Long exceptionId,
            @RequestParam String handler,
            @RequestParam String solution) {
        log.info("处理系统异常：exceptionId={}, handler={}, solution={}", exceptionId, handler, solution);
        systemMonitorService.handleSystemException(exceptionId, handler, solution);
        return Result.success("处理成功");
    }

    /**
     * 获取系统日志列表
     */
    @GetMapping("/logs")
    @Operation(summary = "获取系统日志列表", description = "获取系统日志列表")
    public Result<?> getSystemLogList(
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取系统日志列表：level={}, type={}, keyword={}, page={}, pageSize={}", level, type, keyword, page, pageSize);
        return Result.success(systemMonitorService.getSystemLogList(level, type, keyword, page, pageSize));
    }

    /**
     * 获取系统预警列表
     */
    @GetMapping("/alerts")
    @Operation(summary = "获取系统预警列表", description = "获取系统预警列表")
    public Result<?> getSystemAlertList(
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取系统预警列表：level={}, type={}, page={}, pageSize={}", level, type, page, pageSize);
        return Result.success(systemMonitorService.getSystemAlertList(level, type, page, pageSize));
    }

    /**
     * 处理系统预警
     */
    @PutMapping("/alerts/{alertId}/handle")
    @Operation(summary = "处理系统预警", description = "处理系统预警")
    public Result<?> handleSystemAlert(
            @PathVariable Long alertId,
            @RequestParam String handler,
            @RequestParam String solution) {
        log.info("处理系统预警：alertId={}, handler={}, solution={}", alertId, handler, solution);
        systemMonitorService.handleSystemAlert(alertId, handler, solution);
        return Result.success("处理成功");
    }

    /**
     * 获取系统性能指标
     */
    @GetMapping("/metrics")
    @Operation(summary = "获取系统性能指标", description = "获取系统性能指标")
    public Result<?> getSystemMetrics(
            @RequestParam("metric") String metric,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "interval", defaultValue = "hour") String interval) {
        log.info("获取系统性能指标：metric={}, startDate={}, endDate={}, interval={}", metric, startDate, endDate, interval);
        return Result.success(systemMonitorService.getSystemMetrics(metric, startDate, endDate, interval));
    }

    /**
     * 获取系统健康检查结果
     */
    @GetMapping("/health")
    @Operation(summary = "获取系统健康检查结果", description = "获取系统健康检查结果")
    public Result<?> getSystemHealthCheck() {
        log.info("获取系统健康检查结果");
        return Result.success(systemMonitorService.getSystemHealthCheck());
    }

    /**
     * 手动触发系统健康检查
     */
    @PostMapping("/health/check")
    @Operation(summary = "手动触发系统健康检查", description = "手动触发系统健康检查")
    public Result<?> triggerHealthCheck() {
        log.info("手动触发系统健康检查");
        return Result.success(systemMonitorService.triggerHealthCheck());
    }

    /**
     * 获取系统监控配置
     */
    @GetMapping("/config")
    @Operation(summary = "获取系统监控配置", description = "获取系统监控配置")
    public Result<?> getMonitorConfig() {
        log.info("获取系统监控配置");
        return Result.success(systemMonitorService.getMonitorConfig());
    }

    /**
     * 更新系统监控配置
     */
    @PutMapping("/config")
    @Operation(summary = "更新系统监控配置", description = "更新系统监控配置")
    public Result<?> updateMonitorConfig(@RequestBody Map<String, Object> config) {
        log.info("更新系统监控配置：{}", config);
        systemMonitorService.updateMonitorConfig(config);
        return Result.success("更新成功");
    }
}