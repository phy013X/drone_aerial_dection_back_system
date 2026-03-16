package com.expressway.controller;

import com.expressway.dto.VideoDetectionTaskDTO;
import com.expressway.dto.VideoUploadDTO;
import com.expressway.result.Result;
import com.expressway.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 视频管理控制器
 */
@RestController
@RequestMapping("/video")
@Tag(name = "视频管理", description = "视频上传、检测、管理等接口")
@Slf4j
public class VideoController {

    @Resource
    private VideoService videoService;

    /**
     * 初始化上传
     */
    @PostMapping("/upload/init")
    @Operation(summary = "初始化上传", description = "初始化分片上传任务")
    public Result<?> initUpload(@Validated @RequestBody VideoUploadDTO videoUploadDTO) {
        log.info("初始化上传：{}", videoUploadDTO);
        String uploadId = videoService.initUpload(videoUploadDTO);
        return Result.success(uploadId);
    }

    /**
     * 上传分片
     */
    @PostMapping("/upload/chunk")
    @Operation(summary = "上传分片", description = "上传单个分片")
    public Result<?> uploadChunk(
            @RequestParam("uploadId") String uploadId,
            @RequestParam("chunkIndex") int chunkIndex,
            @RequestParam("chunkSize") int chunkSize,
            @RequestParam("totalChunks") int totalChunks,
            @RequestParam("file") MultipartFile file) {
        log.info("上传分片：uploadId={}, chunkIndex={}, chunkSize={}, totalChunks={}", uploadId, chunkIndex, chunkSize, totalChunks);
        try {
            videoService.uploadChunk(uploadId, chunkIndex, chunkSize, totalChunks, file.getInputStream());
            return Result.success("上传分片成功");
        } catch (IOException e) {
            log.error("上传分片失败", e);
            return Result.error("上传分片失败");
        }
    }

    /**
     * 合并分片
     */
    @PostMapping("/upload/complete")
    @Operation(summary = "合并分片", description = "合并所有分片完成上传")
    public Result<?> completeUpload(@RequestParam("uploadId") String uploadId) {
        log.info("合并分片：uploadId={}", uploadId);
        return Result.success(videoService.completeUpload(uploadId));
    }

    /**
     * 取消上传
     */
    @PostMapping("/upload/cancel")
    @Operation(summary = "取消上传", description = "取消上传任务")
    public Result<?> cancelUpload(@RequestParam("uploadId") String uploadId) {
        log.info("取消上传：uploadId={}", uploadId);
        videoService.cancelUpload(uploadId);
        return Result.success("取消上传成功");
    }

    /**
     * 获取上传进度
     */
    @GetMapping("/upload/progress/{uploadId}")
    @Operation(summary = "获取上传进度", description = "获取上传进度")
    public Result<?> getUploadProgress(@PathVariable String uploadId) {
        log.info("获取上传进度：uploadId={}", uploadId);
        int progress = videoService.getUploadProgress(uploadId);
        return Result.success(progress);
    }

    /**
     * 获取视频列表
     */
    @GetMapping
    @Operation(summary = "获取视频列表", description = "获取用户上传的视频列表")
    public Result<?> getVideoList(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取视频列表：keyword={}, status={}, page={}, pageSize={}", keyword, status, page, pageSize);
        return Result.success(videoService.getVideoList(keyword, status, page, pageSize));
    }

    /**
     * 获取视频详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取视频详情", description = "获取视频详细信息")
    public Result<?> getVideoById(@PathVariable Long id) {
        log.info("获取视频详情：id={}", id);
        return Result.success(videoService.getVideoById(id));
    }

    /**
     * 更新视频信息
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新视频信息", description = "更新视频文件名和描述")
    public Result<?> updateVideo(@PathVariable Long id, @Validated @RequestBody VideoUploadDTO videoUploadDTO) {
        log.info("更新视频信息：id={}, {}", id, videoUploadDTO);
        return Result.success(videoService.updateVideo(id, videoUploadDTO));
    }

    /**
     * 删除视频
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除视频", description = "删除视频及相关数据")
    public Result<?> deleteVideo(@PathVariable Long id) {
        log.info("删除视频：id={}", id);
        videoService.deleteVideo(id);
        return Result.success("删除成功");
    }

    /**
     * 批量删除
     */
    @PostMapping("/batch-delete")
    @Operation(summary = "批量删除", description = "批量删除视频")
    public Result<?> batchDeleteVideo(@RequestBody List<Long> ids) {
        log.info("批量删除视频：ids={}", ids);
        videoService.batchDeleteVideo(ids);
        return Result.success("删除成功");
    }

    /**
     * 创建检测任务
     */
    @PostMapping("/{videoId}/detect")
    @Operation(summary = "创建检测任务", description = "创建视频检测任务")
    public Result<?> createDetectionTask(@PathVariable Long videoId, @Validated @RequestBody VideoDetectionTaskDTO taskDTO) {
        log.info("创建检测任务：videoId={}, {}", videoId, taskDTO);
        taskDTO.setVideoId(videoId);
        return Result.success(videoService.createDetectionTask(taskDTO));
    }

    /**
     * 获取任务列表
     */
    @GetMapping("/{videoId}/tasks")
    @Operation(summary = "获取任务列表", description = "获取视频的检测任务列表")
    public Result<?> getVideoTasks(
            @PathVariable Long videoId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取任务列表：videoId={}, page={}, pageSize={}", videoId, page, pageSize);
        return Result.success(videoService.getVideoTasks(videoId, page, pageSize));
    }

