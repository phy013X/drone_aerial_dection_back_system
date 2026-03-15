-- 创建数据库
CREATE DATABASE IF NOT EXISTS drone_aerial_detection_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE drone_aerial_detection_system;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `email` VARCHAR(100) UNIQUE,
  `phone` VARCHAR(20) UNIQUE,
  `avatar` VARCHAR(255),
  `role_id` BIGINT NOT NULL,
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '1-正常，0-禁用',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_login_time` DATETIME
);

-- 角色表
CREATE TABLE IF NOT EXISTS `role` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL UNIQUE,
  `code` VARCHAR(50) NOT NULL UNIQUE,
  `description` VARCHAR(200),
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 权限表
CREATE TABLE IF NOT EXISTS `permission` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `code` VARCHAR(100) NOT NULL UNIQUE,
  `type` TINYINT NOT NULL COMMENT '1-菜单，2-按钮，3-接口',
  `parent_id` BIGINT,
  `path` VARCHAR(200),
  `icon` VARCHAR(50),
  `sort` INT NOT NULL DEFAULT 0,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 角色权限关联表
CREATE TABLE IF NOT EXISTS `role_permission` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `role_id` BIGINT NOT NULL,
  `permission_id` BIGINT NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role_id`, `permission_id`),
  FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE
);

-- 设备分组表
CREATE TABLE IF NOT EXISTS `device_group` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `parent_id` BIGINT,
  `description` VARCHAR(200),
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 设备表
CREATE TABLE IF NOT EXISTS `device` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `type` VARCHAR(50) NOT NULL,
  `model` VARCHAR(100) NOT NULL,
  `serial_number` VARCHAR(100) NOT NULL UNIQUE,
  `group_id` BIGINT,
  `unit` VARCHAR(100),
  `contact` VARCHAR(50),
  `contact_phone` VARCHAR(20),
  `location` VARCHAR(200),
  `latitude` DECIMAL(10,6),
  `longitude` DECIMAL(10,6),
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '1-在线，0-离线，2-故障，3-维护中',
  `ip` VARCHAR(50),
  `port` INT,
  `connection_type` VARCHAR(20),
  `firmware_version` VARCHAR(50),
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_online_time` DATETIME,
  FOREIGN KEY (`group_id`) REFERENCES `device_group` (`id`) ON DELETE SET NULL
);

-- 检测记录表
CREATE TABLE IF NOT EXISTS `detection` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `device_id` BIGINT NOT NULL,
  `timestamp` DATETIME NOT NULL,
  `image_url` VARCHAR(500) NOT NULL,
  `processed_image_url` VARCHAR(500),
  `latitude` DECIMAL(10,6),
  `longitude` DECIMAL(10,6),
  `altitude` DECIMAL(10,2),
  `detection_count` INT NOT NULL DEFAULT 0,
  `model_version` VARCHAR(50) NOT NULL,
  `inference_time` INT NOT NULL,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`device_id`) REFERENCES `device` (`id`) ON DELETE CASCADE
);

-- 检测结果表
CREATE TABLE IF NOT EXISTS `detection_result` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `detection_id` BIGINT NOT NULL,
  `type` VARCHAR(50) NOT NULL,
  `confidence` DECIMAL(5,4) NOT NULL,
  `bbox_x` INT NOT NULL,
  `bbox_y` INT NOT NULL,
  `bbox_width` INT NOT NULL,
  `bbox_height` INT NOT NULL,
  `track_id` INT,
  FOREIGN KEY (`detection_id`) REFERENCES `detection` (`id`) ON DELETE CASCADE
);

-- 告警表
CREATE TABLE IF NOT EXISTS `alert` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `device_id` BIGINT NOT NULL,
  `type` VARCHAR(50) NOT NULL,
  `level` TINYINT NOT NULL COMMENT '1-高，2-中，3-低',
  `message` VARCHAR(500) NOT NULL,
  `detection_id` BIGINT,
  `image_url` VARCHAR(500),
  `latitude` DECIMAL(10,6),
  `longitude` DECIMAL(10,6),
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '1-未处理，2-已处理，3-已忽略',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `process_time` DATETIME,
  `processor_id` BIGINT,
  `process_note` VARCHAR(500),
  FOREIGN KEY (`device_id`) REFERENCES `device` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`detection_id`) REFERENCES `detection` (`id`) ON DELETE SET NULL,
  FOREIGN KEY (`processor_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
);

