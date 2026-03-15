package com.expressway.service.impl;

import com.expressway.context.BaseContext;
import com.expressway.entity.Alert;
import com.expressway.exception.BusinessException;
import com.expressway.mapper.AlertMapper;
import com.expressway.result.PageResult;
import com.expressway.service.AlertService;
import com.expressway.vo.AlertVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 告警服务实现
 */
@Service
@Slf4j
public class AlertServiceImpl implements AlertService {

    @Resource
    private AlertMapper alertMapper;

    @Override
    public PageResult<AlertVO> getAlertList(String level, String status, String startDate, String endDate, Integer page, Integer pageSize) {
        // 计算分页偏移量
        int offset = (page - 1) * pageSize;
        // 转换级别字符串为整数
        Integer levelInt = null;
        if (level != null) {
            switch (level) {
                case "high":
                    levelInt = 3;
                    break;
                case "medium":
                    levelInt = 2;
                    break;
                case "low":
                    levelInt = 1;
                    break;
            }
        }
        // 转换状态字符串为整数
        Integer statusInt = null;
        if (status != null) {
            switch (status) {
                case "unprocessed":
                    statusInt = 0;
                    break;
                case "processed":
                    statusInt = 1;
                    break;
            }
        }
        // 获取告警列表
        List<Alert> alertList = alertMapper.getAlertList(levelInt, statusInt, offset, pageSize);
        // 获取告警总数
        int total = alertMapper.getAlertCount(levelInt, statusInt);
        // 转换为VO
        List<AlertVO> alertVOList = new ArrayList<>();
        for (Alert alert : alertList) {
            AlertVO alertVO = new AlertVO();
            alertVO.setId(alert.getId());
            alertVO.setDeviceId(alert.getDeviceId());
            alertVO.setDeviceName(alert.getDeviceName());
            alertVO.setType(alert.getType());
            alertVO.setLevel(alert.getLevel());
            alertVO.setLevelText(getLevelText(alert.getLevel()));
            alertVO.setMessage(alert.getMessage());
            alertVO.setDetectionId(alert.getDetectionId());
            alertVO.setImageUrl(alert.getImageUrl());
            alertVO.setLatitude(alert.getLatitude());
            alertVO.setLongitude(alert.getLongitude());
            alertVO.setStatus(alert.getStatus());
            alertVO.setStatusText(getStatusText(alert.getStatus()));
            alertVO.setCreateTime(alert.getCreateTime());
            alertVO.setProcessTime(alert.getProcessTime());
            alertVO.setProcessor(alert.getProcessorName());
            alertVO.setProcessNote(alert.getProcessNote());
            alertVOList.add(alertVO);
        }
        return new PageResult<>(total, alertVOList);
    }

    @Override
    public AlertVO getAlertById(Long id) {
        Alert alert = alertMapper.getAlertById(id);
        if (alert == null) {
            throw new BusinessException("告警不存在");
        }
        AlertVO alertVO = new AlertVO();
        alertVO.setId(alert.getId());
        alertVO.setDeviceId(alert.getDeviceId());
        alertVO.setDeviceName(alert.getDeviceName());
        alertVO.setType(alert.getType());
        alertVO.setLevel(alert.getLevel());
        alertVO.setLevelText(getLevelText(alert.getLevel()));
        alertVO.setMessage(alert.getMessage());
        alertVO.setDetectionId(alert.getDetectionId());
        alertVO.setImageUrl(alert.getImageUrl());
        alertVO.setLatitude(alert.getLatitude());
        alertVO.setLongitude(alert.getLongitude());
        alertVO.setStatus(alert.getStatus());
        alertVO.setStatusText(getStatusText(alert.getStatus()));
        alertVO.setCreateTime(alert.getCreateTime());
        alertVO.setProcessTime(alert.getProcessTime());
        alertVO.setProcessor(alert.getProcessorName());
        alertVO.setProcessNote(alert.getProcessNote());
        return alertVO;
    }

    @Override
    public Alert processAlert(Long id, String processNote) {
        // 检查告警是否存在
        Alert existingAlert = alertMapper.getAlertById(id);
        if (existingAlert == null) {
            throw new BusinessException("告警不存在");
        }
        // 获取当前用户ID
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            throw new BusinessException("未登录");
        }
        // 处理告警
        alertMapper.processAlert(id, 2, new Date(), userId, processNote);
        return alertMapper.getAlertById(id);
    }

    @Override
    public Alert closeAlert(Long id) {
        // 检查告警是否存在
        Alert existingAlert = alertMapper.getAlertById(id);
        if (existingAlert == null) {
            throw new BusinessException("告警不存在");
        }
        // 获取当前用户ID
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            throw new BusinessException("未登录");
        }
        // 关闭告警
        alertMapper.closeAlert(id, 3, new Date(), userId);
        return alertMapper.getAlertById(id);
    }

    @Override
    public Alert createAlert(Alert alert) {
        // 创建告警
        alertMapper.createAlert(alert);
        return alert;
    }

    /**
     * 获取告警级别文本
     * @param level 告警级别
     * @return 级别文本
     */
    private String getLevelText(Integer level) {
        switch (level) {
            case 1:
                return "高";
            case 2:
                return "中";
            case 3:
                return "低";
            default:
                return "未知";
        }
    }

    /**
     * 获取告警状态文本
     * @param status 告警状态
     * @return 状态文本
     */
    private String getStatusText(Integer status) {
        switch (status) {
            case 1:
                return "未处理";
            case 2:
                return "已处理";
            case 3:
                return "已忽略";
            default:
                return "未知";
        }
    }
}
