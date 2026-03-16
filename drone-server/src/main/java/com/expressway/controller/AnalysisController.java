package com.expressway.controller;

import com.expressway.result.Result;
import com.expressway.service.AnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 数据分析控制器
 */
@RestController
@RequestMapping("/analysis")
@Tag(name = "数据分析", description = "数据分析与统计相关接口")
@Slf4j
public class AnalysisController {

    @Resource
    private AnalysisService analysisService;

    /**
     * 检测数量统计
     */
    @GetMapping("/detection/count")
    @Operation(summary = "检测数量统计", description = "统计检测数量")
    public Result<?> getDetectionCountStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "type", required = false) String type) {
        log.info("检测数量统计：deviceId={}, startDate={}, endDate={}, type={}", deviceId, startDate, endDate, type);
        return Result.success(analysisService.getDetectionCountStats(deviceId, startDate, endDate, type));
    }

    /**
     * 检测趋势分析
     */
    @GetMapping("/detection/trend")
    @Operation(summary = "检测趋势分析", description = "分析检测趋势")
    public Result<?> getDetectionTrendStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "interval", defaultValue = "day") String interval) {
        log.info("检测趋势分析：deviceId={}, startDate={}, endDate={}, interval={}", deviceId, startDate, endDate, interval);
        return Result.success(analysisService.getDetectionTrendStats(deviceId, startDate, endDate, interval));
    }

    /**
     * 检测热点分析
     */
    @GetMapping("/detection/hotspot")
    @Operation(summary = "检测热点分析", description = "分析检测热点")
    public Result<?> getDetectionHotspotStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "type", required = false) String type) {
        log.info("检测热点分析：deviceId={}, startDate={}, endDate={}, type={}", deviceId, startDate, endDate, type);
        return Result.success(analysisService.getDetectionHotspotStats(deviceId, startDate, endDate, type));
    }

    /**
     * 检测类型分布
     */
    @GetMapping("/detection/type-distribution")
    @Operation(summary = "检测类型分布", description = "分析检测类型分布")
    public Result<?> getDetectionTypeDistribution(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("检测类型分布：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getDetectionTypeDistribution(deviceId, startDate, endDate));
    }

    /**
     * 检测时间分布
     */
    @GetMapping("/detection/time-distribution")
    @Operation(summary = "检测时间分布", description = "分析检测时间分布")
    public Result<?> getDetectionTimeDistribution(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("检测时间分布：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getDetectionTimeDistribution(deviceId, startDate, endDate));
    }

    /**
     * 告警数量统计
     */
    @GetMapping("/alert/count")
    @Operation(summary = "告警数量统计", description = "统计告警数量")
    public Result<?> getAlertCountStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "type", required = false) String type) {
        log.info("告警数量统计：deviceId={}, startDate={}, endDate={}, level={}, type={}", deviceId, startDate, endDate, level, type);
        return Result.success(analysisService.getAlertCountStats(deviceId, startDate, endDate, level, type));
    }

    /**
     * 告警趋势分析
     */
    @GetMapping("/alert/trend")
    @Operation(summary = "告警趋势分析", description = "分析告警趋势")
    public Result<?> getAlertTrendStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "interval", defaultValue = "day") String interval) {
        log.info("告警趋势分析：deviceId={}, startDate={}, endDate={}, interval={}", deviceId, startDate, endDate, interval);
        return Result.success(analysisService.getAlertTrendStats(deviceId, startDate, endDate, interval));
    }

    /**
     * 告警处理率统计
     */
    @GetMapping("/alert/processing-rate")
    @Operation(summary = "告警处理率统计", description = "统计告警处理率")
    public Result<?> getAlertProcessingRateStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("告警处理率统计：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getAlertProcessingRateStats(deviceId, startDate, endDate));
    }

    /**
     * 告警响应时间统计
     */
    @GetMapping("/alert/response-time")
    @Operation(summary = "告警响应时间统计", description = "统计告警响应时间")
    public Result<?> getAlertResponseTimeStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("告警响应时间统计：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getAlertResponseTimeStats(deviceId, startDate, endDate));
    }

    /**
     * 告警类型分布
     */
    @GetMapping("/alert/type-distribution")
    @Operation(summary = "告警类型分布", description = "分析告警类型分布")
    public Result<?> getAlertTypeDistribution(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("告警类型分布：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getAlertTypeDistribution(deviceId, startDate, endDate));
    }

    /**
     * 告警级别分布
     */
    @GetMapping("/alert/level-distribution")
    @Operation(summary = "告警级别分布", description = "分析告警级别分布")
    public Result<?> getAlertLevelDistribution(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("告警级别分布：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getAlertLevelDistribution(deviceId, startDate, endDate));
    }

    /**
     * 设备在线率统计
     */
    @GetMapping("/device/online-rate")
    @Operation(summary = "设备在线率统计", description = "统计设备在线率")
    public Result<?> getDeviceOnlineRateStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("设备在线率统计：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getDeviceOnlineRateStats(deviceId, startDate, endDate));
    }

    /**
     * 设备故障率统计
     */
    @GetMapping("/device/failure-rate")
    @Operation(summary = "设备故障率统计", description = "统计设备故障率")
    public Result<?> getDeviceFailureRateStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("设备故障率统计：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getDeviceFailureRateStats(deviceId, startDate, endDate));
    }

    /**
     * 设备使用时长统计
     */
    @GetMapping("/device/usage")
    @Operation(summary = "设备使用时长统计", description = "统计设备使用时长")
    public Result<?> getDeviceUsageStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("设备使用时长统计：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getDeviceUsageStats(deviceId, startDate, endDate));
    }

    /**
     * 设备电量消耗统计
     */
    @GetMapping("/device/battery")
    @Operation(summary = "设备电量消耗统计", description = "统计设备电量消耗")
    public Result<?> getDeviceBatteryStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("设备电量消耗统计：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getDeviceBatteryStats(deviceId, startDate, endDate));
    }

    /**
     * 设备信号强度统计
     */
    @GetMapping("/device/signal")
    @Operation(summary = "设备信号强度统计", description = "统计设备信号强度")
    public Result<?> getDeviceSignalStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("设备信号强度统计：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getDeviceSignalStats(deviceId, startDate, endDate));
    }

    /**
     * 设备飞行轨迹分析
     */
    @GetMapping("/device/flight-path")
    @Operation(summary = "设备飞行轨迹分析", description = "分析设备飞行轨迹")
    public Result<?> getDeviceFlightPathStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("设备飞行轨迹分析：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getDeviceFlightPathStats(deviceId, startDate, endDate));
    }

    /**
     * 设备维护成本统计
     */
    @GetMapping("/device/maintenance-cost")
    @Operation(summary = "设备维护成本统计", description = "统计设备维护成本")
    public Result<?> getDeviceMaintenanceCostStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("设备维护成本统计：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getDeviceMaintenanceCostStats(deviceId, startDate, endDate));
    }

    /**
     * 生成自定义统计报表
     */
    @PostMapping("/custom-report")
    @Operation(summary = "生成自定义统计报表", description = "生成自定义统计报表")
    public Result<?> generateCustomReport(@RequestBody Map<String, Object> params) {
        log.info("生成自定义统计报表：{}", params);
        return Result.success(analysisService.generateCustomReport(params));
    }

    /**
     * 导出统计报表
     */
    @PostMapping("/export")
    @Operation(summary = "导出统计报表", description = "导出统计报表")
    public Result<?> exportReport(
            @RequestParam("reportType") String reportType,
            @RequestBody Map<String, Object> params) {
        log.info("导出统计报表：reportType={}, {}", reportType, params);
        String exportId = analysisService.exportReport(reportType, params);
        return Result.success(exportId);
    }

    /**
     * 获取导出进度
     */
    @GetMapping("/export/{exportId}/progress")
    @Operation(summary = "获取导出进度", description = "获取导出进度")
    public Result<?> getExportProgress(@PathVariable String exportId) {
        log.info("获取导出进度：exportId={}", exportId);
        int progress = analysisService.getExportProgress(exportId);
        return Result.success(progress);
    }

    /**
     * 下载导出文件
     */
    @GetMapping("/export/{exportId}/download")
    @Operation(summary = "下载导出文件", description = "下载导出文件")
    public Result<?> downloadExportFile(@PathVariable String exportId) {
        log.info("下载导出文件：exportId={}", exportId);
        String downloadUrl = analysisService.getExportDownloadUrl(exportId);
        return Result.success(downloadUrl);
    }

    /**
     * 设备统计信息
     */
    @GetMapping("/device/stats")
    @Operation(summary = "设备统计信息", description = "获取设备统计信息")
    public Result<?> getDeviceStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("设备统计信息：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getDeviceStats(deviceId, startDate, endDate));
    }

    /**
     * 检测统计信息
     */
    @GetMapping("/detection/stats")
    @Operation(summary = "检测统计信息", description = "获取检测统计信息")
    public Result<?> getDetectionStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("检测统计信息：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getDetectionStats(deviceId, startDate, endDate));
    }

    /**
     * 告警统计信息
     */
    @GetMapping("/alert/stats")
    @Operation(summary = "告警统计信息", description = "获取告警统计信息")
    public Result<?> getAlertStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("告警统计信息：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getAlertStats(deviceId, startDate, endDate));
    }

    /**
     * 设备排名
     */
    @GetMapping("/device/ranking")
    @Operation(summary = "设备排名", description = "获取设备排名")
    public Result<?> getDeviceRanking(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("设备排名：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getDeviceRanking(deviceId, startDate, endDate));
    }

    /**
     * 检测类型
     */
    @GetMapping("/detection/types")
    @Operation(summary = "检测类型", description = "获取检测类型")
    public Result<?> getDetectionTypes(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("检测类型：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getDetectionTypes(deviceId, startDate, endDate));
    }

    /**
     * 检测时间统计
     */
    @GetMapping("/detection/time")
    @Operation(summary = "检测时间统计", description = "获取检测时间统计")
    public Result<?> getDetectionTimeStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("检测时间统计：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getDetectionTimeStats(deviceId, startDate, endDate));
    }

    /**
     * 告警处理统计
     */
    @GetMapping("/alert/process")
    @Operation(summary = "告警处理统计", description = "获取告警处理统计")
    public Result<?> getAlertProcessStats(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("告警处理统计：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getAlertProcessStats(deviceId, startDate, endDate));
    }

    /**
     * 告警类型统计
     */
    @GetMapping("/alert/types")
    @Operation(summary = "告警类型统计", description = "获取告警类型统计")
    public Result<?> getAlertTypes(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        log.info("告警类型统计：deviceId={}, startDate={}, endDate={}", deviceId, startDate, endDate);
        return Result.success(analysisService.getAlertTypes(deviceId, startDate, endDate));
    }
}