package com.expressway.dto;

import lombok.Data;

/**
 * 设备注册DTO
 */
@Data
public class DeviceRegisterDTO {
    private String name; // 设备名称
    private String type; // 设备类型
    private String model; // 设备型号
    private String serialNumber; // 序列号
    private Long groupId; // 分组ID
    private String unit; // 所属单位
    private String contact; // 联系人
    private String contactPhone; // 联系电话
    private String location; // 安装位置
    private Double latitude; // 纬度
    private Double longitude; // 经度
    private String ip; // IP地址
    private Integer port; // 端口
    private String connectionType; // 连接类型
    private String firmwareVersion; // 固件版本
}
