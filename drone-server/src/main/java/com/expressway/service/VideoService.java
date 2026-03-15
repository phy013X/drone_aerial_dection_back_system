package com.expressway.service;

import com.expressway.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 视频服务
 */
public interface VideoService {
    /**
     * 上传视频
     * @param file 视频文件
     * @param deviceId 设备ID
     * @return 视频信息
     */
    Video uploadVideo(MultipartFile file, Long deviceId);

    /**
     * 获取视频列表
     * @param deviceId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 页码
     * @param size 每页数量
     * @return 视频列表
     */
    com.expressway.result.PageResult<Video> getVideoList(
            Long deviceId, String startTime, String endTime, Integer page, Integer size
    );

    /**
     * 获取视频详情
     * @param id 视频ID
     * @return 视频详情
     */
    Video getVideoById(Long id);

    /**
     * 删除视频
     * @param id 视频ID
     */
    void deleteVideo(Long id);

    /**
     * 视频回放
     * @param id 视频ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 回放URL
     */
    String playbackVideo(Long id, String startTime, String endTime);
}
