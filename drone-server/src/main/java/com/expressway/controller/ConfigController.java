package com.expressway.controller;

import com.expressway.result.Result;
import com.expressway.service.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 配置相关控制器
 */
@RestController
@RequestMapping("/config")
@Tag(name = "配置管理", description = "系统配置相关接口")
@Slf4j
public class ConfigController {

    @Resource
    private ConfigService configService;

    /**
     * 获取AI配置
     */
    @GetMapping("/ai")
    @Operation(summary = "获取AI配置", description = "获取AI模型配置")
    public Result<?> getAiConfig() {
        log.info("获取AI配置");
        Map<String, Object> aiConfig = configService.getAiConfig();
        return Result.success(aiConfig);
    }

    /**
     * 更新AI配置
     */
    @PutMapping("/ai")
    @Operation(summary = "更新AI配置", description = "更新AI模型配置")
    public Result<?> updateAiConfig(@RequestBody Map<String, Object> aiConfig) {
        log.info("更新AI配置：{}", aiConfig);
        Map<String, Object> result = configService.updateAiConfig(aiConfig);
        return Result.success(result);
    }

    /**
     * 测试AI模型
     */
    @PostMapping("/ai/test")
    @Operation(summary = "测试AI模型", description = "测试AI模型性能")
    public Result<?> testAiModel() {
        log.info("测试AI模型");
        Map<String, Object> testResult = configService.testAiModel();
        return Result.success(testResult);
    }

    /**
     * 获取告警配置
     */
    @GetMapping("/alert")
    @Operation(summary = "获取告警配置", description = "获取告警规则配置")
    public Result<?> getAlertConfig() {
        log.info("获取告警配置");
        Map<String, Object> alertConfig = configService.getAlertConfig();
        return Result.success(alertConfig);
    }

    /**
     * 更新告警配置
     */
    @PutMapping("/alert")
    @Operation(summary = "更新告警配置", description = "更新告警规则配置")
    public Result<?> updateAlertConfig(@RequestBody Map<String, Object> alertConfig) {
        log.info("更新告警配置：{}", alertConfig);
        Map<String, Object> result = configService.updateAlertConfig(alertConfig);
        return Result.success(result);
    }

    /**
     * 获取系统参数
     */
    @GetMapping("/system")
    @Operation(summary = "获取系统参数", description = "获取系统参数配置")
    public Result<?> getSystemConfig() {
        log.info("获取系统参数");
        Map<String, Object> systemConfig = configService.getSystemConfig();
        return Result.success(systemConfig);
    }

    /**
     * 更新系统参数
     */
    @PutMapping("/system")
    @Operation(summary = "更新系统参数", description = "更新系统参数配置")
    public Result<?> updateSystemConfig(@RequestBody Map<String, Object> systemConfig) {
        log.info("更新系统参数：{}", systemConfig);
        Map<String, Object> result = configService.updateSystemConfig(systemConfig);
        return Result.success(result);
    }

    /**
     * 获取系统信息
     */
    @GetMapping("/info")
    @Operation(summary = "获取系统信息", description = "获取系统基本信息")
    public Result<?> getSystemInfo() {
        log.info("获取系统信息");
        Map<String, Object> systemInfo = configService.getSystemInfo();
        return Result.success(systemInfo);
    }

    /**
     * 重置配置
     */
    @PostMapping("/reset/{type}")
    @Operation(summary = "重置配置", description = "重置指定类型的配置")
    public Result<?> resetConfig(@PathVariable String type) {
        log.info("重置配置：type={}", type);
        Map<String, Object> result = configService.resetConfig(type);
        return Result.success(result);
    }
}