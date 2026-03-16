package com.expressway.mapper;

import com.expressway.entity.VideoDetectionResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoDetectionResultMapper {
    void insert(VideoDetectionResult result);
    
    VideoDetectionResult selectById(@Param("id") Long id);
    
    List<VideoDetectionResult> selectByTaskId(@Param("taskId") Long taskId, @Param("page") Integer page, @Param("size") Integer size);
    
    VideoDetectionResult selectByTaskIdAndFrameNumber(@Param("taskId") Long taskId, @Param("frameNumber") Integer frameNumber);
    
    Integer countByTaskId(@Param("taskId") Long taskId);
    
    void deleteByTaskId(@Param("taskId") Long taskId);
}
