package com.expressway.service.impl;

import com.expressway.result.PageResult;
import com.expressway.service.SystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统配置服务实现
 */
@Service
@Slf4j
public class SystemConfigServiceImpl implements SystemConfigService {

    // 系统参数配置缓存
    private final Map<String, SystemParam> systemParams = new ConcurrentHashMap<>();
    
    // 设备类型配置缓存
    private final Map<Long, DeviceType> deviceTypes = new ConcurrentHashMap<>();
    private long deviceTypeNextId = 1;
    
    // 检测模型配置缓存
    private final Map<Long, DetectionModel> detectionModels = new ConcurrentHashMap<>();
    private long detectionModelNextId = 1;
    
    // 告警规则配置缓存
    private final Map<Long, AlertRule> alertRules = new ConcurrentHashMap<>();
    private long alertRuleNextId = 1;
    
    // 系统备份缓存
    private final Map<Long, SystemBackup> systemBackups = new ConcurrentHashMap<>();
    private long systemBackupNextId = 1;

    // 初始化默认配置
    public SystemConfigServiceImpl() {
        // 初始化系统参数
        systemParams.put("system.name", new SystemParam("system.name", "无人机航拍检测系统", "系统名称"));
        systemParams.put("system.version", new SystemParam("system.version", "1.0.0", "系统版本"));
        systemParams.put("system.description", new SystemParam("system.description", "无人机航拍实时检测后端系统", "系统描述"));
        systemParams.put("upload.maxSize", new SystemParam("upload.maxSize", "1024", "上传文件最大大小(MB)"));
        systemParams.put("upload.path", new SystemParam("upload.path", "./uploads", "上传文件存储路径"));
        
        // 初始化设备类型
        deviceTypes.put(1L, new DeviceType(1L, "DJI Mavic 3", "大疆御3", new HashMap<>()));
        deviceTypes.put(2L, new DeviceType(2L, "DJI Air 2S", "大疆 Air 2S", new HashMap<>()));
        deviceTypeNextId = 3;
        
        // 初始化检测模型
        detectionModels.put(1L, new DetectionModel(1L, "YOLOv8n", "./models/yolov8n.pt", "object", "YOLOv8 nano模型", new HashMap<>()));
        detectionModels.put(2L, new DetectionModel(2L, "YOLOv8s", "./models/yolov8s.pt", "object", "YOLOv8 small模型", new HashMap<>()));
        detectionModelNextId = 3;
        
        // 初始化告警规则
        alertRules.put(1L, new AlertRule(1L, "设备离线告警", "device_offline", "high", "device.status == 0", "send_email,send_sms", "设备离线告警规则", true));
        alertRules.put(2L, new AlertRule(2L, "低电量告警", "low_battery", "medium", "device.battery < 20", "send_email", "低电量告警规则", true));
        alertRuleNextId = 3;
    }

    @Override
    public String getSystemParam(String key) {
        SystemParam param = systemParams.get(key);
        return param != null ? param.getValue() : null;
    }

    @Override
    public void setSystemParam(String key, String value, String description) {
        systemParams.put(key, new SystemParam(key, value, description));
        log.info("设置系统参数：key={}, value={}, description={}", key, value, description);
    }

    @Override
    public PageResult<Map<String, Object>> getAllSystemParams(int page, int pageSize) {
        List<Map<String, Object>> items = new ArrayList<>();
        for (SystemParam param : systemParams.values()) {
            Map<String, Object> item = new HashMap<>();
            item.put("key", param.getKey());
            item.put("value", param.getValue());
            item.put("description", param.getDescription());
            items.add(item);
        }
        int total = items.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<Map<String, Object>> pageItems = items.subList(start, end);
        return new PageResult<>(total, pageItems);
    }

    @Override
    public void deleteSystemParam(String key) {
        systemParams.remove(key);
        log.info("删除系统参数：key={}", key);
    }

