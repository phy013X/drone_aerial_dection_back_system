package com.expressway.controller;

import com.expressway.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 监控相关控制器
 */
@RestController
@RequestMapping("/monitor")
@Tag(name = "监控管理", description = "视频流、检测结果、监控状态相关接口")
@Slf4j
public class MonitorController {

    /**
     * 获取视频流
     */
    @GetMapping("/stream/{deviceId}")
    @Operation(summary = "获取视频流", description = "获取设备实时视频流")
    public Result<?> getVideoStream(@PathVariable Long deviceId) {
        log.info("获取视频流：deviceId={}", deviceId);
        // 实现获取视频流逻辑
        return Result.success("视频流数据");
    }

    /**
     * 获取检测结果
     */
    @GetMapping("/detection/{deviceId}")
    @Operation(summary = "获取检测结果", description = "获取设备实时检测结果")
    public Result<?> getDetectionResult(@PathVariable Long deviceId) {
        log.info("获取检测结果：deviceId={}", deviceId);
        // 实现获取检测结果逻辑
        return Result.success("检测结果数据");
    }

    /**
     * 开始监控
     */
    @PostMapping("/start/{deviceId}")
    @Operation(summary = "开始监控", description = "开始监控指定设备")
    public Result<?> startMonitor(@PathVariable Long deviceId) {
        log.info("开始监控：deviceId={}", deviceId);
        // 实现开始监控逻辑
        return Result.success("开始监控成功");
    }

    /**
     * 停止监控
     */
    @PostMapping("/stop/{deviceId}")
    @Operation(summary = "停止监控", description = "停止监控指定设备")
    public Result<?> stopMonitor(@PathVariable Long deviceId) {
        log.info("停止监控：deviceId={}", deviceId);
        // 实现停止监控逻辑
        return Result.success("停止监控成功");
    }

    /**
     * 获取监控状态
     */
    @GetMapping("/status/{deviceId}")
    @Operation(summary = "获取监控状态", description = "获取设备监控状态")
    public Result<?> getMonitorStatus(@PathVariable Long deviceId) {
        log.info("获取监控状态：deviceId={}", deviceId);
        // 实现获取监控状态逻辑
        return Result.success("监控状态数据");
    }

    /**
     * 获取快照
     */
    @GetMapping("/snapshot/{deviceId}")
    @Operation(summary = "获取快照", description = "获取设备当前快照")
    public Result<?> getSnapshot(@PathVariable Long deviceId) {
        log.info("获取快照：deviceId={}", deviceId);
        // 实现获取快照逻辑
        return Result.success("快照数据");
    }
}