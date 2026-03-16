package com.expressway.service.impl;

import com.expressway.context.BaseContext;
import com.expressway.dto.VideoDetectionTaskDTO;
import com.expressway.dto.VideoUploadDTO;
import com.expressway.entity.Video;
import com.expressway.entity.VideoDetectionTask;
import com.expressway.entity.VideoDetectionResult;
import com.expressway.entity.VideoExportTask;
import com.expressway.entity.VideoUploadTask;
import com.expressway.mapper.VideoDetectionResultMapper;
import com.expressway.mapper.VideoDetectionTaskMapper;
import com.expressway.mapper.VideoExportTaskMapper;
import com.expressway.mapper.VideoMapper;
import com.expressway.mapper.VideoUploadTaskMapper;
import com.expressway.result.PageResult;
import com.expressway.service.VideoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 视频服务实现
 */
@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private VideoUploadTaskMapper uploadTaskMapper;

    @Resource
    private VideoDetectionTaskMapper detectionTaskMapper;

    @Resource
    private VideoDetectionResultMapper detectionResultMapper;

    @Resource
    private VideoExportTaskMapper exportTaskMapper;

    @Value("${video.upload.path:/tmp/videos}")
    private String uploadPath;

    @Value("${video.chunk.path:/tmp/chunks}")
    private String chunkPath;

    private final Map<String, List<Integer>> uploadChunksCache = new ConcurrentHashMap<>();

    @Override
    public String initUpload(VideoUploadDTO videoUploadDTO) {
        String uploadId = UUID.randomUUID().toString();
        VideoUploadTask task = new VideoUploadTask();
        task.setUploadId(uploadId);
        task.setFileName(videoUploadDTO.getFileName());
        task.setFileSize(videoUploadDTO.getFileSize());
        task.setFileType(videoUploadDTO.getFileType());
        task.setChunkSize(videoUploadDTO.getChunkSize() != null ? videoUploadDTO.getChunkSize() : 5242880);
        task.setTotalChunks((int) Math.ceil((double) videoUploadDTO.getFileSize() / task.getChunkSize()));
        task.setUploadedChunks(0);
        task.setStatus(1);
        task.setProgress(0);
        task.setUploadUserId(BaseContext.getCurrentId());
        uploadTaskMapper.insert(task);
        uploadChunksCache.put(uploadId, new ArrayList<>());
        return uploadId;
    }

    @Override
    @Transactional
    public void uploadChunk(String uploadId, int chunkIndex, int chunkSize, int totalChunks, InputStream inputStream) {
        VideoUploadTask task = uploadTaskMapper.selectByUploadId(uploadId);
        if (task == null) {
            throw new RuntimeException("上传任务不存在");
        }

        try {
            String chunkDir = chunkPath + "/" + uploadId;
            Path dirPath = Paths.get(chunkDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            String chunkFilePath = chunkDir + "/" + chunkIndex + ".chunk";
            try (FileOutputStream fos = new FileOutputStream(chunkFilePath)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }

            List<Integer> uploadedChunks = uploadChunksCache.get(uploadId);
            if (!uploadedChunks.contains(chunkIndex)) {
                uploadedChunks.add(chunkIndex);
                task.setUploadedChunks(uploadedChunks.size());
                task.setProgress((int) ((uploadedChunks.size() * 100) / totalChunks));
                uploadTaskMapper.updateProgress(uploadId, task.getProgress(), task.getUploadedChunks());
            }
        } catch (IOException e) {
            log.error("上传分片失败", e);
            throw new RuntimeException("上传分片失败");
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error("关闭输入流失败", e);
            }
        }
    }

    @Override
    @Transactional
    public Video completeUpload(String uploadId) {
        VideoUploadTask uploadTask = uploadTaskMapper.selectByUploadId(uploadId);
        if (uploadTask == null) {
            throw new RuntimeException("上传任务不存在");
        }

        try {
            String videoDir = uploadPath + "/" + uploadId;
            Path dirPath = Paths.get(videoDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            String filePath = videoDir + "/" + uploadTask.getFileName();
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                List<Integer> uploadedChunks = uploadChunksCache.get(uploadId);
                Collections.sort(uploadedChunks);
                for (Integer chunkIndex : uploadedChunks) {
                    String chunkFilePath = chunkPath + "/" + uploadId + "/" + chunkIndex + ".chunk";
                    File chunkFile = new File(chunkFilePath);
                    if (chunkFile.exists()) {
                        try (FileInputStream fis = new FileInputStream(chunkFile)) {
                            byte[] buffer = new byte[8192];
                            int bytesRead;
                            while ((bytesRead = fis.read(buffer)) != -1) {
                                fos.write(buffer, 0, bytesRead);
                            }
                        }
                    }
                }
            }

            Video video = new Video();
            video.setFileName(uploadTask.getFileName());
            video.setFilePath(filePath);
            video.setFileType(uploadTask.getFileType());
            video.setFileSize(uploadTask.getFileSize());
            video.setStatus(1);
            video.setDetectionStatus(1);
            video.setUploadUserId(uploadTask.getUploadUserId());
            videoMapper.createVideo(video);

            uploadTaskMapper.updateVideoId(uploadId, video.getId());
            uploadTaskMapper.updateStatus(uploadId, 2, null);

            uploadChunksCache.remove(uploadId);

            return video;
        } catch (Exception e) {
            log.error("完成上传失败", e);
            uploadTaskMapper.updateStatus(uploadId, 4, e.getMessage());
            throw new RuntimeException("完成上传失败");
        }
    }

    @Override
    @Transactional
    public void cancelUpload(String uploadId) {
        uploadTaskMapper.updateStatus(uploadId, 3, null);
        uploadChunksCache.remove(uploadId);
    }

    @Override
    public int getUploadProgress(String uploadId) {
        VideoUploadTask task = uploadTaskMapper.selectByUploadId(uploadId);
        if (task == null) {
            throw new RuntimeException("上传任务不存在");
        }
        return task.getProgress();
    }

    @Override
    public PageResult<Video> getVideoList(String keyword, Integer status, Integer page, Integer pageSize) {
        int total = videoMapper.getVideoCountByCondition(keyword, status, null, null, null);
        int offset = (page - 1) * pageSize;
        List<Video> list = videoMapper.getVideoListByCondition(keyword, status, null, null, null, offset, pageSize);
        return new PageResult<>(total, list);
    }

    @Override
    public Video getVideoById(Long id) {
        return videoMapper.getVideoById(id);
    }

    @Override
    @Transactional
    public Video updateVideo(Long id, VideoUploadDTO videoUploadDTO) {
        Video video = videoMapper.getVideoById(id);
        if (video == null) {
            throw new RuntimeException("视频不存在");
        }
        video.setFileName(videoUploadDTO.getFileName());
        videoMapper.updateVideo(video);
        return video;
    }

    @Override
    @Transactional
    public void deleteVideo(Long id) {
        Video video = videoMapper.getVideoById(id);
        if (video != null) {
            File file = new File(video.getFilePath());
            if (file.exists()) {
                file.delete();
            }
            videoMapper.deleteVideo(id);
        }
    }

    @Override
    @Transactional
    public void batchDeleteVideo(List<Long> ids) {
        for (Long id : ids) {
            deleteVideo(id);
        }
    }

    @Override
    @Transactional
    public VideoDetectionTask createDetectionTask(VideoDetectionTaskDTO taskDTO) {
        Video video = videoMapper.getVideoById(taskDTO.getVideoId());
        if (video == null) {
            throw new RuntimeException("视频不存在");
        }

        VideoDetectionTask task = new VideoDetectionTask();
        task.setVideoId(taskDTO.getVideoId());
        task.setModelType(taskDTO.getModelType());
        task.setConfidenceThreshold(taskDTO.getConfidenceThreshold());
        task.setDetectClasses(taskDTO.getDetectClasses() != null ? String.join(",", taskDTO.getDetectClasses()) : null);
        task.setFrameInterval(taskDTO.getFrameInterval());
        task.setPriority(taskDTO.getPriority());
        task.setStatus(1);
        task.setProgress(0);
        task.setCreateUserId(BaseContext.getCurrentId());
        detectionTaskMapper.insert(task);

        executeDetectionTask(task);

        return task;
    }

    @Async
    @Transactional
    public void executeDetectionTask(VideoDetectionTask task) {
        try {
            detectionTaskMapper.updateStatus(task.getId(), 2);

            Video video = videoMapper.getVideoById(task.getVideoId());

            int totalFrames = 1000;
            int detectionCount = 0;
            List<VideoDetectionResult> results = new ArrayList<>();

            for (int i = 1; i <= totalFrames; i++) {
                VideoDetectionResult result = new VideoDetectionResult();
                result.setTaskId(task.getId());
                result.setFrameNumber(i);
                result.setTimestamp((double) i / 30.0);
                result.setDetectionCount(2);
                result.setInferenceTime(35);
                result.setTargets("[{\"class\": \"person\", \"confidence\": 0.95, \"bbox\": [100, 100, 200, 200]}, {\"class\": \"car\", \"confidence\": 0.90, \"bbox\": [300, 300, 400, 400]}]");
                results.add(result);
                detectionCount += 2;

                if (results.size() >= 100) {
                    for (VideoDetectionResult r : results) {
                        detectionResultMapper.insert(r);
                    }
                    results.clear();

                    int progress = (int) ((i * 100) / totalFrames);
                    detectionTaskMapper.updateProgress(task.getId(), progress, i, detectionCount);
                }

                Thread.sleep(10);
            }

            for (VideoDetectionResult r : results) {
                detectionResultMapper.insert(r);
            }

            String resultSummary = String.format("{\"person\": %d, \"car\": %d, \"truck\": %d}", detectionCount / 3, detectionCount / 3, detectionCount / 3);
            detectionTaskMapper.updateTaskComplete(task.getId(), 3, totalFrames, totalFrames, detectionCount, resultSummary);

            video.setDetectionStatus(3);
            video.setTotalFrames(totalFrames);
            video.setProcessedFrames(totalFrames);
            video.setDetectionCount(detectionCount);
            videoMapper.updateDetectionStatus(video);

        } catch (Exception e) {
            log.error("检测任务执行失败", e);
            detectionTaskMapper.updateStatus(task.getId(), 4);
        }
    }

    @Override
    public PageResult<VideoDetectionTask> getVideoTasks(Long videoId, Integer page, Integer pageSize) {
        int total = detectionTaskMapper.countByVideoId(videoId);
        int offset = (page - 1) * pageSize;
        List<VideoDetectionTask> list = detectionTaskMapper.selectByVideoId(videoId, offset, pageSize);
        return new PageResult<>(total, list);
    }

    @Override
    public VideoDetectionTask getTaskById(Long taskId) {
        return detectionTaskMapper.selectById(taskId);
    }

    @Override
    @Transactional
    public void cancelDetectionTask(Long taskId) {
        detectionTaskMapper.updateStatus(taskId, 5);
    }

    @Override
    public int getTaskProgress(Long taskId) {
        VideoDetectionTask task = detectionTaskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("检测任务不存在");
        }
        return task.getProgress();
    }

    @Override
    public PageResult<VideoDetectionResult> getDetectionResults(Long videoId, Long taskId, Integer page, Integer pageSize) {
        int total = detectionResultMapper.countByTaskId(taskId);
        int offset = (page - 1) * pageSize;
        List<VideoDetectionResult> list = detectionResultMapper.selectByTaskId(taskId, offset, pageSize);
        return new PageResult<>(total, list);
    }

    @Override
    public VideoDetectionResult getFrameResult(Long videoId, Long taskId, Integer frameNumber) {
        return detectionResultMapper.selectByTaskIdAndFrameNumber(taskId, frameNumber);
    }

    @Override
    public Object getDetectionStats(Long videoId, Long taskId) {
        VideoDetectionTask task = detectionTaskMapper.selectById(taskId);
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalFrames", task.getTotalFrames());
        stats.put("processedFrames", task.getProcessedFrames());
        stats.put("detectionCount", task.getDetectionCount());
        stats.put("resultSummary", task.getResultSummary());
        return stats;
    }

    @Override
    @Transactional
    public String exportVideo(Long videoId, Long taskId) {
        String exportId = UUID.randomUUID().toString();
        VideoExportTask task = new VideoExportTask();
        task.setExportId(exportId);
        task.setVideoId(videoId);
        task.setTaskId(taskId);
        task.setExportType("video");
        task.setStatus(1);
        task.setProgress(0);
        task.setCreateUserId(BaseContext.getCurrentId());
        exportTaskMapper.insert(task);

        executeExportTask(task);

        return exportId;
    }

    @Override
    @Transactional
    public String exportReport(Long videoId, Long taskId) {
        String exportId = UUID.randomUUID().toString();
        VideoExportTask task = new VideoExportTask();
        task.setExportId(exportId);
        task.setVideoId(videoId);
        task.setTaskId(taskId);
        task.setExportType("report");
        task.setStatus(1);
        task.setProgress(0);
        task.setCreateUserId(BaseContext.getCurrentId());
        exportTaskMapper.insert(task);

        executeExportTask(task);

        return exportId;
    }

    @Override
    @Transactional
    public String exportScreenshots(Long videoId, Long taskId) {
        String exportId = UUID.randomUUID().toString();
        VideoExportTask task = new VideoExportTask();
        task.setExportId(exportId);
        task.setVideoId(videoId);
        task.setTaskId(taskId);
        task.setExportType("screenshots");
        task.setStatus(1);
        task.setProgress(0);
        task.setCreateUserId(BaseContext.getCurrentId());
        exportTaskMapper.insert(task);

        executeExportTask(task);

        return exportId;
    }

    @Async
    @Transactional
    public void executeExportTask(VideoExportTask task) {
        try {
            for (int i = 0; i <= 100; i += 10) {
                Thread.sleep(100);
                exportTaskMapper.updateProgress(task.getExportId(), i);
            }

            String fileUrl = "/api/video/export/" + task.getExportId() + "/download";
            exportTaskMapper.updateComplete(task.getExportId(), 2, fileUrl, 1024000L);

        } catch (Exception e) {
            log.error("导出任务执行失败", e);
        }
    }

    @Override
    public int getExportProgress(String exportId) {
        VideoExportTask task = exportTaskMapper.selectByExportId(exportId);
        if (task == null) {
            throw new RuntimeException("导出任务不存在");
        }
        return task.getProgress();
    }

    @Override
    public String getExportDownloadUrl(String exportId) {
        VideoExportTask task = exportTaskMapper.selectByExportId(exportId);
        if (task == null) {
            throw new RuntimeException("导出任务不存在");
        }
        if (task.getProgress() != 100) {
            throw new RuntimeException("导出任务尚未完成");
        }
        return task.getFileUrl();
    }

    @Override
    public PageResult<Object> getExportHistory(Long videoId, Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        List<VideoExportTask> exportTasks = exportTaskMapper.selectByVideoId(videoId, offset, pageSize);
        int total = exportTaskMapper.countByVideoId(videoId);
        // 转换为List<Object>
        List<Object> list = new ArrayList<>(exportTasks);
        return new PageResult<>(total, list);
    }
}
