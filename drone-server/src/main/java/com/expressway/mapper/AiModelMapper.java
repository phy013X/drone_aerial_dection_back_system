package com.expressway.mapper;

import com.expressway.entity.AiModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AI模型Mapper
 */
@Mapper
public interface AiModelMapper {
    /**
     * 获取AI模型列表
     * @param type 模型类型
     * @param enabled 是否启用
     * @return 模型列表
     */
    List<AiModel> getAiModelList(@Param("type") String type, @Param("enabled") Integer enabled);

    /**
     * 获取AI模型详情
     * @param id 模型ID
     * @return 模型详情
     */
    AiModel getAiModelById(Long id);

    /**
     * 创建AI模型
     * @param aiModel 模型信息
     */
    void createAiModel(AiModel aiModel);

    /**
     * 更新AI模型
     * @param aiModel 模型信息
     */
    void updateAiModel(AiModel aiModel);

    /**
     * 删除AI模型
     * @param id 模型ID
     */
    void deleteAiModel(Long id);

    /**
     * 启用/禁用AI模型
     * @param id 模型ID
     * @param enabled 是否启用
     */
    void enableAiModel(@Param("id") Long id, @Param("enabled") Integer enabled);
}
