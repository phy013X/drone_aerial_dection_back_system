package com.expressway.vo;

import lombok.Data;
import java.util.Date;

/**
 * 设备VO
 */
@Data
public class DeviceVO {
    private Long id; // 设备ID
    private String name; // 设备名称
    private String type; // 设备类型
    private String model; // 设备型号
    private String serialNumber; // 序列号
    private String groupName; // 分组名称
    private String unit; // 所属单位
    private String location; // 安装位置
    private Integer status; // 设备状态
    private String statusText; // 状态文本
    private String ip; // IP地址
    private Integer port; // 端口
    private String connectionType; // 连接类型
    private Date lastOnlineTime; // 最后在线时间
    private Date createTime; // 创建时间
}
