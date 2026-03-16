package com.expressway.service;

import com.expressway.dto.VideoDetectionTaskDTO;
import com.expressway.dto.VideoUploadDTO;
import com.expressway.entity.Video;
import com.expressway.entity.VideoDetectionTask;
import com.expressway.entity.VideoDetectionResult;
import com.expressway.result.PageResult;

import java.io.InputStream;
import java.util.List;

/**
 * 视频服务
 */
public interface VideoService {
    /**
     * 初始化视频上传
     * @param videoUploadDTO 视频上传信息
     * @return 上传任务ID
     */
    String initUpload(VideoUploadDTO videoUploadDTO);

    /**
     * 上传视频分片
     * @param uploadId 上传任务ID
     * @param chunkIndex 分片索引
     * @param chunkSize 分片大小
     * @param totalChunks 总分片数
     * @param inputStream 分片数据
     */
    void uploadChunk(String uploadId, int chunkIndex, int chunkSize, int totalChunks, InputStream inputStream);

    /**
     * 完成视频上传
     * @param uploadId 上传任务ID
     * @return 视频信息
     */
    Video completeUpload(String uploadId);

    /**
     * 取消视频上传
     * @param uploadId 上传任务ID
     */
    void cancelUpload(String uploadId);

    /**
     * 获取上传进度
     * @param uploadId 上传任务ID
     * @return 上传进度（0-100）
     */
    int getUploadProgress(String uploadId);

    /**
     * 获取视频列表
     * @param keyword 关键词
     * @param status 状态
     * @param page 页码
     * @param pageSize 每页数量
     * @return 视频列表
     */
    PageResult<Video> getVideoList(String keyword, Integer status, Integer page, Integer pageSize);

    /**
     * 获取视频详情
     * @param id 视频ID
     * @return 视频详情
     */
    Video getVideoById(Long id);

    /**
     * 更新视频信息
     * @param id 视频ID
     * @param videoUploadDTO 视频信息
     * @return 更新后的视频信息
     */
    Video updateVideo(Long id, VideoUploadDTO videoUploadDTO);

    /**
     * 删除视频
     * @param id 视频ID
     */
    void deleteVideo(Long id);

    /**
     * 批量删除视频
     * @param ids 视频ID列表
     */
    void batchDeleteVideo(List<Long> ids);

    /**
     * 创建视频检测任务
     * @param taskDTO 检测任务信息
     * @return 检测任务
     */
    VideoDetectionTask createDetectionTask(VideoDetectionTaskDTO taskDTO);

    /**
     * 获取视频的检测任务列表
     * @param videoId 视频ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 检测任务列表
     */
    PageResult<VideoDetectionTask> getVideoTasks(Long videoId, Integer page, Integer pageSize);

    /**
     * 获取检测任务详情
     * @param taskId 任务ID
     * @return 检测任务详情
     */
    VideoDetectionTask getTaskById(Long taskId);

    /**
     * 取消检测任务
     * @param taskId 任务ID
     */
    void cancelDetectionTask(Long taskId);

    /**
     * 获取检测任务进度
     * @param taskId 任务ID
     * @return 进度（0-100）
     */
    int getTaskProgress(Long taskId);

    /**
     * 获取视频检测结果列表
     * @param videoId 视频ID
     * @param taskId 任务ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 检测结果列表
     */
    PageResult<VideoDetectionResult> getDetectionResults(Long videoId, Long taskId, Integer page, Integer pageSize);

    /**
     * 获取指定帧的检测结果
     * @param videoId 视频ID
     * @param taskId 任务ID
     * @param frameNumber 帧号
     * @return 检测结果
     */
    VideoDetectionResult getFrameResult(Long videoId, Long taskId, Integer frameNumber);

    /**
     * 获取检测统计信息
     * @param videoId 视频ID
     * @param taskId 任务ID
     * @return 统计信息
     */
    Object getDetectionStats(Long videoId, Long taskId);

    /**
     * 导出带检测框的视频
     * @param videoId 视频ID
     * @param taskId 任务ID
     * @return 导出任务ID
     */
    String exportVideo(Long videoId, Long taskId);

    /**
     * 导出检测结果报表
     * @param videoId 视频ID
     * @param taskId 任务ID
     * @return 导出任务ID
     */
    String exportReport(Long videoId, Long taskId);

    /**
     * 导出检测截图
     * @param videoId 视频ID
     * @param taskId 任务ID
     * @return 导出任务ID
     */
    String exportScreenshots(Long videoId, Long taskId);

    /**
     * 获取导出进度
     * @param exportId 导出任务ID
     * @return 进度（0-100）
     */
    int getExportProgress(String exportId);

    /**
     * 获取导出文件下载链接
     * @param exportId 导出任务ID
     * @return 下载链接
     */
    String getExportDownloadUrl(String exportId);

    /**
     * 获取导出历史
     * @param videoId 视频ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 导出历史列表
     */
    PageResult<Object> getExportHistory(Long videoId, Integer page, Integer pageSize);
}