package com.expressway.controller;

import com.expressway.dto.DeviceRegisterDTO;
import com.expressway.dto.DeviceStatusUpdateDTO;
import com.expressway.result.Result;
import com.expressway.service.DeviceService;
import com.expressway.vo.DeviceVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 设备管理控制器
 */
@RestController
@RequestMapping("/devices")
@Tag(name = "设备管理", description = "设备注册、查询、更新等接口")
@Slf4j
public class DeviceController {

    @Resource
    private DeviceService deviceService;

    /**
     * 获取设备列表
     */
    @GetMapping
    @Operation(summary = "获取设备列表", description = "获取所有设备列表，支持状态筛选和分页")
    public Result<?> getDeviceList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        log.info("获取设备列表：keyword={}, status={}, type={}, page={}, limit={}", keyword, status, type, page, limit);
        com.expressway.result.PageResult<?> result = deviceService.getDeviceList(keyword, status, type, page, limit);
        java.util.Map<String, Object> data = new java.util.HashMap<>();
        data.put("list", result.getRecords());
        data.put("total", result.getTotal());
        data.put("page", page);
        data.put("limit", limit);
        return Result.success(data);
    }

    /**
     * 获取设备详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取设备详情", description = "根据设备ID获取设备详细信息")
    public Result<?> getDeviceById(@PathVariable Long id) {
        log.info("获取设备详情：id={}", id);
        com.expressway.entity.Device device = deviceService.getDeviceById(id);
        java.util.Map<String, Object> data = new java.util.HashMap<>();
        data.put("id", device.getId());
        data.put("name", device.getName());
        data.put("type", device.getType());
        data.put("ip", device.getIp());
        data.put("port", device.getPort());
        data.put("status", device.getStatus() == 1 ? "online" : device.getStatus() == 2 ? "error" : "offline");
        data.put("location", device.getLocation());
        data.put("lastOnlineTime", device.getLastOnlineTime());
        data.put("createTime", device.getCreateTime());
        // 添加设备配置信息
        java.util.Map<String, Object> config = new java.util.HashMap<>();
        config.put("videoStreamUrl", "rtsp://" + device.getIp() + ":" + device.getPort() + "/stream1");
        config.put("detectionInterval", 1000);
        config.put("maxConnection", 5);
        data.put("config", config);
        return Result.success(data);
    }

    /**
     * 添建设备
     */
    @PostMapping
    @Operation(summary = "添建设备", description = "注册新设备")
    public Result<?> addDevice(@Validated @RequestBody DeviceRegisterDTO deviceRegisterDTO) {
        log.info("添建设备：{}", deviceRegisterDTO);
        com.expressway.entity.Device device = deviceService.registerDevice(deviceRegisterDTO);
        java.util.Map<String, Object> data = new java.util.HashMap<>();
        data.put("id", device.getId());
        data.put("name", device.getName());
        data.put("type", device.getType());
        data.put("ip", device.getIp());
        data.put("port", device.getPort());
        data.put("status", "offline");
        data.put("location", device.getLocation());
        data.put("createTime", device.getCreateTime());
        return Result.success(data);
    }

    /**
     * 更新设备
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新设备", description = "更新设备信息")
    public Result<?> updateDevice(@PathVariable Long id, @Validated @RequestBody DeviceRegisterDTO deviceRegisterDTO) {
        log.info("更新设备：id={}, {}", id, deviceRegisterDTO);
        com.expressway.entity.Device device = deviceService.updateDevice(id, deviceRegisterDTO);
        java.util.Map<String, Object> data = new java.util.HashMap<>();
        data.put("id", device.getId());
        data.put("name", device.getName());
        data.put("type", device.getType());
        data.put("ip", device.getIp());
        data.put("port", device.getPort());
        data.put("status", device.getStatus() == 1 ? "online" : device.getStatus() == 2 ? "error" : "offline");
        data.put("location", device.getLocation());
        data.put("lastOnlineTime", device.getLastOnlineTime());
        data.put("createTime", device.getCreateTime());
        return Result.success(data);
    }

    /**
     * 删除设备
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除设备", description = "删除指定设备")
    public Result<?> deleteDevice(@PathVariable Long id) {
        log.info("删除设备：id={}", id);
        deviceService.deleteDevice(id);
        return Result.success(null);
    }

    /**
     * 设备状态更新
     */
    @PostMapping("/{id}/status")
    @Operation(summary = "更新设备状态", description = "更新设备运行状态")
    public Result<?> updateDeviceStatus(@PathVariable Long id, @Validated @RequestBody DeviceStatusUpdateDTO deviceStatusUpdateDTO) {
        log.info("更新设备状态：id={}, {}", id, deviceStatusUpdateDTO);
        com.expressway.entity.Device device = deviceService.updateDeviceStatus(id, deviceStatusUpdateDTO);
        java.util.Map<String, Object> data = new java.util.HashMap<>();
        data.put("id", device.getId());
        data.put("status", device.getStatus() == 1 ? "online" : device.getStatus() == 2 ? "error" : "offline");
        data.put("lastOnlineTime", device.getLastOnlineTime());
        return Result.success(data);
    }

    /**
     * 连接设备
     */
    @PostMapping("/{id}/connect")
    @Operation(summary = "连接设备", description = "连接指定设备")
    public Result<?> connectDevice(@PathVariable Long id) {
        log.info("连接设备：id={}", id);
        deviceService.connectDevice(id);
        return Result.success("连接成功");
    }

    /**
     * 断开设备连接
     */
    @PostMapping("/{id}/disconnect")
    @Operation(summary = "断开设备连接", description = "断开指定设备的连接")
    public Result<?> disconnectDevice(@PathVariable Long id) {
        log.info("断开设备连接：id={}", id);
        deviceService.disconnectDevice(id);
        return Result.success("断开连接成功");
    }

    /**
     * 获取状态文本
     * @param status 状态值
     * @return 状态文本
     */
    private String getStatusText(Integer status) {
        if (status == null) {
            return "未知";
        }
        switch (status) {
            case 0:
                return "离线";
            case 1:
                return "在线";
            case 2:
                return "故障";
            case 3:
                return "维护中";
            default:
                return "未知";
        }
    }
}
