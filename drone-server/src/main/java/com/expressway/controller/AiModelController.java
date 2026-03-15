package com.expressway.controller;

import com.expressway.entity.AiModel;
import com.expressway.result.Result;
import com.expressway.service.AiModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI模型控制器
 */
@RestController
@RequestMapping("/ai-models")
@Tag(name = "AI模型管理", description = "AI模型配置接口")
@Slf4j
public class AiModelController {

    @Resource
    private AiModelService aiModelService;

    /**
     * 获取AI模型列表
     */
    @GetMapping
    @Operation(summary = "获取AI模型列表", description = "获取AI模型列表，支持类型和启用状态筛选")
    public Result<List<AiModel>> getAiModelList(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "enabled", required = false) Integer enabled) {
        log.info("获取AI模型列表：type={}, enabled={}", type, enabled);
        List<AiModel> aiModelList = aiModelService.getAiModelList(type, enabled);
        return Result.success(aiModelList);
    }

    /**
     * 获取AI模型详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取AI模型详情", description = "根据模型ID获取AI模型详细信息")
    public Result<AiModel> getAiModelById(@PathVariable Long id) {
        log.info("获取AI模型详情：id={}", id);
        AiModel aiModel = aiModelService.getAiModelById(id);
        return Result.success(aiModel);
    }

    /**
     * 创建AI模型
     */
    @PostMapping
    @Operation(summary = "创建AI模型", description = "创建新的AI模型")
    public Result<?> createAiModel(@Validated @RequestBody AiModel aiModel) {
        log.info("创建AI模型：{}", aiModel);
        aiModelService.createAiModel(aiModel);
        return Result.success("创建成功");
    }

    /**
     * 更新AI模型
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新AI模型", description = "更新AI模型信息")
    public Result<?> updateAiModel(@PathVariable Long id, @Validated @RequestBody AiModel aiModel) {
        log.info("更新AI模型：id={}, {}", id, aiModel);
        aiModelService.updateAiModel(id, aiModel);
        return Result.success("更新成功");
    }

    /**
     * 删除AI模型
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除AI模型", description = "删除指定AI模型")
    public Result<?> deleteAiModel(@PathVariable Long id) {
        log.info("删除AI模型：id={}", id);
        aiModelService.deleteAiModel(id);
        return Result.success("删除成功");
    }

    /**
     * 启用/禁用AI模型
     */
    @PostMapping("/{id}/enable")
    @Operation(summary = "启用/禁用AI模型", description = "启用或禁用指定AI模型")
    public Result<?> enableAiModel(@PathVariable Long id, @RequestParam(value = "enabled") Integer enabled) {
        log.info("启用/禁用AI模型：id={}, enabled={}", id, enabled);
        aiModelService.enableAiModel(id, enabled);
        return Result.success(enabled == 1 ? "启用成功" : "禁用成功");
    }
}
