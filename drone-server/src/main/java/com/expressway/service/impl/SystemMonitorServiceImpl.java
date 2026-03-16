package com.expressway.service.impl;

import com.expressway.result.PageResult;
import com.expressway.service.SystemMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统监控服务实现
 */
@Service
@Slf4j
public class SystemMonitorServiceImpl implements SystemMonitorService {

    // 系统异常记录
    private final Map<Long, SystemException> exceptions = new ConcurrentHashMap<>();
    private long exceptionNextId = 1;
    
    // 系统预警记录
    private final Map<Long, SystemAlert> alerts = new ConcurrentHashMap<>();
    private long alertNextId = 1;
    
    // 系统日志记录
    private final List<SystemLog> logs = Collections.synchronizedList(new ArrayList<>());
    
    // 系统性能指标
    private final Map<String, List<MetricData>> metrics = new ConcurrentHashMap<>();

    // 初始化模拟数据
    public SystemMonitorServiceImpl() {
        // 初始化系统异常
        exceptions.put(1L, new SystemException(1L, "device_offline", "high", "设备离线", "设备123456离线超过5分钟", new Date(), null, null, null));
        exceptions.put(2L, new SystemException(2L, "low_battery", "medium", "低电量", "设备789012电量低于20%", new Date(), null, null, null));
        exceptionNextId = 3;
        
        // 初始化系统预警
        alerts.put(1L, new SystemAlert(1L, "high_cpu", "warning", "CPU使用率过高", "系统CPU使用率超过80%", new Date(), null, null, null));
        alerts.put(2L, new SystemAlert(2L, "low_disk", "warning", "磁盘空间不足", "系统磁盘空间剩余不足10%", new Date(), null, null, null));
        alertNextId = 3;
        
        // 初始化系统日志
        for (int i = 0; i < 20; i++) {
            SystemLog log = new SystemLog(
                    new Date(),
                    i % 5 == 0 ? "error" : i % 3 == 0 ? "warn" : "info",
                    i % 4 == 0 ? "system" : i % 3 == 0 ? "device" : "detection",
                    "测试日志内容 " + i
            );
            logs.add(log);
        }
        
        // 初始化性能指标
        List<MetricData> cpuData = new ArrayList<>();
        List<MetricData> memoryData = new ArrayList<>();
        List<MetricData> diskData = new ArrayList<>();
        
        for (int i = 23; i >= 0; i--) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, -i);
            Date time = cal.getTime();
            
