package com.expressway.entity;

import lombok.Data;
import java.util.Date;

/**
 * 视频实体（对应video表）
 */
@Data
public class Video {
    private Long id; // 视频ID
    private Long deviceId; // 设备ID
    private String fileName; // 文件名
    private String filePath; // 文件路径
    private String fileType; // 文件类型
    private Long fileSize; // 文件大小（字节）
    private Date startTime; // 开始时间
    private Date endTime; // 结束时间
    private Integer duration; // 时长（秒）
    private String resolution; // 分辨率
    private Integer status; // 状态：1-已上传，2-处理中，3-已处理
    private Date createTime; // 创建时间

    // 关联查询字段（非数据库字段）
    private String deviceName;
}