-- 告警规则表
CREATE TABLE IF NOT EXISTS `alert_rule` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `type` VARCHAR(50) NOT NULL,
  `level` TINYINT NOT NULL COMMENT '1-高，2-中，3-低',
  `threshold` INT NOT NULL,
  `area_type` VARCHAR(20),
  `area_data` TEXT,
  `time_type` VARCHAR(20),
  `time_data` TEXT,
  `enabled` TINYINT NOT NULL DEFAULT 1 COMMENT '1-启用，0-禁用',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 告警通知表
CREATE TABLE IF NOT EXISTS `alert_notification` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `alert_id` BIGINT NOT NULL,
  `type` VARCHAR(20) NOT NULL COMMENT 'sms、email、push',
  `recipient` VARCHAR(200) NOT NULL,
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '1-待发送，2-已发送，3-发送失败',
  `send_time` DATETIME,
  `error_message` VARCHAR(500),
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`alert_id`) REFERENCES `alert` (`id`) ON DELETE CASCADE
);

-- 系统配置表
CREATE TABLE IF NOT EXISTS `system_config` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `key` VARCHAR(100) NOT NULL UNIQUE,
  `value` TEXT NOT NULL,
  `type` VARCHAR(20) NOT NULL COMMENT 'string、number、boolean、json',
  `description` VARCHAR(200),
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 系统日志表
CREATE TABLE IF NOT EXISTS `system_log` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT,
  `username` VARCHAR(50),
  `operation` VARCHAR(100) NOT NULL,
  `module` VARCHAR(50) NOT NULL,
  `method` VARCHAR(100) NOT NULL,
  `params` TEXT,
  `result` TEXT,
  `ip` VARCHAR(50),
  `user_agent` VARCHAR(500),
  `execute_time` INT,
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '1-成功，0-失败',
  `error_message` TEXT,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
);

-- AI模型表
CREATE TABLE IF NOT EXISTS `ai_model` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `version` VARCHAR(50) NOT NULL,
  `type` VARCHAR(50) NOT NULL COMMENT 'object_detection、segmentation、classification',
  `description` VARCHAR(500),
  `model_path` VARCHAR(500) NOT NULL,
  `config_path` VARCHAR(500),
  `input_size` INT NOT NULL DEFAULT 640,
  `batch_size` INT NOT NULL DEFAULT 1,
  `confidence_threshold` DECIMAL(3,2) NOT NULL DEFAULT 0.5,
  `enabled` TINYINT NOT NULL DEFAULT 1 COMMENT '1-启用，0-禁用',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 视频表
CREATE TABLE IF NOT EXISTS `video` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `device_id` BIGINT NOT NULL,
  `file_name` VARCHAR(255) NOT NULL,
  `file_path` VARCHAR(500) NOT NULL,
  `file_type` VARCHAR(50) NOT NULL,
  `file_size` BIGINT NOT NULL,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NOT NULL,
  `duration` INT NOT NULL COMMENT '视频时长（秒）',
  `resolution` VARCHAR(20),
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '1-已上传，2-处理中，3-已处理',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`device_id`) REFERENCES `device` (`id`) ON DELETE CASCADE
);

-- 初始化角色数据
INSERT INTO `role` (`name`, `code`, `description`) VALUES
('超级管理员', 'super_admin', '拥有系统所有权限'),
('管理员', 'admin', '拥有设备和用户管理权限'),
('普通用户', 'user', '拥有基本查看权限');

