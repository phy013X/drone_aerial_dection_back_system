package com.expressway.service;

import com.expressway.dto.RealTimeDetectionDTO;
import com.expressway.entity.Detection;
import com.expressway.vo.DetectionHistoryVO;
import com.expressway.vo.RealTimeDetectionVO;

import java.util.List;

/**
 * 检测服务
 */
public interface DetectionService {
    /**
     * 实时检测
     * @param realTimeDetectionDTO 实时检测请求
     * @return 实时检测结果
     */
    RealTimeDetectionVO realTimeDetection(RealTimeDetectionDTO realTimeDetectionDTO);

    /**
     * 获取检测历史
     * @param deviceId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param type 检测类型
     * @param page 页码
     * @param size 每页数量
     * @return 检测历史列表
     */
    com.expressway.result.PageResult<DetectionHistoryVO> getDetectionHistory(
            Long deviceId, String startTime, String endTime, String type, Integer page, Integer size
    );

    /**
     * 获取检测详情
     * @param id 检测ID
     * @return 检测详情
     */
    Detection getDetectionById(Long id);

    /**
     * 分析检测结果
     * @param detectionId 检测ID
     * @return 分析结果
     */
    Object analyzeDetectionResult(Long detectionId);
}
