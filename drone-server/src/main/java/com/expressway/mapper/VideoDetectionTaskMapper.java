package com.expressway.mapper;

import com.expressway.entity.VideoDetectionTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoDetectionTaskMapper {
    void insert(VideoDetectionTask task);
    
    VideoDetectionTask selectById(@Param("id") Long id);
    
    List<VideoDetectionTask> selectByVideoId(@Param("videoId") Long videoId, @Param("page") Integer page, @Param("size") Integer size);
    
    Integer countByVideoId(@Param("videoId") Long videoId);
    
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    void updateProgress(@Param("id") Long id, @Param("progress") Integer progress, @Param("processedFrames") Integer processedFrames, @Param("detectionCount") Integer detectionCount);
    
    void updateTaskComplete(@Param("id") Long id, @Param("status") Integer status, @Param("totalFrames") Integer totalFrames, @Param("processedFrames") Integer processedFrames, @Param("detectionCount") Integer detectionCount, @Param("resultSummary") String resultSummary);
    
    void deleteById(@Param("id") Long id);
}
