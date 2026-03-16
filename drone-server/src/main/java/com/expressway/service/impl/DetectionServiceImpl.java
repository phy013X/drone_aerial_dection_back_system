package com.expressway.service.impl;

import com.expressway.dto.RealTimeDetectionDTO;
import com.expressway.entity.Detection;
import com.expressway.entity.DetectionResult;
import com.expressway.exception.BusinessException;
import com.expressway.mapper.DetectionMapper;
import com.expressway.result.PageResult;
import com.expressway.service.DetectionService;
import com.expressway.vo.DetectionHistoryVO;
import com.expressway.vo.RealTimeDetectionVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * 检测服务实现
 */
@Service
@Slf4j
public class DetectionServiceImpl implements DetectionService {

    @Resource
    private DetectionMapper detectionMapper;

    @Override
    public RealTimeDetectionVO realTimeDetection(RealTimeDetectionDTO realTimeDetectionDTO) {
        // 1. 解析Base64图像
        String imageBase64 = realTimeDetectionDTO.getImage();
        if (imageBase64.startsWith("data:image/jpeg;base64,")) {
            imageBase64 = imageBase64.substring(23);
        } else if (imageBase64.startsWith("data:image/png;base64,")) {
            imageBase64 = imageBase64.substring(22);
        }

        // 2. 保存图像到本地
        String imagePath = saveImage(imageBase64);
        String processedImagePath = imagePath.replace(".jpg", "_processed.jpg");

        // 3. 模拟AI检测
        List<RealTimeDetectionVO.DetectionResultVO> detectionResults = simulateDetection();

        // 4. 创建检测记录
        Detection detection = new Detection();
        detection.setDeviceId(realTimeDetectionDTO.getDeviceId());
        detection.setTimestamp(new Date());
        detection.setImageUrl(imagePath);
        detection.setProcessedImageUrl(processedImagePath);
        detection.setDetectionCount(detectionResults.size());
        detection.setModelVersion("yolov8n");
        detection.setInferenceTime(100); // 模拟推理时间
        detectionMapper.createDetection(detection);

        // 5. 保存检测结果
        for (RealTimeDetectionVO.DetectionResultVO resultVO : detectionResults) {
            DetectionResult detectionResult = new DetectionResult();
            detectionResult.setDetectionId(detection.getId());
            detectionResult.setType(resultVO.getType());
            detectionResult.setConfidence(resultVO.getConfidence());
            detectionResult.setBboxX(resultVO.getBbox().getX());
            detectionResult.setBboxY(resultVO.getBbox().getY());
            detectionResult.setBboxWidth(resultVO.getBbox().getWidth());
            detectionResult.setBboxHeight(resultVO.getBbox().getHeight());
            detectionMapper.createDetectionResult(detectionResult);
        }

        // 6. 构建返回结果
        RealTimeDetectionVO realTimeDetectionVO = new RealTimeDetectionVO();
        realTimeDetectionVO.setId(detection.getId());
        realTimeDetectionVO.setDeviceId(detection.getDeviceId());
        realTimeDetectionVO.setTimestamp(detection.getTimestamp().toString());
        realTimeDetectionVO.setResults(detectionResults);
        realTimeDetectionVO.setProcessedImage(realTimeDetectionDTO.getImage()); // 模拟处理后图像

        return realTimeDetectionVO;
    }

