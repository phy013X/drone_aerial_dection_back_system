package com.expressway.service.impl;

import com.expressway.entity.SystemLog;
import com.expressway.entity.SystemConfig;
import com.expressway.mapper.SystemLogMapper;
import com.expressway.mapper.SystemConfigMapper;
import com.expressway.result.PageResult;
import com.expressway.service.SystemService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统服务实现
 */
@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

    @Resource
    private SystemConfigMapper systemConfigMapper;

    @Resource
    private SystemLogMapper systemLogMapper;

    @Override
    public SystemConfig getSystemConfig(String key) {
        return systemConfigMapper.getConfigByKey(key);
    }

    @Override
    public List<SystemConfig> getAllSystemConfigs() {
        return systemConfigMapper.getAllConfigs();
    }

    @Override
    public SystemConfig updateSystemConfig(String key, String value, String description) {
        SystemConfig config = systemConfigMapper.getConfigByKey(key);
        if (config == null) {
            config = new SystemConfig();
            config.setKey(key);
            config.setValue(value);
            config.setType("string");
            config.setDescription(description);
            config.setCreateTime(new Date());
            config.setUpdateTime(new Date());
            systemConfigMapper.createConfig(config);
        } else {
            config.setValue(value);
            config.setDescription(description);
            config.setUpdateTime(new Date());
            systemConfigMapper.updateConfig(config);
        }
        return config;
    }

    @Override
    public PageResult<SystemLog> getSystemLogs(String type, String startDate, String endDate, Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        List<SystemLog> logList = systemLogMapper.getSystemLogs(type, startDate, endDate, offset, pageSize);
        int total = systemLogMapper.getSystemLogCount(type, startDate, endDate);
        return new PageResult<>(total, logList);
    }

    @Override
    public Map<String, Object> getSystemStatus() {
        Map<String, Object> status = new java.util.HashMap<>();
        status.put("serverStatus", "running");
        status.put("aiServiceStatus", "running");
        status.put("databaseStatus", "connected");
        status.put("mqStatus", "connected");
        status.put("uptime", "24h 30m");
        status.put("cpuUsage", 25.5);
        status.put("memoryUsage", 60.2);
        status.put("diskUsage", 45.8);
        status.put("onlineDevices", 15);
        status.put("runningTasks", 8);
        status.put("systemTime", new Date());
        return status;
    }

    @Override
    public void createSystemLog(String type, String content, String operator) {
        SystemLog log = new SystemLog();
        log.setModule(type);
        log.setOperation(content);
        log.setUsername(operator);
        log.setCreateTime(new Date());
        log.setStatus(1);
        systemLogMapper.createLog(log);
    }
}
