package com.expressway.entity;

import lombok.Data;
import java.util.Date;

/**
 * 设备实体（对应device表）
 */
@Data
public class Device {
    private Long id; // 主键ID
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
    private Integer status; // 状态：1-在线，0-离线，2-故障，3-维护中
    private String ip; // IP地址
    private Integer port; // 端口
    private String connectionType; // 连接类型：WiFi、4G、有线
    private String firmwareVersion; // 固件版本
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    private Date lastOnlineTime; // 最后在线时间

    // 关联查询字段（非数据库字段）
    private String groupName;
}
