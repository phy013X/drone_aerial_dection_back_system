package com.expressway.entity;

import lombok.Data;
import java.util.Date;

@Data
public class VideoDetectionTask {
    private Long id;
    private Long videoId;
    private String modelType;
    private Double confidenceThreshold;
    private String detectClasses;
    private Integer frameInterval;
    private Integer priority;
    private Integer status;
    private Integer progress;
    private Integer totalFrames;
    private Integer processedFrames;
    private Integer detectionCount;
    private Long createUserId;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private String resultSummary;
    
    private String videoName;
}