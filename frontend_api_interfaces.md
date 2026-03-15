# 前端API接口定义

## 通用说明

### 基础URL
```
http://localhost:8080/api
```

### 响应格式
所有接口返回的数据格式应包含以下字段：
```json
{
  "code": 1,        // 状态码：1或200表示成功，其他表示失败
  "message": "成功", // 状态描述
  "data": {}        // 业务数据
}
```

### 认证方式
- 除登录/注册接口外，所有接口需要在请求头中添加：`Authorization: Bearer {token}`
- token 通过登录接口获取

---

## 1. 认证相关接口

### 1.1 用户登录
- **路径**：`/auth/login`
- **方法**：POST
- **请求体**：
```json
{
  "username": "admin",
  "password": "123456"
}
```
- **响应数据**：
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "username": "admin",
    "nickname": "管理员",
    "avatar": "https://example.com/avatar.jpg",
    "email": "admin@example.com",
    "phone": "13800138000",
    "role": "admin"
  }
}
```

### 1.2 用户注册
- **路径**：`/auth/register`
- **方法**：POST
- **请求体**：
```json
{
  "username": "user1",
  "password": "123456",
  "email": "user1@example.com",
  "phone": "13800138001"
}
```
- **响应数据**：同登录接口

### 1.3 获取用户信息
- **路径**：`/auth/user`
- **方法**：GET
- **响应数据**：
```json
{
  "id": 1,
  "username": "admin",
  "nickname": "管理员",
  "avatar": "https://example.com/avatar.jpg",
  "email": "admin@example.com",
  "phone": "13800138000",
  "role": "admin",
  "createTime": "2024-01-15 10:30:00",
  "lastLoginTime": "2024-03-15 14:25:30"
}
```

### 1.4 更新用户信息
- **路径**：`/auth/user`
- **方法**：PUT
- **请求体**：
```json
{
  "nickname": "管理员",
  "email": "admin@example.com",
  "phone": "13800138000",
  "avatar": "https://example.com/avatar.jpg"
}
```
- **响应数据**：更新后的用户信息

---

## 2. 设备相关接口

### 2.1 获取设备列表
- **路径**：`/devices`
- **方法**：GET
- **查询参数**：
    - `page`: 页码，默认1
    - `pageSize`: 每页数量，默认10
    - `keyword`: 搜索关键词（可选）
    - `status`: 状态筛选（可选）：online/offline/fault/maintenance
- **响应数据**：
```json
{
  "list": [
    {
      "id": 1,
      "name": "无人机-001",
      "type": "DJI Mavic 3",
      "serialNumber": "SN123456789",
      "status": "online",
      "location": "北京市朝阳区",
      "organization": "安防监控中心",
      "contactPerson": "张三",
      "contactPhone": "13800138000",
      "deviceType": "consumer",
      "remark": "主要监控区域",
      "createTime": "2024-01-15 10:30:00",
      "lastOnlineTime": "2024-03-15 14:25:30",
      "battery": 85,
      "signal": 92,
      "altitude": 120,
      "runtime": "2h30m"
    }
  ],
  "total": 100,
  "page": 1,
  "pageSize": 10
}
```

**字段说明**：
| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | number | 设备ID |
| name | string | 设备名称 |
| type | string | 设备型号 |
| serialNumber | string | 序列号 |
| status | string | 状态：online(在线)/offline(离线)/fault(故障)/maintenance(维护中) |
| location | string | 安装位置 |
| organization | string | 所属单位 |
| contactPerson | string | 联系人 |
| contactPhone | string | 联系电话 |
| deviceType | string | 设备类型：consumer(消费级)/industrial(工业级)/military(军用级) |
| remark | string | 备注 |
| createTime | string | 创建时间 |
| lastOnlineTime | string | 最后在线时间 |
| battery | number | 电量百分比 |
| signal | number | 信号强度百分比 |
| altitude | number | 飞行高度(米) |
| runtime | string | 运行时间 |

### 2.2 获取设备详情
- **路径**：`/devices/{id}`
- **方法**：GET
- **响应数据**：单个设备对象（同设备列表中的对象）

### 2.3 添加设备
- **路径**：`/devices`
- **方法**：POST
- **请求体**：
```json
{
  "name": "无人机-001",
  "type": "DJI Mavic 3",
  "serialNumber": "SN123456789",
  "location": "北京市朝阳区",
  "organization": "安防监控中心",
  "contactPerson": "张三",
  "contactPhone": "13800138000",
  "deviceType": "consumer",
  "remark": "主要监控区域"
}
```
- **响应数据**：新创建的设备对象（包含id和createTime）

### 2.4 更新设备
- **路径**：`/devices/{id}`
- **方法**：PUT
- **请求体**：同添加设备
- **响应数据**：更新后的设备对象

### 2.5 删除设备
- **路径**：`/devices/{id}`
- **方法**：DELETE
- **响应数据**：
```json
{
  "success": true
}
```

### 2.6 更新设备状态
- **路径**：`/devices/{id}/status`
- **方法**：POST
- **请求体**：
```json
{
  "status": "online"
}
```
- **响应数据**：更新后的设备对象

---

## 3. 告警相关接口

### 3.1 获取告警列表
- **路径**：`/alerts`
- **方法**：GET
- **查询参数**：
    - `page`: 页码
    - `pageSize`: 每页数量
    - `level`: 告警级别（high/medium/low）
    - `status`: 状态（unprocessed/processed）
    - `startDate`: 开始日期
    - `endDate`: 结束日期
- **响应数据**：
```json
{
  "list": [
    {
      "id": 1,
      "deviceId": 1,
      "deviceName": "无人机-001",
      "level": "high",
      "type": "intrusion",
      "message": "检测到可疑人员进入监控区域",
      "status": "unprocessed",
      "createTime": "2024-03-15 14:32:10",
      "processTime": null,
      "processor": null,
      "processNote": null,
      "imageUrl": "https://example.com/alert/1.jpg"
    }
  ],
  "total": 50
}
```

**字段说明**：
| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | number | 告警ID |
| deviceId | number | 设备ID |
| deviceName | string | 设备名称 |
| level | string | 级别：high(高)/medium(中)/low(低) |
| type | string | 类型：intrusion(入侵)/device_offline(设备离线)/low_battery(电量不足)/signal_loss(信号丢失)/abnormal_behavior(异常行为) |
| message | string | 告警内容 |
| status | string | 状态：unprocessed(未处理)/processed(已处理) |
| createTime | string | 创建时间 |
| processTime | string | 处理时间（可选） |
| processor | string | 处理人（可选） |
| processNote | string | 处理备注（可选） |
| imageUrl | string | 告警截图URL（可选） |

### 3.2 获取告警详情
- **路径**：`/alerts/{id}`
- **方法**：GET
- **响应数据**：单个告警对象

### 3.3 处理告警
- **路径**：`/alerts/{id}/process`
- **方法**：PUT
- **请求体**：
```json
{
  "processNote": "已联系现场人员检查设备"
}
```
- **响应数据**：更新后的告警对象

### 3.4 关闭告警
- **路径**：`/alerts/{id}/close`
- **方法**：PUT
- **响应数据**：更新后的告警对象

---

## 4. 检测相关接口

### 4.1 实时检测
- **路径**：`/detection/realtime`
- **方法**：POST
- **请求体**：
```json
{
  "deviceId": 1,
  "image": "base64编码的图片"
}
```
- **响应数据**：
```json
{
  "detectionCount": 5,
  "types": ["person", "car"],
  "results": [
    {
      "type": "person",
      "confidence": 0.95,
      "bbox": {
        "x": 100,
        "y": 150,
        "width": 50,
        "height": 100
      }
    }
  ]
}
```

### 4.2 获取检测历史
- **路径**：`/detection/history`
- **方法**：GET
- **查询参数**：
    - `page`: 页码
    - `pageSize`: 每页数量
    - `deviceId`: 设备ID
    - `type`: 目标类型
    - `startDate`: 开始日期
    - `endDate`: 结束日期
- **响应数据**：
```json
{
  "list": [
    {
      "id": 1,
      "deviceId": 1,
      "deviceName": "无人机-001",
      "timestamp": "2024-03-15 14:30:25",
      "location": "北京市朝阳区建国路",
      "detectionCount": 5,
      "types": ["person", "car"],
      "results": [
        {
          "type": "person",
          "confidence": 0.95,
          "bbox": {
            "x": 100,
            "y": 150,
            "width": 50,
            "height": 100
          }
        }
      ],
      "imageUrl": "https://example.com/detection/1.jpg"
    }
  ],
  "total": 100
}
```

**字段说明**：
| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | number | 检测记录ID |
| deviceId | number | 设备ID |
| deviceName | string | 设备名称 |
| timestamp | string | 检测时间 |
| location | string | 检测位置 |
| detectionCount | number | 检测数量 |
| types | array | 目标类型数组 |
| results | array | 检测结果详情 |
| imageUrl | string | 检测图像URL |

### 4.3 获取检测详情
- **路径**：`/detection/{id}`
- **方法**：GET
- **响应数据**：单个检测记录对象

### 4.4 分析检测
- **路径**：`/detection/analyze`
- **方法**：POST
- **请求体**：
```json
{
  "image": "base64编码的图片",
  "options": {
    "detectPerson": true,
    "detectCar": true,
    "detectAnimal": false
  }
}
```
- **响应数据**：同实时检测响应

---

## 5. 历史记录相关接口

### 5.1 获取检测历史
- **路径**：`/history/detection`
- **方法**：GET
- **查询参数**：同检测历史接口
- **响应数据**：同检测历史接口

### 5.2 获取检测详情
- **路径**：`/history/detection/{id}`
- **方法**：GET
- **响应数据**：单个检测记录对象

### 5.3 导出检测历史
- **路径**：`/history/detection/export`
- **方法**：GET
- **查询参数**：同检测历史接口
- **响应类型**：blob (Excel文件)

### 5.4 获取告警历史
- **路径**：`/history/alert`
- **方法**：GET
- **查询参数**：同告警列表接口
- **响应数据**：同告警列表接口

### 5.5 获取告警详情
- **路径**：`/history/alert/{id}`
- **方法**：GET
- **响应数据**：单个告警对象

### 5.6 处理告警
- **路径**：`/history/alert/{id}/process`
- **方法**：POST
- **请求体**：
```json
{
  "processNote": "已处理"
}
```
- **响应数据**：更新后的告警对象

### 5.7 导出告警历史
- **路径**：`/history/alert/export`
- **方法**：GET
- **查询参数**：同告警列表接口
- **响应类型**：blob (Excel文件)

### 5.8 获取视频记录
- **路径**：`/history/video`
- **方法**：GET
- **查询参数**：
    - `deviceId`: 设备ID
    - `date`: 日期
- **响应数据**：
```json
{
  "list": [
    {
      "id": 1,
      "deviceId": 1,
      "deviceName": "无人机-001",
      "title": "监控录像-20240315-143000",
      "duration": "05:23",
      "createTime": "2024-03-15 14:30:00",
      "fileSize": 102400000,
      "thumbnailUrl": "https://example.com/video/1.jpg"
    }
  ]
}
```

**字段说明**：
| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | number | 视频ID |
| deviceId | number | 设备ID |
| deviceName | string | 设备名称 |
| title | string | 视频标题 |
| duration | string | 时长 |
| createTime | string | 创建时间 |
| fileSize | number | 文件大小(字节) |
| thumbnailUrl | string | 缩略图URL |

### 5.9 获取视频详情
- **路径**：`/history/video/{id}`
- **方法**：GET
- **响应数据**：单个视频对象

### 5.10 下载视频
- **路径**：`/history/video/{id}/download`
- **方法**：GET
- **响应类型**：blob (视频文件)

### 5.11 删除视频记录
- **路径**：`/history/video/{id}`
- **方法**：DELETE
- **响应数据**：
```json
{
  "success": true
}
```

---

## 6. 监控相关接口

### 6.1 获取视频流
- **路径**：`/monitor/stream/{deviceId}`
- **方法**：GET
- **响应类型**：视频流 (如 MJPEG 或 HLS)

### 6.2 获取检测结果
- **路径**：`/monitor/detection/{deviceId}`
- **方法**：GET
- **响应数据**：
```json
{
  "timestamp": "2024-03-15 14:30:25",
  "detectionCount": 5,
  "types": ["person", "car"],
  "results": [
    {
      "type": "person",
      "confidence": 0.95,
      "bbox": {
        "x": 100,
        "y": 150,
        "width": 50,
        "height": 100
      }
    }
  ]
}
```

### 6.3 开始监控
- **路径**：`/monitor/start/{deviceId}`
- **方法**：POST
- **响应数据**：
```json
{
  "success": true,
  "streamUrl": "https://example.com/stream/1"
}
```

### 6.4 停止监控
- **路径**：`/monitor/stop/{deviceId}`
- **方法**：POST
- **响应数据**：
```json
{
  "success": true
}
```

### 6.5 获取监控状态
- **路径**：`/monitor/status/{deviceId}`
- **方法**：GET
- **响应数据**：
```json
{
  "isMonitoring": true,
  "streamUrl": "https://example.com/stream/1",
  "startTime": "2024-03-15 14:30:00"
}
```

### 6.6 获取快照
- **路径**：`/monitor/snapshot/{deviceId}`
- **方法**：GET
- **响应类型**：blob (图片文件)

---

## 7. 分析相关接口

### 7.1 获取检测统计
- **路径**：`/analysis/detection/stats`
- **方法**：GET
- **响应数据**：
```json
{
  "total": 12580,
  "personCount": 4520,
  "carCount": 3890,
  "avgPerDay": 420
}
```

### 7.2 获取检测趋势
- **路径**：`/analysis/detection/trend`
- **方法**：GET
- **查询参数**：
    - `period`: 周期（day/week/month）
- **响应数据**：
```json
{
  "dates": ["2024-03-10", "2024-03-11", "2024-03-12", "2024-03-13", "2024-03-14", "2024-03-15"],
  "values": [320, 350, 380, 400, 420, 450]
}
```

### 7.3 获取检测类型分布
- **路径**：`/analysis/detection/types`
- **方法**：GET
- **响应数据**：
```json
{
  "person": 4520,
  "car": 3890,
  "animal": 1250,
  "building": 2920
}
```

### 7.4 获取时间分布
- **路径**：`/analysis/detection/time`
- **方法**：GET
- **响应数据**：
```json
{
  "hours": ["00:00", "01:00", "02:00", ...],
  "values": [10, 5, 3, ...]
}
```

### 7.5 获取告警统计
- **路径**：`/analysis/alert/stats`
- **方法**：GET
- **响应数据**：
```json
{
  "high": 45,
  "medium": 128,
  "low": 256,
  "processRate": 92.5,
  "avgResponseTime": 12,
  "minResponseTime": 2,
  "maxResponseTime": 58,
  "response30minRate": 88.5
}
```

### 7.6 获取告警趋势
- **路径**：`/analysis/alert/trend`
- **方法**：GET
- **查询参数**：
    - `period`: 周期（day/week/month）
- **响应数据**：同检测趋势

### 7.7 获取告警类型分布
- **路径**：`/analysis/alert/types`
- **方法**：GET
- **响应数据**：
```json
{
  "intrusion": 45,
  "device_offline": 30,
  "low_battery": 50,
  "signal_loss": 25,
  "abnormal_behavior": 20
}
```

### 7.8 获取告警处理统计
- **路径**：`/analysis/alert/process`
- **方法**：GET
- **响应数据**：
```json
{
  "processed": 380,
  "unprocessed": 49,
  "autoProcessed": 120,
  "manualProcessed": 260
}
```

### 7.9 获取设备统计
- **路径**：`/analysis/device/stats`
- **方法**：GET
- **响应数据**：
```json
{
  "total": 15,
  "online": 12,
  "offline": 3,
  "offlineRate": 20
}
```

### 7.10 获取设备在线趋势
- **路径**：`/analysis/device/online`
- **方法**：GET
- **查询参数**：
    - `period`: 周期（day/week/month）
- **响应数据**：同检测趋势

### 7.11 获取设备排名
- **路径**：`/analysis/device/ranking`
- **方法**：GET
- **查询参数**：
    - `limit`: 返回数量，默认5
- **响应数据**：
```json
{
  "list": [
    {
      "deviceId": 1,
      "name": "无人机-001",
      "count": 3250,
      "percentage": 25.8
    }
  ]
}
```

### 7.12 获取设备运行时间
- **路径**：`/analysis/device/runtime`
- **方法**：GET
- **响应数据**：
```json
{
  "dates": ["2024-03-10", "2024-03-11", ...],
  "values": [8.5, 9.2, 7.8, ...]
}
```

### 7.13 获取仪表板统计
- **路径**：`/analysis/dashboard`
- **方法**：GET
- **响应数据**：
```json
{
  "deviceTotal": 15,
  "deviceOnline": 12,
  "deviceOffline": 3,
  "detectionTotal": 12580,
  "detectionToday": 420,
  "alertTotal": 429,
  "alertUnprocessed": 49,
  "onlineRate": 80
}
```

---

## 8. 配置相关接口

### 8.1 获取AI配置
- **路径**：`/config/ai`
- **方法**：GET
- **响应数据**：
```json
{
  "modelType": "yolov8",
  "confidenceThreshold": 0.5,
  "nmsThreshold": 0.4,
  "inputSize": 640,
  "enableTracking": true,
  "enableClassification": true
}
```

### 8.2 更新AI配置
- **路径**：`/config/ai`
- **方法**：PUT
- **请求体**：同获取AI配置响应
- **响应数据**：更新后的配置

### 8.3 测试AI模型
- **路径**：`/config/ai/test`
- **方法**：POST
- **请求体**：
```json
{
  "image": "base64编码的图片"
}
```
- **响应数据**：检测结果

### 8.4 获取告警配置
- **路径**：`/config/alert`
- **方法**：GET
- **响应数据**：
```json
{
  "enableEmail": true,
  "enableSms": false,
  "enablePush": true,
  "emailAddresses": ["admin@example.com"],
  "phoneNumbers": [],
  "alertLevels": ["high", "medium"]
}
```

### 8.5 更新告警配置
- **路径**：`/config/alert`
- **方法**：PUT
- **请求体**：同获取告警配置响应
- **响应数据**：更新后的配置

### 8.6 获取系统参数
- **路径**：`/config/system`
- **方法**：GET
- **响应数据**：
```json
{
  "videoRetentionDays": 30,
  "detectionRetentionDays": 90,
  "maxConcurrentStreams": 10,
  "enableAutoCleanup": true
}
```

### 8.7 更新系统参数
- **路径**：`/config/system`
- **方法**：PUT
- **请求体**：同获取系统参数响应
- **响应数据**：更新后的参数

### 8.8 获取系统信息
- **路径**：`/config/info`
- **方法**：GET
- **响应数据**：
```json
{
  "version": "1.0.0",
  "buildTime": "2024-03-01 10:00:00",
  "serverTime": "2024-03-15 14:30:00",
  "uptime": "15天3小时",
  "os": "Linux",
  "cpuUsage": 35.5,
  "memoryUsage": 62.3,
  "diskUsage": 45.8
}
```

### 8.9 重置配置
- **路径**：`/config/reset/{type}`
- **方法**：POST
- **路径参数**：
    - `type`: 配置类型（ai/alert/system）
- **响应数据**：
```json
{
  "success": true
}
```

---

## 9. 系统相关接口

### 9.1 获取系统状态
- **路径**：`/system/status`
- **方法**：GET
- **响应数据**：
```json
{
  "status": "running",
  "timestamp": "2024-03-15 14:30:00",
  "services": {
    "database": "connected",
    "redis": "connected",
    "aiEngine": "running"
  }
}
```

### 9.2 获取系统配置
- **路径**：`/system/config`
- **方法**：GET
- **响应数据**：系统配置对象

### 9.3 更新系统配置
- **路径**：`/system/config`
- **方法**：PUT
- **请求体**：系统配置对象
- **响应数据**：更新后的配置

### 9.4 获取系统日志
- **路径**：`/system/logs`
- **方法**：GET
- **查询参数**：
    - `level`: 日志级别（debug/info/warn/error）
    - `startDate`: 开始日期
    - `endDate`: 结束日期
    - `page`: 页码
    - `pageSize`: 每页数量
- **响应数据**：
```json
{
  "list": [
    {
      "id": 1,
      "timestamp": "2024-03-15 14:30:00",
      "level": "info",
      "module": "device",
      "message": "设备上线",
      "details": "设备ID: 1"
    }
  ],
  "total": 1000
}
```

---

## 10. 用户相关接口

### 10.1 获取用户资料
- **路径**：`/user/profile`
- **方法**：GET
- **响应数据**：用户信息对象

### 10.2 更新用户资料
- **路径**：`/user/profile`
- **方法**：PUT
- **请求体**：用户信息对象
- **响应数据**：更新后的用户信息

### 10.3 更新头像
- **路径**：`/user/avatar`
- **方法**：POST
- **请求类型**：multipart/form-data
- **请求参数**：
    - `file`: 头像图片文件
- **响应数据**：
```json
{
  "avatarUrl": "https://example.com/avatar/1.jpg"
}
```

### 10.4 修改密码
- **路径**：`/user/password`
- **方法**：PUT
- **请求体**：
```json
{
  "oldPassword": "旧密码",
  "newPassword": "新密码"
}
```
- **响应数据**：
```json
{
  "success": true
}
```

### 10.5 绑定邮箱
- **路径**：`/user/email`
- **方法**：POST
- **请求体**：
```json
{
  "email": "user@example.com",
  "code": "123456"
}
```
- **响应数据**：更新后的用户信息

### 10.6 绑定手机
- **路径**：`/user/phone`
- **方法**：POST
- **请求体**：
```json
{
  "phone": "13800138000",
  "code": "123456"
}
```
- **响应数据**：更新后的用户信息

### 10.7 切换双因素认证
- **路径**：`/user/two-factor`
- **方法**：PUT
- **请求体**：
```json
{
  "enabled": true,
  "type": "email"
}
```
- **响应数据**：更新后的用户信息

### 10.8 获取登录历史
- **路径**：`/user/login-history`
- **方法**：GET
- **查询参数**：
    - `page`: 页码
    - `pageSize`: 每页数量
- **响应数据**：
```json
{
  "list": [
    {
      "id": 1,
      "loginTime": "2024-03-15 14:30:00",
      "ip": "192.168.1.1",
      "device": "Chrome/Windows",
      "location": "北京市"
    }
  ],
  "total": 50
}
```

### 10.9 获取操作日志
- **路径**：`/user/operation-logs`
- **方法**：GET
- **查询参数**：
    - `page`: 页码
    - `pageSize`: 每页数量
- **响应数据**：
```json
{
  "list": [
    {
      "id": 1,
      "operationTime": "2024-03-15 14:30:00",
      "operation": "添加设备",
      "target": "设备ID: 1",
      "result": "成功"
    }
  ],
  "total": 100
}
```

### 10.10 获取用户统计
- **路径**：`/user/stats`
- **方法**：GET
- **响应数据**：
```json
{
  "totalOperations": 150,
  "totalLogins": 30,
  "lastLoginTime": "2024-03-15 14:30:00",
  "accountAge": 60
}
```

---

## 注意事项

### 1. 数据格式
- 所有时间字段格式为：`yyyy-MM-dd HH:mm:ss`
- 所有日期字段格式为：`yyyy-MM-dd`
- 状态字段使用小写字符串

### 2. 分页参数
- 默认页码：`page=1`
- 默认每页数量：`pageSize=10`
- 最大每页数量：`pageSize=100`

### 3. 错误处理
- 所有错误响应应包含 `code` 和 `message` 字段
- 常见错误码：
    - 400: 请求参数错误
    - 401: 未授权
    - 403: 权限不足
    - 404: 资源不存在
    - 500: 服务器内部错误

### 4. 文件上传
- 头像上传使用 `multipart/form-data` 格式
- 图片大小限制：最大 5MB
- 支持的图片格式：jpg, jpeg, png

### 5. 文件下载
- 导出和下载接口需要设置 `responseType: 'blob'`
- 视频下载支持断点续传

### 6. WebSocket
- 实时监控使用 WebSocket 连接
- 连接地址：`ws://localhost:8080/ws/monitor`
- 需要在连接时传递 token
