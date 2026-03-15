package com.expressway.vo;

import lombok.Data;

import java.util.List;

/**
 * 实时检测VO
 */
@Data
public class RealTimeDetectionVO {
    private Long id; // 检测ID
    private Long deviceId; // 设备ID
    private String timestamp; // 时间戳
    private List<DetectionResultVO> results; // 检测结果列表
    private String processedImage; // 处理后图像Base64编码

    @Data
    public static class DetectionResultVO {
        private Long id; // 结果ID
        private String type; // 目标类型
        private Double confidence; // 置信度
        private BoundingBox bbox; // 边界框
    }

    @Data
    public static class BoundingBox {
        private Integer x; // X坐标
        private Integer y; // Y坐标
        private Integer width; // 宽度
        private Integer height; // 高度
    }
}
