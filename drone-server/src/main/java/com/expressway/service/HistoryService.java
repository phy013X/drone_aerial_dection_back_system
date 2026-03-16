package com.expressway.service;

import com.expressway.result.PageResult;

import java.util.Map;

/**
 * 历史记录服务
 */
public interface HistoryService {
    /**
     * 获取检测历史
     * @param deviceId 设备ID
     * @param type 检测类型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param page 页码
     * @param pageSize 每页数量
     * @return 检测历史
     */
    PageResult<Map<String, Object>> getDetectionHistory(Long deviceId, String type, String startDate, String endDate, Integer page, Integer pageSize);

    /**
     * 获取检测详情
     * @param id 检测ID
     * @return 检测详情
     */
    Map<String, Object> getDetectionDetail(Long id);

    /**
     * 导出检测历史
     * @param deviceId 设备ID
     * @param type 检测类型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 导出结果
     */
    Map<String, Object> exportDetectionHistory(Long deviceId, String type, String startDate, String endDate);

    /**
     * 获取告警历史
     * @param level 告警级别
     * @param status 告警状态
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param page 页码
     * @param pageSize 每页数量
     * @return 告警历史
     */
    PageResult<Map<String, Object>> getAlertHistory(String level, String status, String startDate, String endDate, Integer page, Integer pageSize);

    /**
     * 获取告警详情
     * @param id 告警ID
     * @return 告警详情
     */
    Map<String, Object> getAlertDetail(Long id);

    /**
     * 处理告警
     * @param id 告警ID
     * @param processNote 处理备注
     * @return 处理结果
     */
    Map<String, Object> processAlert(Long id, String processNote);

    /**
     * 导出告警历史
     * @param level 告警级别
     * @param status 告警状态
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 导出结果
     */
    Map<String, Object> exportAlertHistory(String level, String status, String startDate, String endDate);

    /**
     * 获取视频记录
     * @param deviceId 设备ID
     * @param date 日期
     * @param page 页码
     * @param pageSize 每页数量
     * @return 视频记录
     */
    PageResult<Map<String, Object>> getVideoHistory(Long deviceId, String date, Integer page, Integer pageSize);

    /**
     * 获取视频详情
     * @param id 视频ID
     * @return 视频详情
     */
    Map<String, Object> getVideoDetail(Long id);

    /**
     * 下载视频
     * @param id 视频ID
     * @return 下载结果
     */
    Map<String, Object> downloadVideo(Long id);

    /**
     * 删除视频记录
     * @param id 视频ID
     * @return 删除结果
     */
    Map<String, Object> deleteVideo(Long id);
}