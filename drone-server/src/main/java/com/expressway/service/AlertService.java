package com.expressway.service;

import com.expressway.entity.Alert;
import com.expressway.vo.AlertVO;

/**
 * 告警服务
 */
public interface AlertService {
    /**
     * 获取告警列表
     * @param level 告警级别
     * @param status 告警状态
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param page 页码
     * @param pageSize 每页数量
     * @return 告警列表
     */
    com.expressway.result.PageResult<AlertVO> getAlertList(String level, String status, String startDate, String endDate, Integer page, Integer pageSize);

    /**
     * 获取告警详情
     * @param id 告警ID
     * @return 告警详情
     */
    AlertVO getAlertById(Long id);

    /**
     * 处理告警
     * @param id 告警ID
     * @param processNote 处理备注
     * @return 处理结果
     */
    Alert processAlert(Long id, String processNote);

    /**
     * 关闭告警
     * @param id 告警ID
     * @return 关闭结果
     */
    Alert closeAlert(Long id);

    /**
     * 创建告警
     * @param alert 告警信息
     * @return 告警信息
     */
    Alert createAlert(Alert alert);
}
