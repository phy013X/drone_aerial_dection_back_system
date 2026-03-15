package com.expressway.entity;

import lombok.Data;

/**
 * 检测结果实体（对应detection_result表）
 */
@Data
public class DetectionResult {
    private Long id; // 结果ID
    private Long detectionId; // 检测ID
    private String type; // 目标类型
    private Double confidence; // 置信度
    private Integer bboxX; // 边界框X坐标
    private Integer bboxY; // 边界框Y坐标
    private Integer bboxWidth; // 边界框宽度
    private Integer bboxHeight; // 边界框高度
    private Integer trackId; // 追踪ID
}
