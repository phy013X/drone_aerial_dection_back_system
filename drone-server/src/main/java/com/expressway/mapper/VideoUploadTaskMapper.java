package com.expressway.mapper;

import com.expressway.entity.VideoUploadTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VideoUploadTaskMapper {
    void insert(VideoUploadTask task);
    
    VideoUploadTask selectByUploadId(@Param("uploadId") String uploadId);
    
    void updateProgress(@Param("uploadId") String uploadId, @Param("progress") Integer progress, @Param("uploadedChunks") Integer uploadedChunks);
    
    void updateStatus(@Param("uploadId") String uploadId, @Param("status") Integer status, @Param("errorMessage") String errorMessage);
    
    void updateVideoId(@Param("uploadId") String uploadId, @Param("videoId") Long videoId);
    
    void deleteByUploadId(@Param("uploadId") String uploadId);
}
