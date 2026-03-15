package com.expressway.controller;

import com.expressway.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统管理控制器
 */
@RestController
@RequestMapping("/system")
@Tag(name = "系统管理", description = "系统状态、配置、日志等接口")
@Slf4j
public class SystemController {

    /**
     * 获取系统状态
     */
    @GetMapping("/status")
    @Operation(summary = "获取系统状态", description = "获取系统运行状态")
    public Result<?> getSystemStatus() {
        log.info("获取系统状态");
        // 模拟系统状态数据
        Map<String, Object> status = new HashMap<>();
        status.put("serverStatus", "running");
        status.put("aiServiceStatus", "running");
        status.put("databaseStatus", "connected");
        status.put("mqStatus", "connected");
        status.put("uptime", "24h 30m");
        status.put("cpuUsage", "25%");
        status.put("memoryUsage", "40%");
        status.put("diskUsage", "60%");
        status.put("activeDevices", 5);
        status.put("pendingAlerts", 2);
        return Result.success(status);
    }

    /**
     * 获取系统配置
     */
    @GetMapping("/config")
    @Operation(summary = "获取系统配置", description = "获取系统配置信息")
    public Result<?> getSystemConfig() {
        log.info("获取系统配置");
        // 模拟系统配置数据
        Map<String, Object> config = new HashMap<>();
        config.put("aiModel", "yolov8n");
        config.put("detectionThreshold", 0.5);
        config.put("maxDevices", 10);
        config.put("imageResolution", "1920x1080");
        
        Map<String, Object> alertLevels = new HashMap<>();
        alertLevels.put("high", new String[]{"intrusion", "device_crash"});
        alertLevels.put("medium", new String[]{"device_offline", "low_battery"});
        alertLevels.put("low", new String[]{"signal_loss"});
        config.put("alertLevels", alertLevels);
        
        Map<String, Object> storageSettings = new HashMap<>();
        storageSettings.put("imageRetention", 30);
        storageSettings.put("videoRetention", 7);
        storageSettings.put("maxStorage", "100GB");
        config.put("storageSettings", storageSettings);
        
        return Result.success(config);
    }

    /**
     * 更新系统配置
     */
    @PutMapping("/config")
    @Operation(summary = "更新系统配置", description = "更新系统配置信息")
    public Result<?> updateSystemConfig(@RequestBody Map<String, Object> config) {
        log.info("更新系统配置：{}", config);
        // 这里可以添加更新系统配置的逻辑
        return Result.success("配置更新成功");
    }

    /**
     * 获取系统日志
     */
    @GetMapping("/logs")
    @Operation(summary = "获取系统日志", description = "获取系统运行日志")
    public Result<?> getSystemLogs(
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取系统日志：level={}, startDate={}, endDate={}, page={}, pageSize={}", 
                level, startDate, endDate, page, pageSize);
        // 模拟系统日志数据
        Map<String, Object> result = new HashMap<>();
        result.put("total", 100);
        result.put("pages", 10);
        result.put("list", new java.util.ArrayList<>());
        return Result.success(result);
    }
}