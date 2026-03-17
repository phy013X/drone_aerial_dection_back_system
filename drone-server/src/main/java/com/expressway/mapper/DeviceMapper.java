package com.expressway.mapper;

import com.expressway.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备Mapper
 */
@Mapper
public interface DeviceMapper {
    /**
     * 获取设备列表
     * @param status 设备状态
     * @param keyword 搜索关键词
     * @param type 设备类型
     * @param offset 偏移量
     * @param limit 每页数量
     * @return 设备列表
     */
    List<Device> getDeviceList(@Param("status") Integer status, @Param("keyword") String keyword, @Param("type") String type, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 获取设备总数
     * @param status 设备状态
     * @param keyword 搜索关键词
     * @param type 设备类型
     * @return 设备总数
     */
    Integer getDeviceCount(@Param("status") Integer status, @Param("keyword") String keyword, @Param("type") String type);

    /**
     * 根据ID获取设备详情
     * @param id 设备ID
     * @return 设备详情
     */
    Device getDeviceById(Long id);

    /**
     * 根据序列号获取设备
     * @param serialNumber 序列号
     * @return 设备信息
     */
    Device getDeviceBySerialNumber(String serialNumber);

    /**
     * 创建设备
     * @param device 设备信息
     */
    void createDevice(Device device);

    /**
     * 更新设备信息
     * @param device 设备信息
     */
    void updateDevice(Device device);

    /**
     * 更新设备状态
     * @param id 设备ID
     * @param status 设备状态
     * @param lastOnlineTime 最后在线时间
     */
    void updateDeviceStatus(@Param("id") Long id, @Param("status") Integer status, @Param("lastOnlineTime") java.util.Date lastOnlineTime);

    /**
     * 删除设备
     * @param id 设备ID
     */
    void deleteDevice(Long id);
}