    @Override
    public PageResult<Map<String, Object>> getDeviceTypes(int page, int pageSize) {
        List<Map<String, Object>> items = new ArrayList<>();
        for (DeviceType type : deviceTypes.values()) {
            Map<String, Object> item = new HashMap<>();
            item.put("typeId", type.getTypeId());
            item.put("typeName", type.getTypeName());
            item.put("description", type.getDescription());
            item.put("params", type.getParams());
            items.add(item);
        }
        int total = items.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<Map<String, Object>> pageItems = items.subList(start, end);
        return new PageResult<>(total, pageItems);
    }

    @Override
    public void addDeviceType(String typeName, String description, Map<String, Object> params) {
        long typeId = deviceTypeNextId++;
        deviceTypes.put(typeId, new DeviceType(typeId, typeName, description, params));
        log.info("添加设备类型：typeId={}, typeName={}, description={}", typeId, typeName, description);
    }

    @Override
    public void updateDeviceType(Long typeId, String typeName, String description, Map<String, Object> params) {
        DeviceType deviceType = deviceTypes.get(typeId);
        if (deviceType != null) {
            deviceType.setTypeName(typeName);
            deviceType.setDescription(description);
            deviceType.setParams(params);
            log.info("更新设备类型：typeId={}, typeName={}, description={}", typeId, typeName, description);
        }
    }

    @Override
    public void deleteDeviceType(Long typeId) {
        deviceTypes.remove(typeId);
        log.info("删除设备类型：typeId={}", typeId);
    }

    @Override
    public PageResult<Map<String, Object>> getDetectionModels(int page, int pageSize) {
        List<Map<String, Object>> items = new ArrayList<>();
        for (DetectionModel model : detectionModels.values()) {
            Map<String, Object> item = new HashMap<>();
            item.put("modelId", model.getModelId());
            item.put("modelName", model.getModelName());
            item.put("modelPath", model.getModelPath());
            item.put("modelType", model.getModelType());
            item.put("description", model.getDescription());
            item.put("params", model.getParams());
            items.add(item);
        }
        int total = items.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<Map<String, Object>> pageItems = items.subList(start, end);
        return new PageResult<>(total, pageItems);
    }

    @Override
    public void addDetectionModel(String modelName, String modelPath, String modelType, String description, Map<String, Object> params) {
        long modelId = detectionModelNextId++;
        detectionModels.put(modelId, new DetectionModel(modelId, modelName, modelPath, modelType, description, params));
        log.info("添加检测模型：modelId={}, modelName={}, modelType={}", modelId, modelName, modelType);
    }

    @Override
    public void updateDetectionModel(Long modelId, String modelName, String modelPath, String modelType, String description, Map<String, Object> params) {
        DetectionModel model = detectionModels.get(modelId);
        if (model != null) {
            model.setModelName(modelName);
            model.setModelPath(modelPath);
            model.setModelType(modelType);
            model.setDescription(description);
            model.setParams(params);
            log.info("更新检测模型：modelId={}, modelName={}, modelType={}", modelId, modelName, modelType);
        }
    }

    @Override
    public void deleteDetectionModel(Long modelId) {
        detectionModels.remove(modelId);
        log.info("删除检测模型：modelId={}", modelId);
    }

    @Override
    public PageResult<Map<String, Object>> getAlertRules(int page, int pageSize) {
        List<Map<String, Object>> items = new ArrayList<>();
        for (AlertRule rule : alertRules.values()) {
            Map<String, Object> item = new HashMap<>();
            item.put("ruleId", rule.getRuleId());
            item.put("ruleName", rule.getRuleName());
            item.put("ruleType", rule.getRuleType());
            item.put("level", rule.getLevel());
            item.put("condition", rule.getCondition());
            item.put("action", rule.getAction());
            item.put("description", rule.getDescription());
            item.put("enabled", rule.isEnabled());
            items.add(item);
        }
        int total = items.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<Map<String, Object>> pageItems = items.subList(start, end);
        return new PageResult<>(total, pageItems);
    }

