package com.expressway.service.impl;

import com.expressway.entity.Video;
import com.expressway.exception.BusinessException;
import com.expressway.mapper.VideoMapper;
import com.expressway.result.PageResult;
import com.expressway.service.VideoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 视频服务实现
 */
@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

    @Resource
    private VideoMapper videoMapper;

    @Override
    public Video uploadVideo(MultipartFile file, Long deviceId) {
        // 检查文件是否为空
        if (file.isEmpty()) {
            throw new BusinessException("视频文件不能为空");
        }
        // 检查文件类型
        String contentType = file.getContentType();
        if (!contentType.startsWith("video/")) {
            throw new BusinessException("请上传视频文件");
        }
        // 保存视频文件
        String fileName = file.getOriginalFilename();
        String directory = "videos/";
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = directory + System.currentTimeMillis() + "_" + fileName;
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            log.error("上传视频失败", e);
            throw new BusinessException("上传视频失败");
        }
        // 创建视频记录
        Video video = new Video();
        video.setDeviceId(deviceId);
        video.setFileName(fileName);
        video.setFilePath(filePath);
        video.setFileType(contentType);
        video.setFileSize(file.getSize());
        video.setStartTime(new Date());
        video.setEndTime(new Date());
        video.setDuration(0); // 模拟时长
        video.setResolution("1280x720"); // 模拟分辨率
        video.setStatus(1); // 已上传
        videoMapper.createVideo(video);
        return video;
    }

    @Override
    public PageResult<Video> getVideoList(
            Long deviceId, String startTime, String endTime, Integer page, Integer size) {
        // 计算分页偏移量
        int offset = (page - 1) * size;
        // 获取视频列表
        List<Video> videoList = videoMapper.getVideoList(deviceId, startTime, endTime, offset, size);
        // 获取视频总数
        int total = videoMapper.getVideoCount(deviceId, startTime, endTime);
        return new PageResult<>(total, videoList);
    }

    @Override
    public Video getVideoById(Long id) {
        Video video = videoMapper.getVideoById(id);
        if (video == null) {
            throw new BusinessException("视频不存在");
        }
        return video;
    }

    @Override
    public void deleteVideo(Long id) {
        // 检查视频是否存在
        Video video = videoMapper.getVideoById(id);
        if (video == null) {
            throw new BusinessException("视频不存在");
        }
        // 删除视频文件
        File videoFile = new File(video.getFilePath());
        if (videoFile.exists()) {
            videoFile.delete();
        }
        // 删除视频记录
        videoMapper.deleteVideo(id);
    }

    @Override
    public String playbackVideo(Long id, String startTime, String endTime) {
        // 检查视频是否存在
        Video video = videoMapper.getVideoById(id);
        if (video == null) {
            throw new BusinessException("视频不存在");
        }
        // 模拟视频回放URL
        // 实际项目中，这里应该返回一个可以访问视频文件的URL，可能需要配置视频服务器
        String playbackUrl = "/api/videos/" + id + "/play";
        if (startTime != null && endTime != null) {
            playbackUrl += "?startTime=" + startTime + "&endTime=" + endTime;
        }
        return playbackUrl;
    }
}
