package com.expressway.entity;

import lombok.Data;
import java.util.Date;

/**
 * 检测记录实体（对应detection表）
 */
@Data
public class Detection {
    private Long id; // 检测ID
    private Long deviceId; // 设备ID
    private Date timestamp; // 检测时间
    private String imageUrl; // 原始图像URL
    private String processedImageUrl; // 处理后图像URL
    private Double latitude; // 纬度
    private Double longitude; // 经度
    private Double altitude; // 高度
    private Integer detectionCount; // 检测目标数量
    private String modelVersion; // 模型版本
    private Integer inferenceTime; // 推理时间（毫秒）
    private Date createTime; // 创建时间

    // 关联查询字段（非数据库字段）
    private String deviceName;
}
