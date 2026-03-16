package com.expressway.entity;

import lombok.Data;
import java.util.Date;

@Data
public class VideoExportTask {
    private Long id;
    private String exportId;
    private Long videoId;
    private Long taskId;
    private String exportType;
    private String exportConfig;
    private Integer status;
    private Integer progress;
    private String fileUrl;
    private Long fileSize;
    private String errorMessage;
    private Long createUserId;
    private Date createTime;
    private Date updateTime;
    private Date completeTime;
}
