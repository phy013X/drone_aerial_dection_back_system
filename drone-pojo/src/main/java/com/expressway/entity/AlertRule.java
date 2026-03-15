package com.expressway.entity;

import lombok.Data;
import java.util.Date;

/**
 * 告警规则实体（对应alert_rule表）
 */
@Data
public class AlertRule {
    private Long id; // 规则ID
    private String name; // 规则名称
    private String type; // 告警类型
    private Integer level; // 告警级别：1-高，2-中，3-低
    private Integer threshold; // 阈值
    private String areaType; // 区域类型：circle、polygon、rectangle
    private String areaData; // 区域数据（JSON）
    private String timeType; // 时间类型：weekday、weekend、all
    private String timeData; // 时间数据（JSON）
    private Integer enabled; // 是否启用：1-启用，0-禁用
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
}
