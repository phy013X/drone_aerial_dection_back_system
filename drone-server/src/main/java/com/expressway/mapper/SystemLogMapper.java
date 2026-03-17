package com.expressway.mapper;

import com.expressway.entity.SystemLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统日志Mapper
 */
@Mapper
public interface SystemLogMapper {

    /**
     * 获取系统日志
     * @param type 日志类型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param offset 偏移量
     * @param limit 每页数量
     * @return 日志列表
     */
    List<SystemLog> getSystemLogs(@Param("type") String type, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 获取系统日志总数
     * @param type 日志类型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 日志总数
     */
    Integer getSystemLogCount(@Param("type") String type, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 创建系统日志
     * @param log 系统日志
     */
    void createLog(SystemLog log);
}
