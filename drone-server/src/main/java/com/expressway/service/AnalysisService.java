package com.expressway.service;

import com.expressway.result.PageResult;

import java.util.Map;

/**
 * 数据分析服务
 */
public interface AnalysisService {
    /**
     * 检测数量统计
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param type 检测类型
     * @return 统计数据
     */
    Map<String, Object> getDetectionCountStats(Long deviceId, String startDate, String endDate, String type);

    /**
     * 检测趋势分析
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param interval 时间间隔（day, week, month, year）
     * @return 趋势数据
     */
    Map<String, Object> getDetectionTrendStats(Long deviceId, String startDate, String endDate, String interval);

    /**
     * 检测热点分析
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param type 检测类型
     * @return 热点数据
     */
    Map<String, Object> getDetectionHotspotStats(Long deviceId, String startDate, String endDate, String type);

    /**
     * 检测类型分布
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 分布数据
     */
    Map<String, Object> getDetectionTypeDistribution(Long deviceId, String startDate, String endDate);

    /**
     * 检测时间分布
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 时间分布数据
     */
    Map<String, Object> getDetectionTimeDistribution(Long deviceId, String startDate, String endDate);

    /**
     * 告警数量统计
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param level 告警级别
     * @param type 告警类型
     * @return 统计数据
     */
    Map<String, Object> getAlertCountStats(Long deviceId, String startDate, String endDate, String level, String type);

    /**
     * 告警趋势分析
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param interval 时间间隔（day, week, month, year）
     * @return 趋势数据
     */
    Map<String, Object> getAlertTrendStats(Long deviceId, String startDate, String endDate, String interval);

    /**
     * 告警处理率统计
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 处理率数据
     */
    Map<String, Object> getAlertProcessingRateStats(Long deviceId, String startDate, String endDate);

    /**
     * 告警响应时间统计
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 响应时间数据
     */
    Map<String, Object> getAlertResponseTimeStats(Long deviceId, String startDate, String endDate);

    /**
     * 告警类型分布
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 分布数据
     */
    Map<String, Object> getAlertTypeDistribution(Long deviceId, String startDate, String endDate);

    /**
     * 告警级别分布
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 分布数据
     */
    Map<String, Object> getAlertLevelDistribution(Long deviceId, String startDate, String endDate);

    /**
     * 设备在线率统计
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 在线率数据
     */
    Map<String, Object> getDeviceOnlineRateStats(Long deviceId, String startDate, String endDate);

    /**
     * 设备故障率统计
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 故障率数据
     */
    Map<String, Object> getDeviceFailureRateStats(Long deviceId, String startDate, String endDate);

    /**
     * 设备使用时长统计
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 使用时长数据
     */
    Map<String, Object> getDeviceUsageStats(Long deviceId, String startDate, String endDate);

    /**
     * 设备电量消耗统计
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 电量消耗数据
     */
    Map<String, Object> getDeviceBatteryStats(Long deviceId, String startDate, String endDate);

    /**
     * 设备信号强度统计
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 信号强度数据
     */
    Map<String, Object> getDeviceSignalStats(Long deviceId, String startDate, String endDate);

    /**
     * 设备飞行轨迹分析
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 轨迹数据
     */
    Map<String, Object> getDeviceFlightPathStats(Long deviceId, String startDate, String endDate);

    /**
     * 设备维护成本统计
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 维护成本数据
     */
    Map<String, Object> getDeviceMaintenanceCostStats(Long deviceId, String startDate, String endDate);

    /**
     * 生成自定义统计报表
     * @param params 报表参数
     * @return 报表数据
     */
    Map<String, Object> generateCustomReport(Map<String, Object> params);

    /**
     * 导出统计报表
     * @param reportType 报表类型
     * @param params 报表参数
     * @return 导出任务ID
     */
    String exportReport(String reportType, Map<String, Object> params);

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
     * 设备统计信息
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 统计数据
     */
    Map<String, Object> getDeviceStats(Long deviceId, String startDate, String endDate);

    /**
     * 检测统计信息
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 统计数据
     */
    Map<String, Object> getDetectionStats(Long deviceId, String startDate, String endDate);

    /**
     * 告警统计信息
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 统计数据
     */
    Map<String, Object> getAlertStats(Long deviceId, String startDate, String endDate);

    /**
     * 设备排名
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 排名数据
     */
    Map<String, Object> getDeviceRanking(Long deviceId, String startDate, String endDate);

    /**
     * 检测类型
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 类型数据
     */
    Map<String, Object> getDetectionTypes(Long deviceId, String startDate, String endDate);

    /**
     * 检测时间统计
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 时间统计数据
     */
    Map<String, Object> getDetectionTimeStats(Long deviceId, String startDate, String endDate);

    /**
     * 告警处理统计
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 处理统计数据
     */
    Map<String, Object> getAlertProcessStats(Long deviceId, String startDate, String endDate);

    /**
     * 告警类型统计
     * @param deviceId 设备ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 类型统计数据
     */
    Map<String, Object> getAlertTypes(Long deviceId, String startDate, String endDate);
}