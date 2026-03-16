package com.expressway.dto;

import lombok.Data;

/**
 * 视频检测任务DTO
 */
@Data
public class VideoDetectionTaskDTO {
    private Long videoId; // 视频ID
    private String modelType; // 模型类型
    private Double confidenceThreshold; // 置信度阈值
    private String detectClasses; // 检测类别（JSON格式）
    private Integer frameInterval; // 帧间隔
    private Integer priority; // 优先级
}