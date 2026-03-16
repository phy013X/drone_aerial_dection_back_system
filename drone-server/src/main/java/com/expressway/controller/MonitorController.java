package com.expressway.controller;

import com.expressway.entity.Device;
import com.expressway.result.Result;
import com.expressway.service.DeviceService;
import com.expressway.service.DetectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 监控相关控制器
 */
@RestController
@RequestMapping("/monitor")
@Tag(name = "监控管理", description = "视频流、检测结果、监控状态相关接口")
@Slf4j
public class MonitorController {

    @Resource
    private DeviceService deviceService;

    @Resource
    private DetectionService detectionService;

    /**
     * 获取视频流
     */
    @GetMapping("/stream/{deviceId}")
    @Operation(summary = "获取视频流", description = "获取设备实时视频流")
    public Result<?> getVideoStream(@PathVariable Long deviceId) {
        log.info("获取视频流：deviceId={}", deviceId);
        try {
            com.expressway.entity.Device device = deviceService.getDeviceById(deviceId);
            if (device == null) {
                return Result.error("设备不存在");
            }
            if (device.getStatus() != 1) {
                return Result.error("设备未在线");
            }
            Map<String, Object> streamInfo = new HashMap<>();
            streamInfo.put("deviceId", deviceId);
            streamInfo.put("deviceName", device.getName());
            streamInfo.put("ip", device.getIp());
            streamInfo.put("port", device.getPort());
            streamInfo.put("connectionType", device.getConnectionType());
            streamInfo.put("streamUrl", "rtsp://" + device.getIp() + ":" + device.getPort() + "/stream");
            streamInfo.put("status", "online");
            return Result.success(streamInfo);
        } catch (Exception e) {
            log.error("获取视频流失败", e);
            return Result.error("获取视频流失败：" + e.getMessage());
        }
    }

    /**
     * 获取检测结果
     */
    @GetMapping("/detection/{deviceId}")
    @Operation(summary = "获取检测结果", description = "获取设备实时检测结果")
    public Result<?> getDetectionResult(@PathVariable Long deviceId) {
        log.info("获取检测结果：deviceId={}", deviceId);
        try {
            com.expressway.entity.Device device = deviceService.getDeviceById(deviceId);
            if (device == null) {
                return Result.error("设备不存在");
            }
            if (device.getStatus() != 1) {
                return Result.error("设备未在线");
            }
            Map<String, Object> detectionInfo = new HashMap<>();
            detectionInfo.put("deviceId", deviceId);
            detectionInfo.put("deviceName", device.getName());
            detectionInfo.put("status", "online");
            detectionInfo.put("lastDetectionTime", new java.util.Date());
            detectionInfo.put("detectionCount", 0);
            detectionInfo.put("detections", new java.util.ArrayList<>());
            return Result.success(detectionInfo);
        } catch (Exception e) {
            log.error("获取检测结果失败", e);
            return Result.error("获取检测结果失败：" + e.getMessage());
        }
    }

    /**
     * 开始监控
     */
    @PostMapping("/start/{deviceId}")
    @Operation(summary = "开始监控", description = "开始监控指定设备")
    public Result<?> startMonitor(@PathVariable Long deviceId) {
        log.info("开始监控：deviceId={}", deviceId);
        try {
            com.expressway.entity.Device device = deviceService.getDeviceById(deviceId);
            if (device == null) {
                return Result.error("设备不存在");
            }
            if (device.getStatus() != 1) {
                return Result.error("设备未在线");
            }
            boolean connected = deviceService.connectDevice(deviceId);
            if (connected) {
                Map<String, Object> result = new HashMap<>();
                result.put("deviceId", deviceId);
                result.put("status", "monitoring");
                result.put("startTime", new java.util.Date());
                return Result.success(result);
            } else {
                return Result.error("开始监控失败");
            }
        } catch (Exception e) {
            log.error("开始监控失败", e);
            return Result.error("开始监控失败：" + e.getMessage());
        }
    }

    /**
     * 停止监控
     */
    @PostMapping("/stop/{deviceId}")
    @Operation(summary = "停止监控", description = "停止监控指定设备")
    public Result<?> stopMonitor(@PathVariable Long deviceId) {
        log.info("停止监控：deviceId={}", deviceId);
        try {
            com.expressway.entity.Device device = deviceService.getDeviceById(deviceId);
            if (device == null) {
                return Result.error("设备不存在");
            }
            boolean disconnected = deviceService.disconnectDevice(deviceId);
            if (disconnected) {
                Map<String, Object> result = new HashMap<>();
                result.put("deviceId", deviceId);
                result.put("status", "stopped");
                result.put("stopTime", new java.util.Date());
                return Result.success(result);
            } else {
                return Result.error("停止监控失败");
            }
        } catch (Exception e) {
            log.error("停止监控失败", e);
            return Result.error("停止监控失败：" + e.getMessage());
        }
    }

    /**
     * 获取监控状态
     */
    @GetMapping("/status/{deviceId}")
    @Operation(summary = "获取监控状态", description = "获取设备监控状态")
    public Result<?> getMonitorStatus(@PathVariable Long deviceId) {
        log.info("获取监控状态：deviceId={}", deviceId);
        try {
            com.expressway.entity.Device device = deviceService.getDeviceById(deviceId);
            if (device == null) {
                return Result.error("设备不存在");
            }
            Map<String, Object> status = new HashMap<>();
            status.put("deviceId", deviceId);
            status.put("deviceName", device.getName());
            status.put("deviceStatus", device.getStatus());
            status.put("monitorStatus", device.getStatus() == 1 ? "monitoring" : "stopped");
            status.put("lastOnlineTime", device.getLastOnlineTime());
            status.put("ip", device.getIp());
            status.put("port", device.getPort());
            status.put("connectionType", device.getConnectionType());
            status.put("latitude", device.getLatitude());
            status.put("longitude", device.getLongitude());
            status.put("location", device.getLocation());
            return Result.success(status);
        } catch (Exception e) {
            log.error("获取监控状态失败", e);
            return Result.error("获取监控状态失败：" + e.getMessage());
        }
    }

    /**
     * 获取快照
     */
    @GetMapping("/snapshot/{deviceId}")
    @Operation(summary = "获取快照", description = "获取设备当前快照")
    public Result<?> getSnapshot(@PathVariable Long deviceId) {
        log.info("获取快照：deviceId={}", deviceId);
        try {
            com.expressway.entity.Device device = deviceService.getDeviceById(deviceId);
            if (device == null) {
                return Result.error("设备不存在");
            }
            if (device.getStatus() != 1) {
                return Result.error("设备未在线");
            }
            Map<String, Object> snapshot = new HashMap<>();
            snapshot.put("deviceId", deviceId);
            snapshot.put("deviceName", device.getName());
            snapshot.put("snapshotTime", new java.util.Date());
            snapshot.put("snapshotUrl", "/images/snapshot_" + deviceId + "_" + System.currentTimeMillis() + ".jpg");
            snapshot.put("latitude", device.getLatitude());
            snapshot.put("longitude", device.getLongitude());
            snapshot.put("location", device.getLocation());
            return Result.success(snapshot);
        } catch (Exception e) {
            log.error("获取快照失败", e);
            return Result.error("获取快照失败：" + e.getMessage());
        }
    }
}