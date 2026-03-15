package com.expressway.vo;

import lombok.Data;

import java.util.Date;

/**
 * 告警VO
 */
@Data
public class AlertVO {
    private Long id; // 告警ID
    private Long deviceId; // 设备ID
    private String deviceName; // 设备名称
    private String type; // 告警类型
    private Integer level; // 告警级别
    private String levelText; // 告警级别文本
    private String message; // 告警信息
    private Long detectionId; // 检测ID
    private String imageUrl; // 图像URL
    private Double latitude; // 纬度
    private Double longitude; // 经度
    private Integer status; // 状态
    private String statusText; // 状态文本
    private Date createTime; // 创建时间
    private Date processTime; // 处理时间
    private String processor; // 处理人
    private String processNote; // 处理备注
}
