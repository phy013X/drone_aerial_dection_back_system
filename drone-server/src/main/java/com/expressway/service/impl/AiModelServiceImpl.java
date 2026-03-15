package com.expressway.service.impl;

import com.expressway.entity.AiModel;
import com.expressway.exception.BusinessException;
import com.expressway.mapper.AiModelMapper;
import com.expressway.service.AiModelService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AI模型服务实现
 */
@Service
@Slf4j
public class AiModelServiceImpl implements AiModelService {

    @Resource
    private AiModelMapper aiModelMapper;

    @Override
    public List<AiModel> getAiModelList(String type, Integer enabled) {
        return aiModelMapper.getAiModelList(type, enabled);
    }

    @Override
    public AiModel getAiModelById(Long id) {
        AiModel aiModel = aiModelMapper.getAiModelById(id);
        if (aiModel == null) {
            throw new BusinessException("AI模型不存在");
        }
        return aiModel;
    }

    @Override
    public AiModel createAiModel(AiModel aiModel) {
        // 检查模型路径是否存在
        java.io.File modelFile = new java.io.File(aiModel.getModelPath());
        if (!modelFile.exists()) {
            throw new BusinessException("模型文件不存在");
        }
        // 创建设置默认值
        if (aiModel.getEnabled() == null) {
            aiModel.setEnabled(0);
        }
        if (aiModel.getInputSize() == null) {
            aiModel.setInputSize(640);
        }
        if (aiModel.getBatchSize() == null) {
            aiModel.setBatchSize(1);
        }
        if (aiModel.getConfidenceThreshold() == null) {
            aiModel.setConfidenceThreshold(0.5);
        }
        // 创建模型
        aiModelMapper.createAiModel(aiModel);
        return aiModel;
    }

    @Override
    public AiModel updateAiModel(Long id, AiModel aiModel) {
        // 检查模型是否存在
        if (aiModelMapper.getAiModelById(id) == null) {
            throw new BusinessException("AI模型不存在");
        }
        // 检查模型路径是否存在
        if (aiModel.getModelPath() != null) {
            java.io.File modelFile = new java.io.File(aiModel.getModelPath());
            if (!modelFile.exists()) {
                throw new BusinessException("模型文件不存在");
            }
        }
        // 更新模型
        aiModel.setId(id);
        aiModelMapper.updateAiModel(aiModel);
        return aiModelMapper.getAiModelById(id);
    }

    @Override
    public void deleteAiModel(Long id) {
        // 检查模型是否存在
        if (aiModelMapper.getAiModelById(id) == null) {
            throw new BusinessException("AI模型不存在");
        }
        // 删除模型
        aiModelMapper.deleteAiModel(id);
    }

    @Override
    public AiModel enableAiModel(Long id, Integer enabled) {
        // 检查模型是否存在
        if (aiModelMapper.getAiModelById(id) == null) {
            throw new BusinessException("AI模型不存在");
        }
        // 启用/禁用模型
        aiModelMapper.enableAiModel(id, enabled);
        return aiModelMapper.getAiModelById(id);
    }
}
