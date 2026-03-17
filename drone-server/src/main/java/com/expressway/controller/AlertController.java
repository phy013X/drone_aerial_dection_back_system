package com.expressway.controller;

import com.expressway.result.Result;
import com.expressway.service.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 告警控制器
 */
@RestController
@RequestMapping("/alerts")
@Tag(name = "告警管理", description = "告警查询、处理等接口")
@Slf4j
public class AlertController {

    @Resource
    private AlertService alertService;

    /**
     * 获取告警列表
     */
    @GetMapping
    @Operation(summary = "获取告警列表", description = "获取告警列表，支持级别和状态筛选")
    public Result<?> getAlertList(
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取告警列表：level={}, status={}, startDate={}, endDate={}, page={}, pageSize={}", level, status, startDate, endDate, page, pageSize);
        return Result.success(alertService.getAlertList(level, status, startDate, endDate, page, pageSize));
    }

    /**
     * 获取告警详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取告警详情", description = "根据告警ID获取告警详细信息")
    public Result<?> getAlertById(@PathVariable Long id) {
        log.info("获取告警详情：id={}", id);
        return Result.success(alertService.getAlertById(id));
    }

    /**
     * 处理告警
     */
    @PutMapping("/{id}/process")
    @Operation(summary = "处理告警", description = "处理指定告警")
    public Result<?> processAlert(@PathVariable Long id, @RequestParam(value = "processNote", required = false) String processNote) {
        log.info("处理告警：id={}, processNote={}", id, processNote);
        alertService.processAlert(id, processNote);
        // 返回更新后的告警信息
        Object updatedAlert = alertService.getAlertById(id);
        return Result.success(updatedAlert);
    }

    /**
     * 关闭告警
     */
    @PutMapping("/{id}/close")
    @Operation(summary = "关闭告警", description = "关闭指定告警")
    public Result<?> closeAlert(@PathVariable Long id) {
        log.info("关闭告警：id={}", id);
        alertService.closeAlert(id);
        // 返回更新后的告警信息
        Object updatedAlert = alertService.getAlertById(id);
        return Result.success(updatedAlert);
    }
}