-- 初始化权限数据
INSERT INTO `permission` (`name`, `code`, `type`, `parent_id`, `path`, `icon`, `sort`) VALUES
('系统管理', 'system_manage', 1, NULL, '/system', 'system', 1),
('用户管理', 'user_manage', 1, 1, '/system/user', 'user', 1),
('角色管理', 'role_manage', 1, 1, '/system/role', 'role', 2),
('设备管理', 'device_manage', 1, NULL, '/device', 'device', 2),
('设备列表', 'device_list', 2, 4, '/device/list', NULL, 1),
('设备添加', 'device_add', 2, 4, '/device/add', NULL, 2),
('设备编辑', 'device_edit', 2, 4, '/device/edit', NULL, 3),
('设备删除', 'device_delete', 2, 4, '/device/delete', NULL, 4),
('监控管理', 'monitor_manage', 1, NULL, '/monitor', 'monitor', 3),
('实时监控', 'realtime_monitor', 1, 9, '/monitor/realtime', 'video', 1),
('历史数据', 'history_data', 1, NULL, '/history', 'history', 4),
('检测历史', 'detection_history', 1, 11, '/history/detection', 'detection', 1),
('视频回放', 'video_playback', 1, 11, '/history/video', 'video', 2),
('分析管理', 'analysis_manage', 1, NULL, '/analysis', 'analysis', 5),
('配置管理', 'config_manage', 1, NULL, '/config', 'config', 6),
('AI模型配置', 'ai_model_config', 1, 15, '/config/ai', 'ai', 1),
('告警配置', 'alert_config', 1, 15, '/config/alert', 'alert', 2),
('系统配置', 'system_config', 1, 15, '/config/system', 'settings', 3);

-- 初始化角色权限关联数据
-- 超级管理员拥有所有权限
INSERT INTO `role_permission` (`role_id`, `permission_id`) SELECT 1, id FROM `permission`;

-- 管理员拥有设备和监控相关权限
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES
(2, 4), (2, 5), (2, 6), (2, 7), (2, 8), (2, 9), (2, 10), (2, 11), (2, 12), (2, 13), (2, 14), (2, 15), (2, 16), (2, 17);

-- 普通用户拥有查看权限
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES
(3, 9), (3, 10), (3, 11), (3, 12), (3, 13), (3, 14);

-- 初始化超级管理员用户
INSERT INTO `user` (`username`, `password`, `name`, `email`, `phone`, `role_id`, `status`) VALUES
('admin', '$2a$10$6RvBzU8w1Q77V5m9Q5Q4Qe0G5e0G5e0G5e0G5e0G5e0G5e0G5e0G5e', '超级管理员', 'admin@example.com', '13800138000', 1, 1);

-- 初始化设备分组数据
INSERT INTO `device_group` (`name`, `parent_id`, `description`) VALUES
('默认分组', NULL, '默认设备分组'),
('东区设备', 1, '东区区域设备'),
('西区设备', 1, '西区区域设备');

-- 初始化设备数据
INSERT INTO `device` (`name`, `type`, `model`, `serial_number`, `group_id`, `unit`, `contact`, `contact_phone`, `location`, `latitude`, `longitude`, `status`, `ip`, `port`, `connection_type`, `firmware_version`) VALUES
('无人机1', 'drone', 'DJI Mavic 3', 'DRONE001', 2, '安保部', '张三', '13800138001', '东区广场', 39.9042, 116.4074, 1, '192.168.1.101', 8080, 'wifi', 'v1.0.0'),
('无人机2', 'drone', 'DJI Air 2S', 'DRONE002', 3, '安保部', '李四', '13800138002', '西区公园', 39.9142, 116.3974, 1, '192.168.1.102', 8080, 'wifi', 'v1.0.0'),
('摄像头1', 'camera', 'Hikvision DS-2CD2T45G1-I', 'CAM001', 2, '安保部', '张三', '13800138001', '东区入口', 39.9042, 116.4074, 1, '192.168.1.103', 8081, 'wired', 'v2.0.0'),
('摄像头2', 'camera', 'Hikvision DS-2CD2T45G1-I', 'CAM002', 3, '安保部', '李四', '13800138002', '西区入口', 39.9142, 116.3974, 0, '192.168.1.104', 8081, 'wired', 'v2.0.0');

-- 初始化AI模型数据
INSERT INTO `ai_model` (`name`, `version`, `type`, `description`, `model_path`, `config_path`, `input_size`, `batch_size`, `confidence_threshold`, `enabled`) VALUES
('YOLOv8n', 'v8.0', 'object_detection', '轻量级目标检测模型', '/models/yolov8n.pt', '/configs/yolov8n.yaml', 640, 1, 0.5, 1),
('YOLOv8s', 'v8.0', 'object_detection', '小型目标检测模型', '/models/yolov8s.pt', '/configs/yolov8s.yaml', 640, 1, 0.5, 0),
('DETR', 'v1.0', 'object_detection', 'Transformer-based目标检测模型', '/models/detr.pt', '/configs/detr.yaml', 800, 1, 0.5, 0);

