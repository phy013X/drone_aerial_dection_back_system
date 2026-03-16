package com.expressway.dto;

import lombok.Data;

@Data
public class VideoUploadDTO {
    private String fileName;
    private String fileType;
    private Long fileSize;
    private Integer duration;
    private String resolution;
    private String description;
    private Integer chunkSize;
}