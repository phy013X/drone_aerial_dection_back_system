package com.expressway.entity;

import lombok.Data;
import java.util.Date;

/**
 * 告警实体（对应alert表）
 */
@Data
public class Alert {
    private Long id; // 告警ID
    private Long deviceId; // 设备ID
    private String type; // 告警类型
    private Integer level; // 告警级别：1-高，2-中，3-低
    private String message; // 告警信息
    private Long detectionId; // 检测ID
    private String imageUrl; // 图像URL
    private Double latitude; // 纬度
    private Double longitude; // 经度
    private Integer status; // 状态：1-未处理，2-已处理，3-已忽略
    private Date createTime; // 创建时间
    private Date processTime; // 处理时间
    private Long processorId; // 处理人ID
    private String processNote; // 处理备注

    // 关联查询字段（非数据库字段）
    private String deviceName;
    private String processorName;
}
