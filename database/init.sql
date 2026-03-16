-- 创建数据库
CREATE DATABASE IF NOT EXISTS drone_aerial_detection_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE drone_aerial_detection_system;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '加密后的密码',
  `name` VARCHAR(50) NOT NULL COMMENT '姓名',
  `email` VARCHAR(100) UNIQUE COMMENT '邮箱',
  `phone` VARCHAR(20) UNIQUE COMMENT '手机号',
  `avatar` VARCHAR(255) COMMENT '头像URL',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '1-正常，0-禁用',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_login_time` DATETIME COMMENT '最后登录时间',
  INDEX `idx_username` (`username`),
  INDEX `idx_email` (`email`),
  INDEX `idx_phone` (`phone`),
  INDEX `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS `role` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
  `code` VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码',
  `description` VARCHAR(200) COMMENT '角色描述',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX `idx_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 权限表
CREATE TABLE IF NOT EXISTS `permission` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '权限名称',
  `code` VARCHAR(100) NOT NULL UNIQUE COMMENT '权限编码',
  `type` TINYINT NOT NULL COMMENT '1-菜单，2-按钮，3-接口',
  `parent_id` BIGINT COMMENT '父权限ID',
  `path` VARCHAR(200) COMMENT '路由路径',
  `icon` VARCHAR(50) COMMENT '图标',
  `sort` INT NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX `idx_code` (`code`),
  INDEX `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 角色权限关联表
CREATE TABLE IF NOT EXISTS `role_permission` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `permission_id` BIGINT NOT NULL COMMENT '权限ID',
  UNIQUE KEY `uk_role_permission` (`role_id`, `permission_id`),
  INDEX `idx_role_id` (`role_id`),
  INDEX `idx_permission_id` (`permission_id`),
  FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 登录日志表
