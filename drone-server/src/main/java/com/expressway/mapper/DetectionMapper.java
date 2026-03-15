package com.expressway.mapper;

import com.expressway.entity.Detection;
import com.expressway.entity.DetectionResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 检测Mapper
 */
@Mapper
public interface DetectionMapper {
    /**
     * 创建检测记录
     * @param detection 检测记录
     */
    void createDetection(Detection detection);

    /**
     * 获取检测记录详情
     * @param id 检测ID
     * @return 检测记录
     */
    Detection getDetectionById(Long id);

    /**
     * 获取检测历史记录
     * @param deviceId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param type 检测类型
     * @param page 页码
     * @param size 每页数量
     * @return 检测历史记录列表
     */
    List<Detection> getDetectionHistory(
            @Param("deviceId") Long deviceId,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("type") String type,
            @Param("page") Integer page,
            @Param("size") Integer size
    );

    /**
     * 获取检测历史记录总数
     * @param deviceId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param type 检测类型
     * @return 检测历史记录总数
     */
    Integer getDetectionHistoryCount(
            @Param("deviceId") Long deviceId,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("type") String type
    );

    /**
     * 创建检测结果
     * @param detectionResult 检测结果
     */
    void createDetectionResult(DetectionResult detectionResult);

    /**
     * 获取检测结果列表
     * @param detectionId 检测ID
     * @return 检测结果列表
     */
    List<DetectionResult> getDetectionResultsByDetectionId(Long detectionId);
}
