package com.expressway.mapper;

import com.expressway.entity.VideoExportTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoExportTaskMapper {
    void insert(VideoExportTask task);
    
    VideoExportTask selectByExportId(@Param("exportId") String exportId);
    
    List<VideoExportTask> selectByVideoId(@Param("videoId") Long videoId, @Param("page") Integer page, @Param("size") Integer size);
    
    int countByVideoId(@Param("videoId") Long videoId);
    
    void updateProgress(@Param("exportId") String exportId, @Param("progress") Integer progress);
    
    void updateComplete(@Param("exportId") String exportId, @Param("status") Integer status, @Param("fileUrl") String fileUrl, @Param("fileSize") Long fileSize);
    
    void deleteByExportId(@Param("exportId") String exportId);
}