    @Override
    public void addAlertRule(String ruleName, String ruleType, String level, String condition, String action, String description) {
        long ruleId = alertRuleNextId++;
        alertRules.put(ruleId, new AlertRule(ruleId, ruleName, ruleType, level, condition, action, description, true));
        log.info("添加告警规则：ruleId={}, ruleName={}, ruleType={}", ruleId, ruleName, ruleType);
    }

    @Override
    public void updateAlertRule(Long ruleId, String ruleName, String ruleType, String level, String condition, String action, String description) {
        AlertRule rule = alertRules.get(ruleId);
        if (rule != null) {
            rule.setRuleName(ruleName);
            rule.setRuleType(ruleType);
            rule.setLevel(level);
            rule.setCondition(condition);
            rule.setAction(action);
            rule.setDescription(description);
            log.info("更新告警规则：ruleId={}, ruleName={}, ruleType={}", ruleId, ruleName, ruleType);
        }
    }

    @Override
    public void deleteAlertRule(Long ruleId) {
        alertRules.remove(ruleId);
        log.info("删除告警规则：ruleId={}", ruleId);
    }

    @Override
    public void enableAlertRule(Long ruleId, boolean enabled) {
        AlertRule rule = alertRules.get(ruleId);
        if (rule != null) {
            rule.setEnabled(enabled);
            log.info("{}告警规则：ruleId={}", enabled ? "启用" : "禁用", ruleId);
        }
    }

