package com.expressway.service;

import com.expressway.entity.AiModel;

import java.util.List;

/**
 * AI模型服务
 */
public interface AiModelService {
    /**
     * 获取AI模型列表
     * @param type 模型类型
     * @param enabled 是否启用
     * @return 模型列表
     */
    List<AiModel> getAiModelList(String type, Integer enabled);

    /**
     * 获取AI模型详情
     * @param id 模型ID
     * @return 模型详情
     */
    AiModel getAiModelById(Long id);

    /**
     * 创建AI模型
     * @param aiModel 模型信息
     * @return 模型信息
     */
    AiModel createAiModel(AiModel aiModel);

    /**
     * 更新AI模型
     * @param id 模型ID
     * @param aiModel 模型信息
     * @return 模型信息
     */
    AiModel updateAiModel(Long id, AiModel aiModel);

    /**
     * 删除AI模型
     * @param id 模型ID
     */
    void deleteAiModel(Long id);

    /**
     * 启用/禁用AI模型
     * @param id 模型ID
     * @param enabled 是否启用
     * @return 模型信息
     */
    AiModel enableAiModel(Long id, Integer enabled);
}
