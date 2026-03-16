package com.expressway.vo;

import lombok.Data;
import java.util.Date;

/**
 * 视频VO
 */
@Data
public class VideoVO {
    private Long id; // 视频ID
    private String fileName; // 文件名
    private String fileType; // 文件类型
    private Long fileSize; // 文件大小
    private Integer duration; // 时长（秒）
    private String resolution; // 分辨率
    private Integer status; // 状态
    private String statusText; // 状态文本
    private Date createTime; // 创建时间
    private String deviceName; // 设备名称
    private String fileUrl; // 文件访问URL
}