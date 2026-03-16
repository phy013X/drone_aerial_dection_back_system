package com.expressway.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Video {
    private Long id;
    private Long deviceId;
    private String fileName;
    private String description;
    private String filePath;
    private String fileType;
    private Long fileSize;
    private Date startTime;
    private Date endTime;
    private Integer duration;
    private String resolution;
    private Integer width;
    private Integer height;
    private Double fps;
    private String codec;
    private String format;
    private String thumbnailUrl;
    private Integer status;
    private Integer detectionStatus;
    private Integer totalFrames;
    private Integer processedFrames;
    private Integer detectionCount;
    private Long uploadUserId;
    private Date createTime;
    private Date updateTime;
    private Date completeTime;

    private String deviceName;
}