CREATE TABLE IF NOT EXISTS `login_log` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT COMMENT '用户ID',
  `username` VARCHAR(50) COMMENT '用户名',
  `ip` VARCHAR(50) COMMENT 'IP地址',
  `location` VARCHAR(200) COMMENT '登录地点',
  `user_agent` VARCHAR(500) COMMENT '浏览器信息',
  `login_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '1-成功，0-失败',
  `error_message` VARCHAR(500) COMMENT '错误信息',
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_username` (`username`),
  INDEX `idx_login_time` (`login_time`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志表';

-- 设备分组表
CREATE TABLE IF NOT EXISTS `device_group` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL COMMENT '分组名称',
  `parent_id` BIGINT COMMENT '父分组ID',
  `description` VARCHAR(200) COMMENT '分组描述',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备分组表';

-- 设备表
CREATE TABLE IF NOT EXISTS `device` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL COMMENT '设备名称',
  `type` VARCHAR(50) NOT NULL COMMENT '设备类型',
  `model` VARCHAR(100) NOT NULL COMMENT '设备型号',
  `serial_number` VARCHAR(100) NOT NULL UNIQUE COMMENT '序列号',
  `group_id` BIGINT COMMENT '分组ID',
  `unit` VARCHAR(100) COMMENT '所属单位',
  `contact` VARCHAR(50) COMMENT '联系人',
  `contact_phone` VARCHAR(20) COMMENT '联系电话',
  `location` VARCHAR(200) COMMENT '安装位置',
  `latitude` DECIMAL(10,6) COMMENT '纬度',
  `longitude` DECIMAL(10,6) COMMENT '经度',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '1-在线，0-离线，2-故障，3-维护中',
  `ip` VARCHAR(50) COMMENT 'IP地址',
  `port` INT COMMENT '端口',
  `connection_type` VARCHAR(20) COMMENT '连接类型：WiFi、4G、有线',
  `firmware_version` VARCHAR(50) COMMENT '固件版本',
  `battery` INT COMMENT '电量百分比',
  `signal` INT COMMENT '信号强度百分比',
  `altitude` DECIMAL(10,2) COMMENT '飞行高度（米）',
  `speed` DECIMAL(10,2) COMMENT '飞行速度（米/秒）',
  `runtime` INT COMMENT '运行时间（秒）',
  `temperature` DECIMAL(5,2) COMMENT '设备温度（摄氏度）',
  `storage_used` BIGINT COMMENT '已用存储空间（字节）',
  `storage_total` BIGINT COMMENT '总存储空间（字节）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_online_time` DATETIME COMMENT '最后在线时间',
  INDEX `idx_serial_number` (`serial_number`),
  INDEX `idx_group_id` (`group_id`),
  INDEX `idx_status` (`status`),
  INDEX `idx_type` (`type`),
  FOREIGN KEY (`group_id`) REFERENCES `device_group` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备表';

-- 设备维护记录表
CREATE TABLE IF NOT EXISTS `device_maintenance` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `device_id` BIGINT NOT NULL COMMENT '设备ID',
  `type` VARCHAR(50) NOT NULL COMMENT '维护类型：routine(例行)、repair(维修)、upgrade(升级)',
  `content` TEXT COMMENT '维护内容',
  `result` VARCHAR(200) COMMENT '维护结果',
  `maintainer` VARCHAR(50) COMMENT '维护人员',
  `maintain_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '维护时间',
  `cost` DECIMAL(10,2) COMMENT '维护费用',
  `notes` VARCHAR(500) COMMENT '备注',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX `idx_device_id` (`device_id`),
  INDEX `idx_maintain_time` (`maintain_time`),
  INDEX `idx_type` (`type`),
  FOREIGN KEY (`device_id`) REFERENCES `device` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备维护记录表';

-- 检测记录表
CREATE TABLE IF NOT EXISTS `detection` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `device_id` BIGINT NOT NULL COMMENT '设备ID',
  `timestamp` DATETIME NOT NULL COMMENT '检测时间',
  `image_url` VARCHAR(500) NOT NULL COMMENT '原始图像URL',
  `processed_image_url` VARCHAR(500) COMMENT '处理后图像URL',
  `latitude` DECIMAL(10,6) COMMENT '纬度',
  `longitude` DECIMAL(10,6) COMMENT '经度',
  `altitude` DECIMAL(10,2) COMMENT '高度（米）',
  `detection_count` INT NOT NULL DEFAULT 0 COMMENT '检测目标数量',
  `model_version` VARCHAR(50) NOT NULL COMMENT '模型版本',
  `inference_time` INT NOT NULL COMMENT '推理时间（毫秒）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX `idx_device_id` (`device_id`),
  INDEX `idx_timestamp` (`timestamp`),
  INDEX `idx_device_timestamp` (`device_id`, `timestamp`),
  FOREIGN KEY (`device_id`) REFERENCES `device` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='检测记录表';

-- 检测结果表
CREATE TABLE IF NOT EXISTS `detection_result` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `detection_id` BIGINT NOT NULL COMMENT '检测记录ID',
  `type` VARCHAR(50) NOT NULL COMMENT '目标类型',
  `confidence` DECIMAL(5,4) NOT NULL COMMENT '置信度',
  `bbox_x` INT NOT NULL COMMENT '边界框X坐标',
  `bbox_y` INT NOT NULL COMMENT '边界框Y坐标',
  `bbox_width` INT NOT NULL COMMENT '边界框宽度',
  `bbox_height` INT NOT NULL COMMENT '边界框高度',
  `track_id` INT COMMENT '追踪ID',
  INDEX `idx_detection_id` (`detection_id`),
  INDEX `idx_type` (`type`),
  FOREIGN KEY (`detection_id`) REFERENCES `detection` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='检测结果表';

-- 告警表
CREATE TABLE IF NOT EXISTS `alert` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `device_id` BIGINT NOT NULL COMMENT '设备ID',
  `type` VARCHAR(50) NOT NULL COMMENT '告警类型',
  `level` TINYINT NOT NULL COMMENT '告警级别：1-高，2-中，3-低',
  `message` VARCHAR(500) NOT NULL COMMENT '告警信息',
  `detection_id` BIGINT COMMENT '检测记录ID',
  `image_url` VARCHAR(500) COMMENT '告警图像URL',
  `latitude` DECIMAL(10,6) COMMENT '纬度',
  `longitude` DECIMAL(10,6) COMMENT '经度',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '1-未处理，2-已处理，3-已忽略',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `process_time` DATETIME COMMENT '处理时间',
  `processor_id` BIGINT COMMENT '处理人ID',
  `process_note` VARCHAR(500) COMMENT '处理备注',
  INDEX `idx_device_id` (`device_id`),
  INDEX `idx_type` (`type`),
  INDEX `idx_level` (`level`),
  INDEX `idx_status` (`status`),
  INDEX `idx_create_time` (`create_time`),
  INDEX `idx_device_status` (`device_id`, `status`),
  FOREIGN KEY (`device_id`) REFERENCES `device` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`detection_id`) REFERENCES `detection` (`id`) ON DELETE SET NULL,
  FOREIGN KEY (`processor_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警表';

-- 告警规则表
CREATE TABLE IF NOT EXISTS `alert_rule` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL COMMENT '规则名称',
  `type` VARCHAR(50) NOT NULL COMMENT '告警类型',
  `level` TINYINT NOT NULL COMMENT '告警级别：1-高，2-中，3-低',
  `threshold` INT NOT NULL COMMENT '阈值',
  `area_type` VARCHAR(20) COMMENT '区域类型：all(全部)、polygon(多边形)、circle(圆形)',
  `area_data` TEXT COMMENT '区域数据（JSON格式）',
  `time_type` VARCHAR(20) COMMENT '时间类型：all(全部)、workday(工作日)、weekend(周末)、custom(自定义)',
  `time_data` TEXT COMMENT '时间数据（JSON格式）',
  `enabled` TINYINT NOT NULL DEFAULT 1 COMMENT '1-启用，0-禁用',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX `idx_type` (`type`),
  INDEX `idx_enabled` (`enabled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警规则表';

-- 告警通知表
CREATE TABLE IF NOT EXISTS `alert_notification` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `alert_id` BIGINT NOT NULL COMMENT '告警ID',
  `type` VARCHAR(20) NOT NULL COMMENT '通知类型：sms、email、push',
  `recipient` VARCHAR(200) NOT NULL COMMENT '接收人',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '1-待发送，2-已发送，3-发送失败',
  `send_time` DATETIME COMMENT '发送时间',
  `error_message` VARCHAR(500) COMMENT '错误信息',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX `idx_alert_id` (`alert_id`),
  INDEX `idx_status` (`status`),
  FOREIGN KEY (`alert_id`) REFERENCES `alert` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警通知表';

-- 系统配置表
CREATE TABLE IF NOT EXISTS `system_config` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `key` VARCHAR(100) NOT NULL UNIQUE COMMENT '配置键',
  `value` TEXT NOT NULL COMMENT '配置值',
  `type` VARCHAR(20) NOT NULL COMMENT '配置类型：string、number、boolean、json',
  `description` VARCHAR(200) COMMENT '配置描述',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX `idx_key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- 系统日志表
CREATE TABLE IF NOT EXISTS `system_log` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT COMMENT '用户ID',
  `username` VARCHAR(50) COMMENT '用户名',
  `operation` VARCHAR(100) NOT NULL COMMENT '操作名称',
  `module` VARCHAR(50) NOT NULL COMMENT '模块名称',
  `method` VARCHAR(100) NOT NULL COMMENT '方法名称',
  `params` TEXT COMMENT '请求参数',
  `result` TEXT COMMENT '返回结果',
  `ip` VARCHAR(50) COMMENT 'IP地址',
  `user_agent` VARCHAR(500) COMMENT '浏览器信息',
  `execute_time` INT COMMENT '执行时间（毫秒）',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '1-成功，0-失败',
  `error_message` TEXT COMMENT '错误信息',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX `idx_user_id` (`user_id`),
  INDEX `idx_module` (`module`),
  INDEX `idx_create_time` (`create_time`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志表';

-- AI模型表
CREATE TABLE IF NOT EXISTS `ai_model` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL COMMENT '模型名称',
  `version` VARCHAR(50) NOT NULL COMMENT '模型版本',
  `type` VARCHAR(50) NOT NULL COMMENT '模型类型：object_detection、segmentation、classification',
  `description` VARCHAR(500) COMMENT '模型描述',
  `model_path` VARCHAR(500) NOT NULL COMMENT '模型文件路径',
  `config_path` VARCHAR(500) COMMENT '配置文件路径',
  `input_size` INT NOT NULL DEFAULT 640 COMMENT '输入尺寸',
  `batch_size` INT NOT NULL DEFAULT 1 COMMENT '批处理大小',
  `confidence_threshold` DECIMAL(3,2) NOT NULL DEFAULT 0.5 COMMENT '置信度阈值',
  `nms_threshold` DECIMAL(3,2) NOT NULL DEFAULT 0.4 COMMENT 'NMS阈值',
  `enabled` TINYINT NOT NULL DEFAULT 1 COMMENT '1-启用，0-禁用',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX `idx_type` (`type`),
  INDEX `idx_enabled` (`enabled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI模型表';

-- 视频表
CREATE TABLE IF NOT EXISTS `video` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `device_id` BIGINT COMMENT '设备ID',
  `file_name` VARCHAR(255) NOT NULL COMMENT '文件名',
  `description` VARCHAR(500) COMMENT '视频描述',
  `file_path` VARCHAR(500) NOT NULL COMMENT '文件路径',
  `file_type` VARCHAR(50) NOT NULL COMMENT '文件类型',
  `file_size` BIGINT NOT NULL COMMENT '文件大小（字节）',
  `duration` INT NOT NULL COMMENT '视频时长（秒）',
  `width` INT COMMENT '视频宽度',
  `height` INT COMMENT '视频高度',
  `fps` DECIMAL(10,2) COMMENT '帧率',
  `codec` VARCHAR(50) COMMENT '编码格式',
  `format` VARCHAR(20) COMMENT '视频格式',
  `thumbnail_url` VARCHAR(500) COMMENT '缩略图URL',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '1-已上传，2-处理中，3-已处理，4-失败',
  `detection_status` TINYINT NOT NULL DEFAULT 1 COMMENT '1-待检测，2-检测中，3-已检测，4-检测失败',
  `total_frames` INT COMMENT '总帧数',
  `processed_frames` INT DEFAULT 0 COMMENT '已处理帧数',
  `detection_count` INT DEFAULT 0 COMMENT '检测目标数量',
  `upload_user_id` BIGINT COMMENT '上传用户ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `complete_time` DATETIME COMMENT '完成时间',
  INDEX `idx_device_id` (`device_id`),
  INDEX `idx_status` (`status`),
  INDEX `idx_detection_status` (`detection_status`),
  INDEX `idx_upload_user_id` (`upload_user_id`),
  INDEX `idx_create_time` (`create_time`),
  FOREIGN KEY (`device_id`) REFERENCES `device` (`id`) ON DELETE SET NULL,
  FOREIGN KEY (`upload_user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频表';

-- 视频上传任务表
CREATE TABLE IF NOT EXISTS `video_upload_task` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `upload_id` VARCHAR(100) NOT NULL UNIQUE COMMENT '上传任务ID',
  `video_id` BIGINT COMMENT '视频ID',
  `file_name` VARCHAR(255) NOT NULL COMMENT '文件名',
  `file_size` BIGINT NOT NULL COMMENT '文件大小（字节）',
  `file_type` VARCHAR(50) NOT NULL COMMENT '文件类型',
  `chunk_size` INT NOT NULL COMMENT '分片大小（字节）',
  `total_chunks` INT NOT NULL COMMENT '总分片数',
  `uploaded_chunks` INT DEFAULT 0 COMMENT '已上传分片数',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '1-上传中，2-已完成，3-已取消，4-失败',
  `progress` INT DEFAULT 0 COMMENT '上传进度（0-100）',
  `error_message` VARCHAR(500) COMMENT '错误信息',
  `upload_user_id` BIGINT COMMENT '上传用户ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `complete_time` DATETIME COMMENT '完成时间',
  INDEX `idx_upload_id` (`upload_id`),
  INDEX `idx_video_id` (`video_id`),
  INDEX `idx_status` (`status`),
  INDEX `idx_upload_user_id` (`upload_user_id`),
  FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE SET NULL,
  FOREIGN KEY (`upload_user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频上传任务表';

-- 视频检测任务表
CREATE TABLE IF NOT EXISTS `video_detection_task` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `video_id` BIGINT NOT NULL COMMENT '视频ID',
  `model_type` VARCHAR(50) NOT NULL COMMENT '模型类型',
  `confidence_threshold` DECIMAL(3,2) NOT NULL DEFAULT 0.5 COMMENT '置信度阈值',
  `detect_classes` VARCHAR(500) COMMENT '检测类别（JSON格式）',
  `frame_interval` INT NOT NULL DEFAULT 1 COMMENT '帧间隔',
  `priority` INT NOT NULL DEFAULT 1 COMMENT '优先级：1-低，2-中，3-高',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '1-待处理，2-处理中，3-已完成，4-失败，5-已取消',
  `progress` INT DEFAULT 0 COMMENT '进度（0-100）',
  `queue_position` INT COMMENT '队列位置',
  `total_frames` INT COMMENT '总帧数',
  `processed_frames` INT DEFAULT 0 COMMENT '已处理帧数',
  `detection_count` INT DEFAULT 0 COMMENT '检测目标数量',
  `current_fps` DECIMAL(10,2) COMMENT '当前处理速度（帧/秒）',
  `average_inference_time` INT COMMENT '平均推理时间（毫秒）',
  `result_summary` TEXT COMMENT '结果摘要（JSON格式）',
  `create_user_id` BIGINT COMMENT '创建用户ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `start_time` DATETIME COMMENT '开始时间',
  `end_time` DATETIME COMMENT '结束时间',
  `error_message` VARCHAR(500) COMMENT '错误信息',
  INDEX `idx_video_id` (`video_id`),
  INDEX `idx_status` (`status`),
  INDEX `idx_priority` (`priority`),
  INDEX `idx_create_time` (`create_time`),
  INDEX `idx_create_user_id` (`create_user_id`),
  FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`create_user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频检测任务表';

-- 视频检测结果表
CREATE TABLE IF NOT EXISTS `video_detection_result` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `task_id` BIGINT NOT NULL COMMENT '任务ID',
  `frame_number` INT NOT NULL COMMENT '帧号',
  `timestamp` DECIMAL(10,3) NOT NULL COMMENT '时间戳（秒）',
  `image_url` VARCHAR(500) COMMENT '原始帧图像URL',
  `processed_image_url` VARCHAR(500) COMMENT '处理后帧图像URL',
  `detection_count` INT NOT NULL DEFAULT 0 COMMENT '检测目标数量',
  `inference_time` INT COMMENT '推理时间（毫秒）',
  `targets` TEXT COMMENT '检测目标（JSON格式）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX `idx_task_id` (`task_id`),
  INDEX `idx_frame_number` (`frame_number`),
  INDEX `idx_task_frame` (`task_id`, `frame_number`),
  FOREIGN KEY (`task_id`) REFERENCES `video_detection_task` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频检测结果表';

-- 视频导出任务表
CREATE TABLE IF NOT EXISTS `video_export_task` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `export_id` VARCHAR(100) NOT NULL UNIQUE COMMENT '导出任务ID',
  `video_id` BIGINT NOT NULL COMMENT '视频ID',
  `task_id` BIGINT COMMENT '检测任务ID',
  `export_type` VARCHAR(50) NOT NULL COMMENT '导出类型：video、report、screenshots',
  `export_config` TEXT COMMENT '导出配置（JSON格式）',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '1-处理中，2-已完成，3-失败',
  `progress` INT DEFAULT 0 COMMENT '导出进度（0-100）',
  `file_url` VARCHAR(500) COMMENT '导出文件URL',
  `file_size` BIGINT COMMENT '导出文件大小（字节）',
  `error_message` VARCHAR(500) COMMENT '错误信息',
  `create_user_id` BIGINT COMMENT '创建用户ID',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `complete_time` DATETIME COMMENT '完成时间',
  INDEX `idx_export_id` (`export_id`),
  INDEX `idx_video_id` (`video_id`),
  INDEX `idx_task_id` (`task_id`),
  INDEX `idx_status` (`status`),
  INDEX `idx_create_user_id` (`create_user_id`),
  FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`task_id`) REFERENCES `video_detection_task` (`id`) ON DELETE SET NULL,
  FOREIGN KEY (`create_user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频导出任务表';

-- 插入示例数据

-- 1. 角色表数据
INSERT INTO `role` (`name`, `code`, `description`) VALUES
('超级管理员', 'super_admin', '系统最高权限管理员'),
('管理员', 'admin', '系统管理员'),
('普通用户', 'user', '普通用户');

-- 2. 权限表数据
INSERT INTO `permission` (`name`, `code`, `type`, `parent_id`, `path`, `icon`, `sort`) VALUES
-- 系统管理
('系统管理', 'system_manage', 1, NULL, '/system', 'settings', 1),
('系统状态', 'system_status', 2, 1, '/system/status', NULL, 1),
('系统配置', 'system_config', 2, 1, '/system/config', NULL, 2),
('系统日志', 'system_logs', 2, 1, '/system/logs', NULL, 3),
-- 用户管理
('用户管理', 'user_manage', 1, NULL, '/user', 'user', 2),
('用户列表', 'user_list', 2, 5, '/user/list', NULL, 1),
('用户添加', 'user_add', 2, 5, '/user/add', NULL, 2),
('用户编辑', 'user_edit', 2, 5, '/user/edit', NULL, 3),
('用户删除', 'user_delete', 2, 5, '/user/delete', NULL, 4),
-- 角色管理
('角色管理', 'role_manage', 1, NULL, '/role', 'key', 3),
('角色列表', 'role_list', 2, 10, '/role/list', NULL, 1),
('角色添加', 'role_add', 2, 10, '/role/add', NULL, 2),
('角色编辑', 'role_edit', 2, 10, '/role/edit', NULL, 3),
('角色删除', 'role_delete', 2, 10, '/role/delete', NULL, 4),
-- 设备管理
('设备管理', 'device_manage', 1, NULL, '/device', 'hardware', 4),
('设备列表', 'device_list', 2, 15, '/device/list', NULL, 1),
('设备添加', 'device_add', 2, 15, '/device/add', NULL, 2),
('设备编辑', 'device_edit', 2, 15, '/device/edit', NULL, 3),
('设备删除', 'device_delete', 2, 15, '/device/delete', NULL, 4),
('设备状态', 'device_status', 2, 15, '/device/status', NULL, 5),
-- 检测管理
('检测管理', 'detection_manage', 1, NULL, '/detection', 'search', 5),
('实时检测', 'realtime_detection', 2, 20, '/detection/realtime', NULL, 1),
('检测历史', 'detection_history', 2, 20, '/detection/history', NULL, 2),
('检测分析', 'detection_analyze', 2, 20, '/detection/analyze', NULL, 3),
-- 告警管理
('告警管理', 'alert_manage', 1, NULL, '/alert', 'bell', 6),
('告警列表', 'alert_list', 2, 25, '/alert/list', NULL, 1),
('告警处理', 'alert_process', 2, 25, '/alert/process', NULL, 2),
('告警规则', 'alert_rule', 2, 25, '/alert/rule', NULL, 3),
-- 视频管理
('视频管理', 'video_manage', 1, NULL, '/video', 'film', 7),
('视频上传', 'video_upload', 2, 30, '/video/upload', NULL, 1),
('视频列表', 'video_list', 2, 30, '/video/list', NULL, 2),
('视频检测', 'video_detect', 2, 30, '/video/detect', NULL, 3),
('视频导出', 'video_export', 2, 30, '/video/export', NULL, 4),
-- AI模型管理
('AI模型管理', 'ai_model_manage', 1, NULL, '/ai/model', 'brain', 8),
('模型列表', 'model_list', 2, 35, '/ai/model/list', NULL, 1),
('模型添加', 'model_add', 2, 35, '/ai/model/add', NULL, 2),
('模型编辑', 'model_edit', 2, 35, '/ai/model/edit', NULL, 3),
('模型删除', 'model_delete', 2, 35, '/ai/model/delete', NULL, 4);

-- 3. 角色权限关联表数据
-- 超级管理员拥有所有权限
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES
(1, 1), (1, 2), (1, 3), (1, 4),
(1, 5), (1, 6), (1, 7), (1, 8), (1, 9),
(1, 10), (1, 11), (1, 12), (1, 13), (1, 14),
(1, 15), (1, 16), (1, 17), (1, 18), (1, 19), (1, 20),
(1, 21), (1, 22), (1, 23), (1, 24),
(1, 25), (1, 26), (1, 27), (1, 28),
(1, 29), (1, 30), (1, 31), (1, 32), (1, 33),
(1, 34), (1, 35), (1, 36), (1, 37), (1, 38);

-- 管理员拥有大部分权限，除了系统配置和AI模型管理
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES
(2, 1), (2, 2), (2, 4),
(2, 5), (2, 6), (2, 7), (2, 8), (2, 9),
(2, 10), (2, 11), (2, 12), (2, 13), (2, 14),
(2, 15), (2, 16), (2, 17), (2, 18), (2, 19), (2, 20),
(2, 21), (2, 22), (2, 23), (2, 24),
(2, 25), (2, 26), (2, 27), (2, 28),
(2, 29), (2, 30), (2, 31), (2, 32), (2, 33);

-- 普通用户拥有基本权限
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES
(3, 2), (3, 4),
(3, 6),
(3, 16), (3, 20),
(3, 22), (3, 23),
(3, 26),
(3, 30), (3, 31);

-- 4. 用户表数据（密码使用加密后的字符串，实际项目中应使用bcrypt等加密方式）
INSERT INTO `user` (`username`, `password`, `name`, `email`, `phone`, `avatar`, `role_id`, `status`, `last_login_time`) VALUES
('admin', '123456', '系统管理员', 'admin@example.com', '13800138000', 'https://example.com/avatar/admin.jpg', 1, 1, '2023-07-01 10:00:00'),
('user1', '123456', '普通用户1', 'user1@example.com', '13800138001', 'https://example.com/avatar/user1.jpg', 3, 1, '2023-07-01 09:30:00'),
('user2', '123456', '普通用户2', 'user2@example.com', '13800138002', 'https://example.com/avatar/user2.jpg', 3, 1, '2023-07-01 09:00:00');

-- 5. 设备分组表数据
INSERT INTO `device_group` (`name`, `parent_id`, `description`) VALUES
('默认分组', NULL, '默认设备分组'),
('安防监控', 1, '安防监控设备'),
('交通管理', 1, '交通管理设备'),
('环境监测', 1, '环境监测设备');

-- 6. 设备表数据
INSERT INTO `device` (`name`, `type`, `model`, `serial_number`, `group_id`, `unit`, `contact`, `contact_phone`, `location`, `latitude`, `longitude`, `status`, `ip`, `port`, `connection_type`, `firmware_version`, `battery`, `signal`, `altitude`, `speed`, `runtime`, `temperature`, `storage_used`, `storage_total`, `last_online_time`) VALUES
('无人机1', '消费级', 'DJI Mavic 3', 'SN1234567890', 2, '北京市公安局', '张三', '13900139000', '北京市朝阳区', 39.9042, 116.4074, 1, '192.168.1.100', 8080, '4G', 'v1.0.0', 85, 90, 100.5, 5.2, 3600, 25.5, 1073741824, 8589934592, '2023-07-01 10:00:00'),
('无人机2', '工业级', 'DJI Air 2S', 'SN0987654321', 3, '北京市交通局', '李四', '13900139001', '北京市海淀区', 39.9609, 116.3069, 1, '192.168.1.101', 8080, 'WiFi', 'v1.1.0', 75, 85, 80.2, 4.8, 7200, 26.2, 2147483648, 8589934592, '2023-07-01 09:30:00'),
('无人机3', '消费级', 'DJI Mini 3 Pro', 'SN1122334455', 4, '北京市环保局', '王五', '13900139002', '北京市丰台区', 39.8584, 116.2861, 0, '192.168.1.102', 8080, '4G', 'v1.0.0', 60, 0, 0, 0, 0, 0, 536870912, 4294967296, '2023-06-30 18:00:00'),
('无人机4', '工业级', 'DJI Phantom 4 Pro', 'SN5544332211', 2, '北京市公安局', '赵六', '13900139003', '北京市西城区', 39.9122, 116.3667, 2, '192.168.1.103', 8080, '有线', 'v1.2.0', 0, 0, 0, 0, 0, 0, 1073741824, 8589934592, '2023-06-30 12:00:00');

-- 7. 设备维护记录表数据
INSERT INTO `device_maintenance` (`device_id`, `type`, `content`, `result`, `maintainer`, `maintain_time`, `cost`, `notes`) VALUES
(1, 'routine', '定期检查和维护', '维护完成，设备正常', '张三', '2023-06-15 10:00:00', 0, '常规维护'),
(2, 'repair', '更换电池', '维修完成，电池已更换', '李四', '2023-06-20 14:00:00', 500, '电池老化'),
(4, 'repair', '修复电机故障', '维修中', '王五', '2023-06-30 10:00:00', 1200, '电机故障');

-- 8. AI模型表数据
INSERT INTO `ai_model` (`name`, `version`, `type`, `description`, `model_path`, `config_path`, `input_size`, `batch_size`, `confidence_threshold`, `nms_threshold`, `enabled`) VALUES
('YOLOv8n', '8.0', 'object_detection', '轻量级目标检测模型', '/models/yolov8n.pt', '/models/yolov8n.yaml', 640, 1, 0.5, 0.4, 1),
('YOLOv8s', '8.0', 'object_detection', '标准目标检测模型', '/models/yolov8s.pt', '/models/yolov8s.yaml', 640, 1, 0.5, 0.4, 1),
('YOLOv8m', '8.0', 'object_detection', '中等大小目标检测模型', '/models/yolov8m.pt', '/models/yolov8m.yaml', 640, 1, 0.5, 0.4, 0);

-- 9. 告警规则表数据
INSERT INTO `alert_rule` (`name`, `type`, `level`, `threshold`, `area_type`, `area_data`, `time_type`, `time_data`, `enabled`) VALUES
('人员入侵检测', 'intrusion', 1, 1, 'all', NULL, 'all', NULL, 1),
('车辆聚集检测', 'vehicle_cluster', 2, 5, 'all', NULL, 'all', NULL, 1),
('设备离线检测', 'device_offline', 2, 1, 'all', NULL, 'all', NULL, 1),
('电量低检测', 'low_battery', 3, 20, 'all', NULL, 'all', NULL, 1);

-- 10. 系统配置表数据
INSERT INTO `system_config` (`key`, `value`, `type`, `description`) VALUES
('ai_model.default', 'yolov8n', 'string', '默认AI模型'),
('detection.confidence_threshold', '0.5', 'number', '默认检测置信度阈值'),
('detection.max_devices', '10', 'number', '最大设备数量'),
('storage.image_retention', '30', 'number', '图像保留天数'),
('storage.video_retention', '7', 'number', '视频保留天数'),
('storage.max_storage', '107374182400', 'number', '最大存储容量（字节）'),
('alert.notification.enabled', 'true', 'boolean', '是否启用告警通知'),
('alert.notification.email', 'admin@example.com', 'string', '告警通知邮箱'),
('system.max_concurrent_tasks', '5', 'number', '最大并发任务数'),
('system.log_level', 'info', 'string', '系统日志级别');

-- 11. 检测记录表数据
INSERT INTO `detection` (`device_id`, `timestamp`, `image_url`, `processed_image_url`, `latitude`, `longitude`, `altitude`, `detection_count`, `model_version`, `inference_time`) VALUES
(1, '2023-07-01 10:00:00', 'https://example.com/images/1.jpg', 'https://example.com/images/processed/1.jpg', 39.9042, 116.4074, 100.5, 2, 'yolov8n', 35),
(1, '2023-07-01 10:01:00', 'https://example.com/images/2.jpg', 'https://example.com/images/processed/2.jpg', 39.9043, 116.4075, 100.2, 1, 'yolov8n', 32),
(2, '2023-07-01 09:30:00', 'https://example.com/images/3.jpg', 'https://example.com/images/processed/3.jpg', 39.9609, 116.3069, 80.2, 3, 'yolov8n', 38);

-- 12. 检测结果表数据
INSERT INTO `detection_result` (`detection_id`, `type`, `confidence`, `bbox_x`, `bbox_y`, `bbox_width`, `bbox_height`, `track_id`) VALUES
(1, 'person', 0.95, 100, 150, 50, 100, 1),
(1, 'car', 0.92, 200, 250, 80, 40, 2),
(2, 'person', 0.90, 150, 180, 45, 90, 3),
(3, 'car', 0.93, 120, 200, 70, 35, 4),
(3, 'car', 0.91, 190, 220, 75, 38, 5),
(3, 'person', 0.88, 280, 190, 40, 85, 6);

-- 13. 告警表数据
INSERT INTO `alert` (`device_id`, `type`, `level`, `message`, `detection_id`, `image_url`, `latitude`, `longitude`, `status`, `process_time`, `processor_id`, `process_note`) VALUES
(1, 'intrusion', 1, '检测到入侵人员', 1, 'https://example.com/images/1.jpg', 39.9042, 116.4074, 2, '2023-07-01 10:05:00', 1, '已确认，为工作人员'),
(3, 'device_offline', 2, '设备离线', NULL, NULL, 39.8584, 116.2861, 1, NULL, NULL, NULL),
(4, 'device_fault', 2, '设备故障', NULL, NULL, 39.9122, 116.3667, 1, NULL, NULL, NULL);

-- 14. 告警通知表数据
INSERT INTO `alert_notification` (`alert_id`, `type`, `recipient`, `status`, `send_time`, `error_message`) VALUES
(1, 'email', 'admin@example.com', 2, '2023-07-01 10:00:05', NULL),
(1, 'sms', '13800138000', 2, '2023-07-01 10:00:06', NULL),
(2, 'email', 'admin@example.com', 2, '2023-07-01 09:30:00', NULL);

-- 15. 视频表数据
INSERT INTO `video` (`device_id`, `file_name`, `description`, `file_path`, `file_type`, `file_size`, `duration`, `width`, `height`, `fps`, `codec`, `format`, `thumbnail_url`, `status`, `detection_status`, `total_frames`, `processed_frames`, `detection_count`, `upload_user_id`, `complete_time`) VALUES
(1, 'drone_video_001.mp4', '安防监控视频', '/videos/drone_video_001.mp4', 'video/mp4', 2147483648, 300, 1920, 1080, 30.00, 'h264', 'mp4', 'https://example.com/videos/thumbnails/001.jpg', 3, 3, 9000, 9000, 1250, 1, '2023-07-01 09:00:00'),
(2, 'traffic_video_001.mp4', '交通监控视频', '/videos/traffic_video_001.mp4', 'video/mp4', 1073741824, 150, 1280, 720, 25.00, 'h264', 'mp4', 'https://example.com/videos/thumbnails/002.jpg', 3, 3, 3750, 3750, 850, 1, '2023-07-01 08:30:00'),
(1, 'test_video_001.mp4', '测试视频', '/videos/test_video_001.mp4', 'video/mp4', 536870912, 60, 1920, 1080, 30.00, 'h264', 'mp4', 'https://example.com/videos/thumbnails/003.jpg', 3, 1, 1800, 0, 0, 2, '2023-07-01 10:00:00');

-- 16. 视频上传任务表数据
INSERT INTO `video_upload_task` (`upload_id`, `video_id`, `file_name`, `file_size`, `file_type`, `chunk_size`, `total_chunks`, `uploaded_chunks`, `status`, `progress`, `upload_user_id`, `complete_time`) VALUES
('upload_1234567890', 1, 'drone_video_001.mp4', 2147483648, 'video/mp4', 10485760, 205, 205, 2, 100, 1, '2023-07-01 08:50:00'),
('upload_0987654321', 2, 'traffic_video_001.mp4', 1073741824, 'video/mp4', 10485760, 103, 103, 2, 100, 1, '2023-07-01 08:20:00'),
('upload_1122334455', 3, 'test_video_001.mp4', 536870912, 'video/mp4', 10485760, 52, 52, 2, 100, 2, '2023-07-01 09:50:00');

-- 17. 视频检测任务表数据
INSERT INTO `video_detection_task` (`video_id`, `model_type`, `confidence_threshold`, `detect_classes`, `frame_interval`, `priority`, `status`, `progress`, `total_frames`, `processed_frames`, `detection_count`, `current_fps`, `average_inference_time`, `result_summary`, `create_user_id`, `start_time`, `end_time`) VALUES
(1, 'yolov8n', 0.5, '["person", "car", "truck"]', 1, 2, 3, 100, 9000, 9000, 1250, 30.00, 35, '{"person": 450, "car": 700, "truck": 100}', 1, '2023-07-01 09:00:00', '2023-07-01 09:50:00'),
(2, 'yolov8n', 0.5, '["car", "truck", "bus"]', 1, 2, 3, 100, 3750, 3750, 850, 25.00, 32, '{"car": 600, "truck": 150, "bus": 100}', 1, '2023-07-01 08:30:00', '2023-07-01 08:45:00'),
(3, 'yolov8s', 0.5, '["person", "car"]', 1, 1, 1, 0, 1800, 0, 0, NULL, NULL, NULL, 2, '2023-07-01 10:00:00', NULL);

-- 18. 视频检测结果表数据
INSERT INTO `video_detection_result` (`task_id`, `frame_number`, `timestamp`, `image_url`, `processed_image_url`, `detection_count`, `inference_time`, `targets`) VALUES
(1, 100, 3.333, 'https://example.com/videos/frames/1/100.jpg', 'https://example.com/videos/processed/1/100.jpg', 3, 35, '[{"type": "person", "confidence": 0.95, "bbox": {"x": 100, "y": 150, "width": 50, "height": 100}, "track_id": 1}, {"type": "car", "confidence": 0.92, "bbox": {"x": 200, "y": 250, "width": 80, "height": 40}, "track_id": 2}, {"type": "car", "confidence": 0.88, "bbox": {"x": 300, "y": 230, "width": 75, "height": 38}, "track_id": 3}]'),
(1, 200, 6.667, 'https://example.com/videos/frames/1/200.jpg', 'https://example.com/videos/processed/1/200.jpg', 2, 33, '[{"type": "person", "confidence": 0.93, "bbox": {"x": 120, "y": 160, "width": 45, "height": 95}, "track_id": 1}, {"type": "car", "confidence": 0.90, "bbox": {"x": 220, "y": 260, "width": 78, "height": 39}, "track_id": 2}]'),
(2, 50, 2.000, 'https://example.com/videos/frames/2/50.jpg', 'https://example.com/videos/processed/2/50.jpg', 4, 38, '[{"type": "car", "confidence": 0.94, "bbox": {"x": 100, "y": 200, "width": 70, "height": 35}, "track_id": 1}, {"type": "car", "confidence": 0.92, "bbox": {"x": 180, "y": 210, "width": 72, "height": 36}, "track_id": 2}, {"type": "truck", "confidence": 0.89, "bbox": {"x": 280, "y": 190, "width": 90, "height": 45}, "track_id": 3}, {"type": "bus", "confidence": 0.87, "bbox": {"x": 380, "y": 180, "width": 100, "height": 48}, "track_id": 4}]');

-- 19. 视频导出任务表数据
INSERT INTO `video_export_task` (`export_id`, `video_id`, `task_id`, `export_type`, `export_config`, `status`, `progress`, `file_url`, `file_size`, `create_user_id`, `complete_time`) VALUES
('export_1234567890', 1, 1, 'video', '{"include_detection": true, "output_format": "mp4"}', 2, 100, 'https://example.com/exports/video_1.mp4', 2684354560, 1, '2023-07-01 10:00:00'),
('export_0987654321', 1, 1, 'report', '{"format": "excel", "include_summary": true}', 2, 100, 'https://example.com/exports/report_1.xlsx', 1048576, 1, '2023-07-01 10:05:00'),
('export_1122334455', 2, 2, 'screenshots', '{"interval": 10, "include_detection": true}', 2, 100, 'https://example.com/exports/screenshots_2.zip', 52428800, 1, '2023-07-01 09:00:00');

-- 20. 登录日志表数据
INSERT INTO `login_log` (`user_id`, `username`, `ip`, `location`, `user_agent`, `login_time`, `status`, `error_message`) VALUES
(1, 'admin', '192.168.1.1', '北京市', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36', '2023-07-01 10:00:00', 1, NULL),
(2, 'user1', '192.168.1.2', '上海市', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36', '2023-07-01 09:30:00', 1, NULL),
(3, 'user2', '192.168.1.3', '广州市', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36', '2023-07-01 09:00:00', 1, NULL);

-- 21. 系统日志表数据
INSERT INTO `system_log` (`user_id`, `username`, `operation`, `module`, `method`, `params`, `result`, `ip`, `user_agent`, `execute_time`, `status`, `error_message`) VALUES
(1, 'admin', '登录', '认证', 'login', '{"username": "admin"}', '{"token": "xxx"}', '192.168.1.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36', 120, 1, NULL),
(1, 'admin', '添加设备', '设备管理', 'addDevice', '{"name": "无人机5", "type": "消费级", "model": "DJI Mini 2", "serialNumber": "SN9988776655"}', '{"id": 5, "name": "无人机5"}', '192.168.1.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36', 350, 1, NULL),
(2, 'user1', '上传视频', '视频管理', 'uploadVideo', '{"fileName": "test.mp4", "fileSize": 104857600}', '{"id": 4, "fileName": "test.mp4"}', '192.168.1.2', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36', 1500, 1, NULL);
