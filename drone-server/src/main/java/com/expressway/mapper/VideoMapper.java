package com.expressway.mapper;

import com.expressway.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 视频Mapper
 */
@Mapper
public interface VideoMapper {
    /**
     * 创建视频记录
     * @param video 视频信息
     */
    void createVideo(Video video);

    /**
     * 获取视频列表
     * @param deviceId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 页码
     * @param size 每页数量
     * @return 视频列表
     */
    List<Video> getVideoList(
            @Param("deviceId") Long deviceId,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("page") Integer page,
            @Param("size") Integer size
    );

    /**
     * 获取视频总数
     * @param deviceId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 视频总数
     */
    Integer getVideoCount(
            @Param("deviceId") Long deviceId,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );

    /**
     * 获取视频详情
     * @param id 视频ID
     * @return 视频详情
     */
    Video getVideoById(Long id);

    /**
     * 更新视频状态
     * @param id 视频ID
     * @param status 视频状态
     */
    void updateVideoStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 删除视频
     * @param id 视频ID
     */
    void deleteVideo(Long id);
}
