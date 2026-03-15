package com.expressway.entity;

import lombok.Data;
import java.util.Date;

/**
 * 设备分组实体（对应device_group表）
 */
@Data
public class DeviceGroup {
    private Long id; // 分组ID
    private String name; // 分组名称
    private Long parentId; // 父分组ID
    private String description; // 分组描述
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
}
