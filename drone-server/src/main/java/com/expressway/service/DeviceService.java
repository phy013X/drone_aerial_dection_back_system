package com.expressway.service;

import com.expressway.dto.DeviceRegisterDTO;
import com.expressway.dto.DeviceStatusUpdateDTO;
import com.expressway.entity.Device;
import com.expressway.vo.DeviceVO;

import java.util.List;

/**
 * 设备服务
 */
public interface DeviceService {
    /**
     * 获取设备列表
     * @param keyword 搜索关键词
     * @param status 设备状态
     * @param type 设备类型
     * @param page 页码
     * @param pageSize 每页数量
     * @return 设备列表
     */
    com.expressway.result.PageResult<DeviceVO> getDeviceList(String keyword, String status, String type, Integer page, Integer pageSize);

    /**
     * 获取设备详情
     * @param id 设备ID
     * @return 设备详情
     */
    Device getDeviceById(Long id);

    /**
     * 注册设备
     * @param deviceRegisterDTO 设备注册信息
     * @return 设备信息
     */
    Device registerDevice(DeviceRegisterDTO deviceRegisterDTO);

    /**
     * 更新设备信息
     * @param id 设备ID
     * @param deviceRegisterDTO 设备信息
     * @return 设备信息
     */
    Device updateDevice(Long id, DeviceRegisterDTO deviceRegisterDTO);

    /**
     * 更新设备状态
     * @param id 设备ID
     * @param deviceStatusUpdateDTO 设备状态信息
     * @return 设备信息
     */
    Device updateDeviceStatus(Long id, DeviceStatusUpdateDTO deviceStatusUpdateDTO);

    /**
     * 删除设备
     * @param id 设备ID
     */
    void deleteDevice(Long id);

    /**
     * 连接设备
     * @param id 设备ID
     * @return 连接结果
     */
    boolean connectDevice(Long id);

    /**
     * 断开设备连接
     * @param id 设备ID
     * @return 断开结果
     */
    boolean disconnectDevice(Long id);
}