    @Override
    public Map<String, Object> getSystemStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "running");
        status.put("uptime", "24h 30m");
        status.put("memory", Map.of(
                "total", "8GB",
                "used", "4GB",
                "free", "4GB"
        ));
        status.put("cpu", Map.of(
                "usage", "30%",
                "cores", 8
        ));
        status.put("disk", Map.of(
                "total", "500GB",
                "used", "200GB",
                "free", "300GB"
        ));
        status.put("network", Map.of(
                "in", "100Mbps",
                "out", "50Mbps"
        ));
        return status;
    }

    @Override
    public Map<String, Object> getLogConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("level", "info");
        config.put("path", "./logs");
        config.put("maxFileSize", "10MB");
        config.put("maxHistory", 7);
        config.put("compression", true);
        return config;
    }

    @Override
    public void updateLogConfig(Map<String, Object> config) {
        log.info("更新日志配置：{}", config);
    }

    @Override
    public void restartSystem() {
        log.info("系统重启");
    }

    @Override
    public void shutdownSystem() {
        log.info("系统关闭");
    }

    @Override
    public String backupSystem(String backupName) {
        long backupId = systemBackupNextId++;
        String backupPath = "./backups/" + backupName + ".zip";
        systemBackups.put(backupId, new SystemBackup(backupId, backupName, backupPath, new Date()));
        log.info("系统备份：backupId={}, backupName={}, backupPath={}", backupId, backupName, backupPath);
        return backupPath;
    }

    @Override
    public void restoreSystem(String backupPath) {
        log.info("系统恢复：backupPath={}", backupPath);
    }

    @Override
    public PageResult<Map<String, Object>> getSystemBackups(int page, int pageSize) {
        List<Map<String, Object>> items = new ArrayList<>();
        for (SystemBackup backup : systemBackups.values()) {
            Map<String, Object> item = new HashMap<>();
            item.put("backupId", backup.getBackupId());
            item.put("backupName", backup.getBackupName());
            item.put("backupPath", backup.getBackupPath());
            item.put("backupSize", backup.getBackupSize());
            item.put("backupTime", backup.getBackupTime());
            items.add(item);
        }
        int total = items.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<Map<String, Object>> pageItems = items.subList(start, end);
        return new PageResult<>(total, pageItems);
    }

    @Override
    public void deleteSystemBackup(Long backupId) {
        systemBackups.remove(backupId);
        log.info("删除系统备份：backupId={}", backupId);
    }

    // 内部类：系统参数
    private static class SystemParam {
        private String key;
        private String value;
        private String description;

        public SystemParam(String key, String value, String description) {
            this.key = key;
            this.value = value;
            this.description = description;
        }

        // getter and setter
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    // 内部类：设备类型
    private static class DeviceType {
        private Long typeId;
        private String typeName;
        private String description;
        private Map<String, Object> params;

        public DeviceType(Long typeId, String typeName, String description, Map<String, Object> params) {
            this.typeId = typeId;
            this.typeName = typeName;
            this.description = description;
            this.params = params;
        }

        // getter and setter
        public Long getTypeId() {
            return typeId;
        }

        public void setTypeId(Long typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public void setParams(Map<String, Object> params) {
            this.params = params;
        }
    }

    // 内部类：检测模型
    private static class DetectionModel {
        private Long modelId;
        private String modelName;
        private String modelPath;
        private String modelType;
        private String description;
        private Map<String, Object> params;

        public DetectionModel(Long modelId, String modelName, String modelPath, String modelType, String description, Map<String, Object> params) {
            this.modelId = modelId;
            this.modelName = modelName;
            this.modelPath = modelPath;
            this.modelType = modelType;
            this.description = description;
            this.params = params;
        }

        // getter and setter
        public Long getModelId() {
            return modelId;
        }

        public void setModelId(Long modelId) {
            this.modelId = modelId;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public String getModelPath() {
            return modelPath;
        }

        public void setModelPath(String modelPath) {
            this.modelPath = modelPath;
        }

        public String getModelType() {
            return modelType;
        }

        public void setModelType(String modelType) {
            this.modelType = modelType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public void setParams(Map<String, Object> params) {
            this.params = params;
        }
    }

    // 内部类：告警规则
    private static class AlertRule {
        private Long ruleId;
        private String ruleName;
        private String ruleType;
        private String level;
        private String condition;
        private String action;
        private String description;
        private boolean enabled;

        public AlertRule(Long ruleId, String ruleName, String ruleType, String level, String condition, String action, String description, boolean enabled) {
            this.ruleId = ruleId;
            this.ruleName = ruleName;
            this.ruleType = ruleType;
            this.level = level;
            this.condition = condition;
            this.action = action;
            this.description = description;
            this.enabled = enabled;
        }

        // getter and setter
        public Long getRuleId() {
            return ruleId;
        }

        public void setRuleId(Long ruleId) {
            this.ruleId = ruleId;
        }

        public String getRuleName() {
            return ruleName;
        }

        public void setRuleName(String ruleName) {
            this.ruleName = ruleName;
        }

        public String getRuleType() {
            return ruleType;
        }

        public void setRuleType(String ruleType) {
            this.ruleType = ruleType;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    // 内部类：系统备份
    private static class SystemBackup {
        private Long backupId;
        private String backupName;
        private String backupPath;
        private Date backupTime;

        public SystemBackup(Long backupId, String backupName, String backupPath, Date backupTime) {
            this.backupId = backupId;
            this.backupName = backupName;
            this.backupPath = backupPath;
            this.backupTime = backupTime;
        }

        // getter and setter
        public Long getBackupId() {
            return backupId;
        }

        public void setBackupId(Long backupId) {
            this.backupId = backupId;
        }

        public String getBackupName() {
            return backupName;
        }

        public void setBackupName(String backupName) {
            this.backupName = backupName;
        }

        public String getBackupPath() {
            return backupPath;
        }

        public void setBackupPath(String backupPath) {
            this.backupPath = backupPath;
        }

        public Date getBackupTime() {
            return backupTime;
        }

        public void setBackupTime(Date backupTime) {
            this.backupTime = backupTime;
        }

        public String getBackupSize() {
            return "100MB";
        }
    }
}