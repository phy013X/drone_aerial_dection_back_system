package com.expressway.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 检测历史VO
 */
@Data
public class DetectionHistoryVO {
    private Long id; // 检测ID
    private Long deviceId; // 设备ID
    private String deviceName; // 设备名称
    private Date timestamp; // 检测时间
    private Integer detectionCount; // 检测目标数量
    private List<String> types; // 检测目标类型列表
    private String imageUrl; // 图像URL
    private String processedImageUrl; // 处理后图像URL
    private Double latitude; // 纬度
    private Double longitude; // 经度
    private Double altitude; // 高度
    private Integer inferenceTime; // 推理时间（毫秒）
}
