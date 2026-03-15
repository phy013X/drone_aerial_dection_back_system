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
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取设备列表：keyword={}, status={}, page={}, pageSize={}", keyword, status, page, pageSize);
        return Result.success(deviceService.getDeviceList(keyword, status, page, pageSize));
    }

    /**
     * 获取设备详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取设备详情", description = "根据设备ID获取设备详细信息")
    public Result<DeviceVO> getDeviceById(@PathVariable Long id) {
        log.info("获取设备详情：id={}", id);
        return Result.success(deviceService.getDeviceById(id));
    }

    /**
     * 添建设备
     */
    @PostMapping
    @Operation(summary = "添建设备", description = "注册新设备")
    public Result<?> addDevice(@Validated @RequestBody DeviceRegisterDTO deviceRegisterDTO) {
        log.info("添建设备：{}", deviceRegisterDTO);
        deviceService.registerDevice(deviceRegisterDTO);
        return Result.success("添加成功");
    }

    /**
     * 更新设备
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新设备", description = "更新设备信息")
    public Result<?> updateDevice(@PathVariable Long id, @Validated @RequestBody DeviceRegisterDTO deviceRegisterDTO) {
        log.info("更新设备：id={}, {}", id, deviceRegisterDTO);
        deviceService.updateDevice(id, deviceRegisterDTO);
        return Result.success("更新成功");
    }

    /**
     * 删除设备
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除设备", description = "删除指定设备")
    public Result<?> deleteDevice(@PathVariable Long id) {
        log.info("删除设备：id={}", id);
        deviceService.deleteDevice(id);
        return Result.success("删除成功");
    }

    /**
     * 设备状态更新
     */
    @PostMapping("/{id}/status")
    @Operation(summary = "更新设备状态", description = "更新设备运行状态")
    public Result<?> updateDeviceStatus(@PathVariable Long id, @Validated @RequestBody DeviceStatusUpdateDTO deviceStatusUpdateDTO) {
        log.info("更新设备状态：id={}, {}", id, deviceStatusUpdateDTO);
        deviceService.updateDeviceStatus(id, deviceStatusUpdateDTO);
        return Result.success("状态更新成功");
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
}
