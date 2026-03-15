package com.expressway.dto;

import lombok.Data;

/**
 * 设备状态更新DTO
 */
@Data
public class DeviceStatusUpdateDTO {
    private Integer status; // 设备状态：1-在线，0-离线，2-故障，3-维护中
    private String ip; // IP地址
    private Integer port; // 端口
    private Double latitude; // 纬度
    private Double longitude; // 经度
}