    /**
     * 获取任务详情
     */
    @GetMapping("/task/{taskId}")
    @Operation(summary = "获取任务详情", description = "获取检测任务详情")
    public Result<?> getTaskById(@PathVariable Long taskId) {
        log.info("获取任务详情：taskId={}", taskId);
        return Result.success(videoService.getTaskById(taskId));
    }

    /**
     * 取消检测任务
     */
    @PostMapping("/task/{taskId}/cancel")
    @Operation(summary = "取消检测任务", description = "取消正在进行的检测任务")
    public Result<?> cancelDetectionTask(@PathVariable Long taskId) {
        log.info("取消检测任务：taskId={}", taskId);
        videoService.cancelDetectionTask(taskId);
        return Result.success("取消成功");
    }

    /**
     * 获取任务进度
     */
    @GetMapping("/task/{taskId}/progress")
    @Operation(summary = "获取任务进度", description = "获取检测进度")
    public Result<?> getTaskProgress(@PathVariable Long taskId) {
        log.info("获取任务进度：taskId={}", taskId);
        int progress = videoService.getTaskProgress(taskId);
        return Result.success(progress);
    }

    /**
     * 获取结果列表
     */
    @GetMapping("/{videoId}/results")
    @Operation(summary = "获取结果列表", description = "获取检测结果列表")
    public Result<?> getDetectionResults(
            @PathVariable Long videoId,
            @RequestParam(value = "taskId", required = false) Long taskId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取结果列表：videoId={}, taskId={}, page={}, pageSize={}", videoId, taskId, page, pageSize);
        return Result.success(videoService.getDetectionResults(videoId, taskId, page, pageSize));
    }

    /**
     * 获取指定帧结果
     */
    @GetMapping("/{videoId}/frame/{frameNumber}")
    @Operation(summary = "获取指定帧结果", description = "获取指定帧的检测结果")
    public Result<?> getFrameResult(
            @PathVariable Long videoId,
            @PathVariable Integer frameNumber,
            @RequestParam(value = "taskId", required = false) Long taskId) {
        log.info("获取指定帧结果：videoId={}, frameNumber={}, taskId={}", videoId, frameNumber, taskId);
        return Result.success(videoService.getFrameResult(videoId, taskId, frameNumber));
    }

    /**
     * 获取检测统计
     */
    @GetMapping("/{videoId}/stats")
    @Operation(summary = "获取检测统计", description = "获取检测统计信息")
    public Result<?> getDetectionStats(
            @PathVariable Long videoId,
            @RequestParam(value = "taskId", required = false) Long taskId) {
        log.info("获取检测统计：videoId={}, taskId={}", videoId, taskId);
        return Result.success(videoService.getDetectionStats(videoId, taskId));
    }

    /**
     * 导出视频
     */
    @PostMapping("/{videoId}/export/video")
    @Operation(summary = "导出视频", description = "导出带检测框的视频")
    public Result<?> exportVideo(
            @PathVariable Long videoId,
            @RequestParam(value = "taskId", required = false) Long taskId) {
        log.info("导出视频：videoId={}, taskId={}", videoId, taskId);
        String exportId = videoService.exportVideo(videoId, taskId);
        return Result.success(exportId);
    }

    /**
     * 导出报表
     */
    @PostMapping("/{videoId}/export/report")
    @Operation(summary = "导出报表", description = "导出检测结果报表")
    public Result<?> exportReport(
            @PathVariable Long videoId,
            @RequestParam(value = "taskId", required = false) Long taskId) {
        log.info("导出报表：videoId={}, taskId={}", videoId, taskId);
        String exportId = videoService.exportReport(videoId, taskId);
        return Result.success(exportId);
    }

    /**
     * 导出截图
     */
    @PostMapping("/{videoId}/export/screenshots")
    @Operation(summary = "导出截图", description = "导出检测截图")
    public Result<?> exportScreenshots(
            @PathVariable Long videoId,
            @RequestParam(value = "taskId", required = false) Long taskId) {
        log.info("导出截图：videoId={}, taskId={}", videoId, taskId);
        String exportId = videoService.exportScreenshots(videoId, taskId);
        return Result.success(exportId);
    }

    /**
     * 获取导出进度
     */
    @GetMapping("/export/{exportId}/progress")
    @Operation(summary = "获取导出进度", description = "获取导出进度")
    public Result<?> getExportProgress(@PathVariable String exportId) {
        log.info("获取导出进度：exportId={}", exportId);
        int progress = videoService.getExportProgress(exportId);
        return Result.success(progress);
    }

    /**
     * 下载导出文件
     */
    @GetMapping("/export/{exportId}/download")
    @Operation(summary = "下载导出文件", description = "下载导出文件")
    public Result<?> downloadExportFile(@PathVariable String exportId) {
        log.info("下载导出文件：exportId={}", exportId);
        String downloadUrl = videoService.getExportDownloadUrl(exportId);
        return Result.success(downloadUrl);
    }

    /**
     * 获取导出历史
     */
    @GetMapping("/{videoId}/exports")
    @Operation(summary = "获取导出历史", description = "获取导出历史记录")
    public Result<?> getExportHistory(
            @PathVariable Long videoId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("获取导出历史：videoId={}, page={}, pageSize={}", videoId, page, pageSize);
        return Result.success(videoService.getExportHistory(videoId, page, pageSize));
    }
}