-- 初始化告警规则数据
INSERT INTO `alert_rule` (`name`, `type`, `level`, `threshold`, `area_type`, `area_data`, `time_type`, `time_data`, `enabled`) VALUES
('入侵检测', 'intrusion', 1, 1, 'all', '{}', 'all', '{}', 1),
('设备离线', 'device_offline', 2, 1, 'all', '{}', 'all', '{}', 1),
('低电量', 'low_battery', 2, 1, 'all', '{}', 'all', '{}', 1),
('信号丢失', 'signal_loss', 3, 1, 'all', '{}', 'all', '{}', 1);

-- 初始化检测记录数据
INSERT INTO `detection` (`device_id`, `timestamp`, `image_url`, `processed_image_url`, `latitude`, `longitude`, `altitude`, `detection_count`, `model_version`, `inference_time`) VALUES
(1, '2026-03-15 10:00:00', '/images/detection_1.jpg', '/images/processed_detection_1.jpg', 39.9042, 116.4074, 100.0, 2, 'yolov8n', 100),
(1, '2026-03-15 10:01:00', '/images/detection_2.jpg', '/images/processed_detection_2.jpg', 39.9042, 116.4074, 100.0, 1, 'yolov8n', 95),
(2, '2026-03-15 10:00:00', '/images/detection_3.jpg', '/images/processed_detection_3.jpg', 39.9142, 116.3974, 120.0, 3, 'yolov8n', 105);

-- 初始化检测结果数据
INSERT INTO `detection_result` (`detection_id`, `type`, `confidence`, `bbox_x`, `bbox_y`, `bbox_width`, `bbox_height`, `track_id`) VALUES
(1, 'person', 0.95, 100, 150, 50, 100, 1),
(1, 'car', 0.92, 200, 250, 80, 40, 2),
(2, 'person', 0.90, 120, 160, 45, 90, 3),
(3, 'person', 0.88, 90, 140, 48, 95, 4),
(3, 'car', 0.91, 190, 240, 75, 38, 5),
(3, 'bicycle', 0.85, 150, 200, 60, 30, 6);

-- 初始化告警数据
INSERT INTO `alert` (`device_id`, `type`, `level`, `message`, `detection_id`, `image_url`, `latitude`, `longitude`, `status`) VALUES
(1, 'intrusion', 1, '检测到入侵行为', 1, '/images/alert_1.jpg', 39.9042, 116.4074, 1),
(2, 'device_offline', 2, '设备离线', NULL, NULL, 39.9142, 116.3974, 2),
(1, 'low_battery', 2, '设备电量低', NULL, NULL, 39.9042, 116.4074, 1);

-- 初始化视频数据
INSERT INTO `video` (`device_id`, `file_name`, `file_path`, `file_type`, `file_size`, `start_time`, `end_time`, `duration`, `resolution`, `status`) VALUES
(1, '20260315_100000.mp4', '/videos/20260315_100000.mp4', 'video/mp4', 10485760, '2026-03-15 10:00:00', '2026-03-15 10:05:00', 300, '1280x720', 1),
(2, '20260315_090000.mp4', '/videos/20260315_090000.mp4', 'video/mp4', 20971520, '2026-03-15 09:00:00', '2026-03-15 09:10:00', 600, '1920x1080', 1);

-- 初始化系统配置
INSERT INTO `system_config` (`key`, `value`, `type`, `description`) VALUES
('ai.model', 'yolov8n', 'string', 'AI模型名称'),
('ai.detectionThreshold', '0.5', 'number', '检测置信度阈值'),
('device.maxCount', '10', 'number', '最大设备数量'),
('storage.imageRetention', '90', 'number', '图像保留天数'),
('storage.videoRetention', '7', 'number', '视频保留天数'),
('alert.levels', '{"high":["intrusion","device_crash"],"medium":["device_offline","low_battery"],"low":["signal_loss"]}', 'json', '告警级别配置'),
('system.logLevel', 'INFO', 'string', '系统日志级别'),
('system.backupCycle', '24', 'number', '备份周期（小时）');
