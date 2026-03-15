package com.expressway.mapper;

import com.expressway.entity.Alert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 告警Mapper
 */
@Mapper
public interface AlertMapper {
    /**
     * 创建告警
     * @param alert 告警信息
     */
    void createAlert(Alert alert);

    /**
     * 获取告警列表
     * @param level 告警级别
     * @param status 告警状态
     * @param page 页码
     * @param size 每页数量
     * @return 告警列表
     */
    List<Alert> getAlertList(
            @Param("level") Integer level,
            @Param("status") Integer status,
            @Param("page") Integer page,
            @Param("size") Integer size
    );

    /**
     * 获取告警总数
     * @param level 告警级别
     * @param status 告警状态
     * @return 告警总数
     */
    Integer getAlertCount(
            @Param("level") Integer level,
            @Param("status") Integer status
    );

    /**
     * 获取告警详情
     * @param id 告警ID
     * @return 告警详情
     */
    Alert getAlertById(Long id);

    /**
     * 处理告警
     * @param id 告警ID
     * @param status 告警状态
     * @param processTime 处理时间
     * @param processorId 处理人ID
     * @param processNote 处理备注
     */
    void processAlert(
            @Param("id") Long id,
            @Param("status") Integer status,
            @Param("processTime") java.util.Date processTime,
            @Param("processorId") Long processorId,
            @Param("processNote") String processNote
    );

    /**
     * 关闭告警
     * @param id 告警ID
     * @param status 告警状态
     * @param processTime 处理时间
     * @param processorId 处理人ID
     */
    void closeAlert(
            @Param("id") Long id,
            @Param("status") Integer status,
            @Param("processTime") java.util.Date processTime,
            @Param("processorId") Long processorId
    );
}