            cpuData.add(new MetricData(time, 30 + Math.random() * 50));
            memoryData.add(new MetricData(time, 40 + Math.random() * 40));
            diskData.add(new MetricData(time, 60 + Math.random() * 30));
        }
        
        metrics.put("cpu", cpuData);
        metrics.put("memory", memoryData);
        metrics.put("disk", diskData);
    }

    @Override
    public Map<String, Object> getSystemStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "running");
        status.put("uptime", "24h 30m");
        status.put("lastRestart", "2023-07-01 00:00:00");
        status.put("version", "1.0.0");
        status.put("environment", "production");
        return status;
    }

    @Override
    public Map<String, Object> getSystemResources() {
        Map<String, Object> resources = new HashMap<>();
        resources.put("cpu", Map.of(
                "usage", 45.5,
                "cores", 8,
                "load", 1.2
        ));
        resources.put("memory", Map.of(
                "total", 8192,
                "used", 3648,
                "free", 4544,
                "usage", 44.5
        ));
        resources.put("disk", Map.of(
                "total", 512000,
                "used", 204800,
                "free", 307200,
                "usage", 40.0
        ));
        resources.put("network", Map.of(
                "in", 102400,
                "out", 51200,
                "packetsIn", 1000,
                "packetsOut", 800
        ));
        return resources;
    }

    @Override
    public Map<String, Object> getSystemStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalDevices", 50);
        stats.put("onlineDevices", 45);
        stats.put("offlineDevices", 5);
        stats.put("totalTasks", 1000);
        stats.put("runningTasks", 50);
        stats.put("completedTasks", 900);
        stats.put("failedTasks", 50);
        stats.put("totalAlerts", 150);
        stats.put("unhandledAlerts", 10);
        stats.put("totalExceptions", 50);
        stats.put("unhandledExceptions", 5);
        return stats;
    }

    @Override
    public PageResult<Map<String, Object>> getDeviceStatusList(int page, int pageSize) {
        List<Map<String, Object>> devices = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Map<String, Object> device = new HashMap<>();
            device.put("deviceId", i);
            device.put("deviceName", "设备" + i);
            device.put("status", i % 5 == 0 ? "offline" : "online");
            device.put("battery", 100 - (i * 5));
            device.put("signal", 70 + (i % 30));
            device.put("temperature", 25 + (i % 10));
            device.put("lastOnline", new Date());
            devices.add(device);
        }
        int total = devices.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<Map<String, Object>> pageItems = devices.subList(start, end);
        return new PageResult<>(total, pageItems);
    }

    @Override
    public Map<String, Object> getDeviceStatusDetail(Long deviceId) {
        Map<String, Object> device = new HashMap<>();
        device.put("deviceId", deviceId);
        device.put("deviceName", "设备" + deviceId);
        device.put("status", "online");
        device.put("battery", 75);
        device.put("signal", 85);
        device.put("temperature", 28);
        device.put("location", Map.of(
                "latitude", 39.9042,
                "longitude", 116.4074,
                "altitude", 100
        ));
        device.put("speed", 5);
        device.put("direction", 90);
        device.put("lastOnline", new Date());
        device.put("statistics", Map.of(
                "totalDetection", 1000,
                "totalAlerts", 50,
                "uptime", "24h"
        ));
        return device;
    }

    @Override
    public PageResult<Map<String, Object>> getTaskStatusList(String type, Integer status, int page, int pageSize) {
        List<Map<String, Object>> tasks = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            Map<String, Object> task = new HashMap<>();
            task.put("taskId", i);
            task.put("taskName", "任务" + i);
            task.put("type", i % 3 == 0 ? "detection" : i % 2 == 0 ? "export" : "upload");
            task.put("status", i % 4 == 0 ? "failed" : i % 3 == 0 ? "completed" : i % 2 == 0 ? "processing" : "pending");
            task.put("progress", i % 4 == 0 ? 0 : i % 3 == 0 ? 100 : 50);
            task.put("createTime", new Date());
            task.put("startTime", new Date());
            task.put("endTime", i % 3 == 0 ? new Date() : null);
            tasks.add(task);
        }
        int total = tasks.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<Map<String, Object>> pageItems = tasks.subList(start, end);
        return new PageResult<>(total, pageItems);
    }

    @Override
    public Map<String, Object> getTaskStatusDetail(Long taskId) {
        Map<String, Object> task = new HashMap<>();
        task.put("taskId", taskId);
        task.put("taskName", "任务" + taskId);
        task.put("type", "detection");
        task.put("status", "processing");
        task.put("progress", 65);
        task.put("createTime", new Date());
        task.put("startTime", new Date());
        task.put("endTime", null);
        task.put("parameters", Map.of(
                "videoId", 123,
                "modelType", "yolov8",
                "confidence", 0.5
        ));
        task.put("statistics", Map.of(
                "processedFrames", 1200,
                "totalFrames", 1800,
                "detectionCount", 500
        ));
        return task;
    }

    @Override
    public PageResult<Map<String, Object>> getSystemExceptionList(String level, String type, int page, int pageSize) {
        List<Map<String, Object>> items = new ArrayList<>();
        for (SystemException exception : exceptions.values()) {
            Map<String, Object> item = new HashMap<>();
            item.put("exceptionId", exception.getExceptionId());
            item.put("type", exception.getType());
            item.put("level", exception.getLevel());
            item.put("title", exception.getTitle());
            item.put("description", exception.getDescription());
            item.put("occurTime", exception.getOccurTime());
            item.put("handler", exception.getHandler());
            item.put("handleTime", exception.getHandleTime());
            item.put("solution", exception.getSolution());
            items.add(item);
        }
        int total = items.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<Map<String, Object>> pageItems = items.subList(start, end);
        return new PageResult<>(total, pageItems);
    }

    @Override
    public Map<String, Object> getSystemExceptionDetail(Long exceptionId) {
        SystemException exception = exceptions.get(exceptionId);
        if (exception == null) {
            return new HashMap<>();
        }
        Map<String, Object> detail = new HashMap<>();
        detail.put("exceptionId", exception.getExceptionId());
        detail.put("type", exception.getType());
        detail.put("level", exception.getLevel());
        detail.put("title", exception.getTitle());
        detail.put("description", exception.getDescription());
        detail.put("occurTime", exception.getOccurTime());
        detail.put("handler", exception.getHandler());
        detail.put("handleTime", exception.getHandleTime());
        detail.put("solution", exception.getSolution());
        return detail;
    }

    @Override
    public void handleSystemException(Long exceptionId, String handler, String solution) {
        SystemException exception = exceptions.get(exceptionId);
        if (exception != null) {
            exception.setHandler(handler);
            exception.setHandleTime(new Date());
            exception.setSolution(solution);
            log.info("处理系统异常：exceptionId={}, handler={}, solution={}", exceptionId, handler, solution);
        }
    }

    @Override
    public PageResult<Map<String, Object>> getSystemLogList(String level, String type, String keyword, int page, int pageSize) {
        List<Map<String, Object>> items = new ArrayList<>();
        for (SystemLog log : logs) {
            Map<String, Object> item = new HashMap<>();
            item.put("logId", log.getLogId());
            item.put("level", log.getLevel());
            item.put("type", log.getType());
            item.put("message", log.getMessage());
            item.put("source", log.getSource());
            item.put("createTime", log.getCreateTime());
            items.add(item);
        }
        int total = items.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<Map<String, Object>> pageItems = items.subList(start, end);
        return new PageResult<>(total, pageItems);
    }

    @Override
    public PageResult<Map<String, Object>> getSystemAlertList(String level, String type, int page, int pageSize) {
        List<Map<String, Object>> items = new ArrayList<>();
        for (SystemAlert alert : alerts.values()) {
            Map<String, Object> item = new HashMap<>();
            item.put("alertId", alert.getAlertId());
            item.put("level", alert.getLevel());
            item.put("type", alert.getType());
            item.put("title", alert.getTitle());
            item.put("description", alert.getDescription());
            item.put("occurTime", alert.getOccurTime());
            item.put("handler", alert.getHandler());
            item.put("handleTime", alert.getHandleTime());
            item.put("solution", alert.getSolution());
            items.add(item);
        }
        int total = items.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<Map<String, Object>> pageItems = items.subList(start, end);
        return new PageResult<>(total, pageItems);
    }

    @Override
    public void handleSystemAlert(Long alertId, String handler, String solution) {
        SystemAlert alert = alerts.get(alertId);
        if (alert != null) {
            alert.setHandler(handler);
            alert.setHandleTime(new Date());
            alert.setSolution(solution);
            log.info("处理系统预警：alertId={}, handler={}, solution={}", alertId, handler, solution);
        }
    }

    @Override
    public Map<String, Object> getSystemMetrics(String metric, String startDate, String endDate, String interval) {
        Map<String, Object> result = new HashMap<>();
        List<MetricData> data = metrics.get(metric);
        if (data != null) {
            List<String> labels = new ArrayList<>();
            List<Double> values = new ArrayList<>();
            for (MetricData md : data) {
                labels.add(md.getTime().toString());
                values.add(md.getValue());
            }
            result.put("labels", labels);
            result.put("values", values);
        }
        return result;
    }

    @Override
    public Map<String, Object> getSystemHealthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "healthy");
        health.put("checks", Map.of(
                "system", Map.of("status", "healthy", "message", "系统运行正常"),
                "database", Map.of("status", "healthy", "message", "数据库连接正常"),
                "redis", Map.of("status", "healthy", "message", "Redis连接正常"),
                "mq", Map.of("status", "healthy", "message", "消息队列正常"),
                "storage", Map.of("status", "healthy", "message", "存储服务正常")
        ));
        health.put("lastCheck", new Date());
        return health;
    }

    @Override
    public Map<String, Object> triggerHealthCheck() {
        log.info("手动触发系统健康检查");
        return getSystemHealthCheck();
    }

    @Override
    public Map<String, Object> getMonitorConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("enabled", true);
        config.put("checkInterval", 60); // 秒
        config.put("alertThresholds", Map.of(
                "cpu", 80,
                "memory", 85,
                "disk", 90,
                "network", 95
        ));
        config.put("notification", Map.of(
                "email", true,
                "sms", false,
                "webhook", true
        ));
        return config;
    }

    @Override
    public void updateMonitorConfig(Map<String, Object> config) {
        log.info("更新系统监控配置：{}", config);
    }

    // 内部类：系统异常
    private static class SystemException {
        private Long exceptionId;
        private String type;
        private String level;
        private String title;
        private String description;
        private Date occurTime;
        private String handler;
        private Date handleTime;
        private String solution;

        public SystemException(Long exceptionId, String type, String level, String title, String description, Date occurTime, String handler, Date handleTime, String solution) {
            this.exceptionId = exceptionId;
            this.type = type;
            this.level = level;
            this.title = title;
            this.description = description;
            this.occurTime = occurTime;
            this.handler = handler;
            this.handleTime = handleTime;
            this.solution = solution;
        }

        // getter and setter
        public Long getExceptionId() {
            return exceptionId;
        }

        public void setExceptionId(Long exceptionId) {
            this.exceptionId = exceptionId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Date getOccurTime() {
            return occurTime;
        }

        public void setOccurTime(Date occurTime) {
            this.occurTime = occurTime;
        }

        public String getHandler() {
            return handler;
        }

        public void setHandler(String handler) {
            this.handler = handler;
        }

        public Date getHandleTime() {
            return handleTime;
        }

        public void setHandleTime(Date handleTime) {
            this.handleTime = handleTime;
        }

        public String getSolution() {
            return solution;
        }

        public void setSolution(String solution) {
            this.solution = solution;
        }
    }

    // 内部类：系统预警
    private static class SystemAlert {
        private Long alertId;
        private String type;
        private String level;
        private String title;
        private String description;
        private Date occurTime;
        private String handler;
        private Date handleTime;
        private String solution;

        public SystemAlert(Long alertId, String type, String level, String title, String description, Date occurTime, String handler, Date handleTime, String solution) {
            this.alertId = alertId;
            this.type = type;
            this.level = level;
            this.title = title;
            this.description = description;
            this.occurTime = occurTime;
            this.handler = handler;
            this.handleTime = handleTime;
            this.solution = solution;
        }

        // getter and setter
        public Long getAlertId() {
            return alertId;
        }

        public void setAlertId(Long alertId) {
            this.alertId = alertId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Date getOccurTime() {
            return occurTime;
        }

        public void setOccurTime(Date occurTime) {
            this.occurTime = occurTime;
        }

        public String getHandler() {
            return handler;
        }

        public void setHandler(String handler) {
            this.handler = handler;
        }

        public Date getHandleTime() {
            return handleTime;
        }

        public void setHandleTime(Date handleTime) {
            this.handleTime = handleTime;
        }

        public String getSolution() {
            return solution;
        }

        public void setSolution(String solution) {
            this.solution = solution;
        }
    }

    // 内部类：系统日志
    private static class SystemLog {
        private Long logId;
        private Date time;
        private String level;
        private String type;
        private String content;
        private String source;

        public SystemLog(Date time, String level, String type, String content) {
            this.logId = System.currentTimeMillis();
            this.time = time;
            this.level = level;
            this.type = type;
            this.content = content;
            this.source = "system";
        }

        // getter and setter
        public Long getLogId() {
            return logId;
        }

        public void setLogId(Long logId) {
            this.logId = logId;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        public Date getCreateTime() {
            return time;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMessage() {
            return content;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }

    // 内部类：性能指标数据
    private static class MetricData {
        private Date time;
        private double value;

        public MetricData(Date time, double value) {
            this.time = time;
            this.value = value;
        }

        // getter and setter
        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}