    @Override
    public PageResult<DetectionHistoryVO> getDetectionHistory(
            Long deviceId, String startTime, String endTime, String type, Integer page, Integer size) {
        // 计算分页偏移量
        int offset = (page - 1) * size;
        // 获取检测历史记录
        List<Detection> detectionList = detectionMapper.getDetectionHistory(deviceId, startTime, endTime, type, offset, size);
        // 获取检测历史记录总数
        int total = detectionMapper.getDetectionHistoryCount(deviceId, startTime, endTime, type);
        // 转换为VO
        List<DetectionHistoryVO> detectionHistoryVOList = new ArrayList<>();
        for (Detection detection : detectionList) {
            DetectionHistoryVO detectionHistoryVO = new DetectionHistoryVO();
            detectionHistoryVO.setId(detection.getId());
            detectionHistoryVO.setDeviceId(detection.getDeviceId());
            detectionHistoryVO.setDeviceName(detection.getDeviceName());
            detectionHistoryVO.setTimestamp(detection.getTimestamp());
            detectionHistoryVO.setDetectionCount(detection.getDetectionCount());
            // 获取检测类型列表
            List<DetectionResult> detectionResults = detectionMapper.getDetectionResultsByDetectionId(detection.getId());
            List<String> types = new ArrayList<>();
            for (DetectionResult result : detectionResults) {
                if (!types.contains(result.getType())) {
                    types.add(result.getType());
                }
            }
            detectionHistoryVO.setTypes(types);
            detectionHistoryVO.setImageUrl(detection.getImageUrl());
            detectionHistoryVO.setProcessedImageUrl(detection.getProcessedImageUrl());
            detectionHistoryVO.setLatitude(detection.getLatitude());
            detectionHistoryVO.setLongitude(detection.getLongitude());
            detectionHistoryVO.setAltitude(detection.getAltitude());
            detectionHistoryVO.setInferenceTime(detection.getInferenceTime());
            detectionHistoryVOList.add(detectionHistoryVO);
        }
        return new PageResult<>(total, detectionHistoryVOList);
    }

    @Override
    public Detection getDetectionById(Long id) {
        Detection detection = detectionMapper.getDetectionById(id);
        if (detection == null) {
            throw new BusinessException("检测记录不存在");
        }
        return detection;
    }

    @Override
    public java.util.Map<String, Object> analyzeDetectionResult(Long detectionId) {
        // 模拟分析结果
        Detection detection = detectionMapper.getDetectionById(detectionId);
        if (detection == null) {
            throw new BusinessException("检测记录不存在");
        }
        List<DetectionResult> detectionResults = detectionMapper.getDetectionResultsByDetectionId(detectionId);
        // 构建分析结果
        java.util.Map<String, Object> analysisResult = new java.util.HashMap<>();
        analysisResult.put("detectionId", detectionId);
        analysisResult.put("deviceId", detection.getDeviceId());
        analysisResult.put("timestamp", detection.getTimestamp());
        analysisResult.put("detectionCount", detection.getDetectionCount());
        // 统计各类型目标数量
        java.util.Map<String, Integer> typeCount = new java.util.HashMap<>();
        for (DetectionResult result : detectionResults) {
            typeCount.put(result.getType(), typeCount.getOrDefault(result.getType(), 0) + 1);
        }
        analysisResult.put("typeCount", typeCount);
        return analysisResult;
    }

    /**
     * 保存图像到本地
     * @param imageBase64 Base64编码的图像
     * @return 图像保存路径
     */
    private String saveImage(String imageBase64) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
            // 使用绝对路径，确保目录存在
            String directory = System.getProperty("user.dir") + "/images/";
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            String filePath = directory + fileName;
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(imageBytes);
            fos.close();
            return filePath;
        } catch (IOException e) {
            log.error("保存图像失败", e);
            throw new BusinessException("保存图像失败");
        }
    }

    /**
     * 模拟AI检测
     * @return 检测结果
     */
    private List<RealTimeDetectionVO.DetectionResultVO> simulateDetection() {
        List<RealTimeDetectionVO.DetectionResultVO> results = new ArrayList<>();
        
        // 可能的检测类型
        String[] types = {"person", "car", "truck", "bicycle", "motorcycle"};
        
        // 随机生成1-3个检测结果
        int count = 1 + (int)(Math.random() * 3);
        
        for (int i = 0; i < count; i++) {
            RealTimeDetectionVO.DetectionResultVO result = new RealTimeDetectionVO.DetectionResultVO();
            result.setId((long)(i + 1));
            
            // 随机选择检测类型
            String type = types[(int)(Math.random() * types.length)];
            result.setType(type);
            
            // 随机生成置信度（0.8-0.99）
            double confidence = 0.8 + (Math.random() * 0.19);
            result.setConfidence(confidence);
            
            // 随机生成边界框
            RealTimeDetectionVO.BoundingBox bbox = new RealTimeDetectionVO.BoundingBox();
            bbox.setX(50 + (int)(Math.random() * 300));
            bbox.setY(50 + (int)(Math.random() * 200));
            bbox.setWidth(30 + (int)(Math.random() * 100));
            bbox.setHeight(30 + (int)(Math.random() * 100));
            result.setBbox(bbox);
            
            results.add(result);
        }
        
        return results;
    }
}
