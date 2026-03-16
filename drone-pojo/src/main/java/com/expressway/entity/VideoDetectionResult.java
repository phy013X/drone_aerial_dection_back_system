package com.expressway.entity;

import lombok.Data;

/**
 * 视频检测结果实体
 */
@Data
public class VideoDetectionResult {
    private Long id; // 结果ID
    private Long taskId; // 任务ID
    private Integer frameNumber; // 帧号
    private Double timestamp; // 时间戳（秒）
    private String imageUrl; // 原始帧图像URL
    private String processedImageUrl; // 处理后帧图像URL
    private Integer detectionCount; // 检测目标数量
    private Integer inferenceTime; // 推理时间（毫秒）
    private String targets; // 检测目标（JSON格式）
}