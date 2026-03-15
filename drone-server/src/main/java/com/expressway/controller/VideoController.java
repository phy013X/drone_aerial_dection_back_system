package com.expressway.controller;

import com.expressway.result.Result;
import com.expressway.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 视频控制器
 */
@RestController
@RequestMapping("/videos")
@Tag(name = "视频管理", description = "视频上传、查询、回放等接口")
@Slf4j
public class VideoController {

    @Resource
    private VideoService videoService;

    /**
     * 上传视频
     */
    @PostMapping("/upload")
    @Operation(summary = "上传视频", description = "上传视频文件")
    public Result<?> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("deviceId") Long deviceId) {
        log.info("上传视频：deviceId={}, fileName={}", deviceId, file.getOriginalFilename());
        videoService.uploadVideo(file, deviceId);
        return Result.success("上传成功");
    }

    /**
     * 获取视频列表
     */
    @GetMapping
    @Operation(summary = "获取视频列表", description = "获取视频列表，支持设备ID和时间范围筛选")
    public Result<?> getVideoList(
            @RequestParam(value = "deviceId", required = false) Long deviceId,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("获取视频列表：deviceId={}, startTime={}, endTime={}, page={}, size={}", 
                deviceId, startTime, endTime, page, size);
        return Result.success(videoService.getVideoList(deviceId, startTime, endTime, page, size));
    }

    /**
     * 获取视频详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取视频详情", description = "根据视频ID获取视频详细信息")
    public Result<?> getVideoById(@PathVariable Long id) {
        log.info("获取视频详情：id={}", id);
        return Result.success(videoService.getVideoById(id));
    }

    /**
     * 删除视频
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除视频", description = "删除指定视频")
    public Result<?> deleteVideo(@PathVariable Long id) {
        log.info("删除视频：id={}", id);
        videoService.deleteVideo(id);
        return Result.success("删除成功");
    }

    /**
     * 视频回放
     */
    @GetMapping("/{id}/playback")
    @Operation(summary = "视频回放", description = "获取视频回放URL")
    public Result<?> playbackVideo(
            @PathVariable Long id,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime) {
        log.info("视频回放：id={}, startTime={}, endTime={}", id, startTime, endTime);
        String playbackUrl = videoService.playbackVideo(id, startTime, endTime);
        return Result.success(playbackUrl);
    }
}
