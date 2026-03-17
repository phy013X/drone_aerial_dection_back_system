package com.expressway.mapper;

import com.expressway.entity.SystemConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统配置Mapper
 */
@Mapper
public interface SystemConfigMapper {

    /**
     * 根据键获取配置
     * @param key 配置键
     * @return 系统配置
     */
    SystemConfig getConfigByKey(String key);

    /**
     * 获取所有配置
     * @return 配置列表
     */
    List<SystemConfig> getAllConfigs();

    /**
     * 创建配置
     * @param config 系统配置
     */
    void createConfig(SystemConfig config);

    /**
     * 更新配置
     * @param config 系统配置
     */
    void updateConfig(SystemConfig config);
}
