package com.expressway.entity;

import lombok.Data;
import java.util.Date;

/**
 * AI模型实体（对应ai_model表）
 */
@Data
public class AiModel {
    private Long id; // 模型ID
    private String name; // 模型名称
    private String version; // 模型版本
    private String type; // 模型类型
    private String description; // 模型描述
    private String modelPath; // 模型路径
    private String configPath; // 配置文件路径
    private Integer inputSize; // 输入尺寸
    private Integer batchSize; // 批处理大小
    private Double confidenceThreshold; // 置信度阈值
    private Integer enabled; // 是否启用：1-启用，0-禁用
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
}
