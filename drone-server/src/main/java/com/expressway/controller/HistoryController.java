package com.expressway.controller;

import com.expressway.result.PageResult;
import com.expressway.result.Result;
import com.expressway.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 历史记录相关控制器
 */
@RestController
@RequestMapping("/history")
@Tag(name = "历史记录管理", description = "检测历史、告警历史、视频记录相关接口")
@Slf4j
public class HistoryController {

    @Resource
    private HistoryService historyService;

    /**
     * 获取检测历史
     */
    @GetMapping("/detection")
    @Operation(summary = "获取检测历史", description = "获取检测历史记录")
    public Result<?> getDetectionHistory(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取检测历史：deviceId={}, type={}, startDate={}, endDate={}, page={}, pageSize={}", 
                deviceId, type, startDate, endDate, page, pageSize);
        PageResult<Map<String, Object>> detectionHistory = historyService.getDetectionHistory(deviceId, type, startDate, endDate, page, pageSize);
        return Result.success(detectionHistory);
    }

    /**
     * 获取检测详情
     */
    @GetMapping("/detection/{id}")
    @Operation(summary = "获取检测详情", description = "获取检测历史详情")
    public Result<?> getDetectionDetail(@PathVariable Long id) {
        log.info("获取检测详情：id={}", id);
        Map<String, Object> detail = historyService.getDetectionDetail(id);
        return Result.success(detail);
    }

    /**
     * 导出检测历史
     */
    @GetMapping("/detection/export")
    @Operation(summary = "导出检测历史", description = "导出检测历史记录")
    public Result<?> exportDetectionHistory(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("导出检测历史：deviceId={}, type={}, startDate={}, endDate={}", 
                deviceId, type, startDate, endDate);
        Map<String, Object> result = historyService.exportDetectionHistory(deviceId, type, startDate, endDate);
        return Result.success(result);
    }

    /**
     * 获取告警历史
     */
    @GetMapping("/alert")
    @Operation(summary = "获取告警历史", description = "获取告警历史记录")
    public Result<?> getAlertHistory(
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取告警历史：level={}, status={}, startDate={}, endDate={}, page={}, pageSize={}", 
                level, status, startDate, endDate, page, pageSize);
        PageResult<Map<String, Object>> alertHistory = historyService.getAlertHistory(level, status, startDate, endDate, page, pageSize);
        return Result.success(alertHistory);
    }

    /**
     * 获取告警详情
     */
    @GetMapping("/alert/{id}")
    @Operation(summary = "获取告警详情", description = "获取告警历史详情")
    public Result<?> getAlertDetail(@PathVariable Long id) {
        log.info("获取告警详情：id={}", id);
        Map<String, Object> detail = historyService.getAlertDetail(id);
        return Result.success(detail);
    }

    /**
     * 处理告警
     */
    @PostMapping("/alert/{id}/process")
    @Operation(summary = "处理告警", description = "处理告警记录")
    public Result<?> processAlert(@PathVariable Long id, @RequestBody Map<String, String> processData) {
        log.info("处理告警：id={}, data={}", id, processData);
        String processNote = processData.get("processNote");
        Map<String, Object> result = historyService.processAlert(id, processNote);
        return Result.success(result);
    }

    /**
     * 导出告警历史
     */
    @GetMapping("/alert/export")
    @Operation(summary = "导出告警历史", description = "导出告警历史记录")
    public Result<?> exportAlertHistory(
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("导出告警历史：level={}, status={}, startDate={}, endDate={}", 
                level, status, startDate, endDate);
        Map<String, Object> result = historyService.exportAlertHistory(level, status, startDate, endDate);
        return Result.success(result);
    }

    /**
     * 获取视频记录
     */
    @GetMapping("/video")
    @Operation(summary = "获取视频记录", description = "获取视频记录列表")
    public Result<?> getVideoHistory(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取视频记录：deviceId={}, date={}, page={}, pageSize={}", deviceId, date, page, pageSize);
        PageResult<Map<String, Object>> videoHistory = historyService.getVideoHistory(deviceId, date, page, pageSize);
        return Result.success(videoHistory);
    }

    /**
     * 获取视频详情
     */
    @GetMapping("/video/{id}")
    @Operation(summary = "获取视频详情", description = "获取视频记录详情")
    public Result<?> getVideoDetail(@PathVariable Long id) {
        log.info("获取视频详情：id={}", id);
        Map<String, Object> detail = historyService.getVideoDetail(id);
        return Result.success(detail);
    }

    /**
     * 下载视频
     */
    @GetMapping("/video/{id}/download")
    @Operation(summary = "下载视频", description = "下载指定视频")
    public Result<?> downloadVideo(@PathVariable Long id) {
        log.info("下载视频：id={}", id);
        Map<String, Object> result = historyService.downloadVideo(id);
        return Result.success(result);
    }

    /**
     * 删除视频记录
     */
    @DeleteMapping("/video/{id}")
    @Operation(summary = "删除视频记录", description = "删除指定视频记录")
    public Result<?> deleteVideo(@PathVariable Long id) {
        log.info("删除视频记录：id={}", id);
        Map<String, Object> result = historyService.deleteVideo(id);
        return Result.success(result);
    }
}
