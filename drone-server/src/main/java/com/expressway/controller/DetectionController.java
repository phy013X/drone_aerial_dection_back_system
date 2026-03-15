package com.expressway.controller;

import com.expressway.dto.RealTimeDetectionDTO;
import com.expressway.result.Result;
import com.expressway.service.DetectionService;
import com.expressway.vo.RealTimeDetectionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 检测控制器
 */
@RestController
@RequestMapping("/detection")
@Tag(name = "检测管理", description = "实时检测、检测历史查询等接口")
@Slf4j
public class DetectionController {

    @Resource
    private DetectionService detectionService;

    /**
     * 实时检测
     */
    @PostMapping("/realtime")
    @Operation(summary = "实时检测", description = "实时视频流检测，返回检测结果")
    public Result<RealTimeDetectionVO> realTimeDetection(@Validated @RequestBody RealTimeDetectionDTO realTimeDetectionDTO) {
        log.info("实时检测：{}", realTimeDetectionDTO);
        RealTimeDetectionVO realTimeDetectionVO = detectionService.realTimeDetection(realTimeDetectionDTO);
        return Result.success(realTimeDetectionVO);
    }

    /**
     * 获取检测历史
     */
    @GetMapping("/history")
    @Operation(summary = "获取检测历史", description = "获取检测历史记录，支持设备ID、时间范围和类型筛选")
    public Result<?> getDetectionHistory(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取检测历史：deviceId={}, type={}, startDate={}, endDate={}, page={}, pageSize={}", 
                deviceId, type, startDate, endDate, page, pageSize);
        return Result.success(detectionService.getDetectionHistory(deviceId, type, startDate, endDate, page, pageSize));
    }

    /**
     * 获取检测详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取检测详情", description = "根据检测ID获取检测详细信息")
    public Result<?> getDetectionById(@PathVariable Long id) {
        log.info("获取检测详情：id={}", id);
        return Result.success(detectionService.getDetectionById(id));
    }

    /**
     * 分析检测结果
     */
    @GetMapping("/{id}/analyze")
    @Operation(summary = "分析检测结果", description = "分析检测结果，返回统计信息")
    public Result<?> analyzeDetectionResult(@PathVariable Long id) {
        log.info("分析检测结果：id={}", id);
        return Result.success(detectionService.analyzeDetectionResult(id));
    }

    /**
     * 分析检测
     */
    @PostMapping("/analyze")
    @Operation(summary = "分析检测", description = "分析检测数据")
    public Result<?> analyzeDetection(@RequestBody Object detectionData) {
        log.info("分析检测：{}", detectionData);
        // 实现分析检测逻辑
        return Result.success("分析成功");
    }
}
