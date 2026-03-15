package com.expressway.dto;

import lombok.Data;

/**
 * 实时检测DTO
 */
@Data
public class RealTimeDetectionDTO {
    private Long deviceId; // 设备ID
    private String image; // 图像Base64编码
    private String timestamp; // 时间戳
}
