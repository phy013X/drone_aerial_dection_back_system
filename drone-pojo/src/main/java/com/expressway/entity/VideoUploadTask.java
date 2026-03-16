package com.expressway.entity;

import lombok.Data;
import java.util.Date;

@Data
public class VideoUploadTask {
    private Long id;
    private String uploadId;
    private Long videoId;
    private String fileName;
    private Long fileSize;
    private String fileType;
    private Integer chunkSize;
    private Integer totalChunks;
    private Integer uploadedChunks;
    private Integer status;
    private Integer progress;
    private String errorMessage;
    private Long uploadUserId;
    private Date createTime;
    private Date updateTime;
    private Date completeTime;
}
