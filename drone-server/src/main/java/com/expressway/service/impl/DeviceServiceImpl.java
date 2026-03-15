package com.expressway.service.impl;

import com.expressway.dto.DeviceRegisterDTO;
import com.expressway.dto.DeviceStatusUpdateDTO;
import com.expressway.entity.Device;
import com.expressway.exception.BusinessException;
import com.expressway.mapper.DeviceMapper;
import com.expressway.result.PageResult;
import com.expressway.service.DeviceService;
import com.expressway.vo.DeviceVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 设备服务实现
 */
@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    @Resource
    private DeviceMapper deviceMapper;

    @Override
    public PageResult<DeviceVO> getDeviceList(String keyword, String status, Integer page, Integer pageSize) {
        // 计算分页偏移量
        int offset = (page - 1) * pageSize;
        // 转换状态字符串为整数（如果前端传递的是字符串）
        Integer statusInt = null;
        if (status != null) {
            switch (status) {
                case "online":
                    statusInt = 1;
                    break;
                case "offline":
                    statusInt = 0;
                    break;
                case "fault":
                    statusInt = 2;
                    break;
                case "maintenance":
                    statusInt = 3;
                    break;
            }
        }
        // 获取设备列表
        List<Device> deviceList = deviceMapper.getDeviceList(statusInt, offset, pageSize);
        // 获取设备总数
        int total = deviceMapper.getDeviceCount(statusInt);
        // 转换为VO
        List<DeviceVO> deviceVOList = new ArrayList<>();
        for (Device device : deviceList) {
            DeviceVO deviceVO = new DeviceVO();
            deviceVO.setId(device.getId());
            deviceVO.setName(device.getName());
            deviceVO.setType(device.getType());
            deviceVO.setModel(device.getModel());
            deviceVO.setSerialNumber(device.getSerialNumber());
            deviceVO.setGroupName(device.getGroupName());
            deviceVO.setUnit(device.getUnit());
            deviceVO.setLocation(device.getLocation());
            deviceVO.setStatus(device.getStatus());
            deviceVO.setStatusText(getStatusText(device.getStatus()));
            deviceVO.setIp(device.getIp());
            deviceVO.setPort(device.getPort());
            deviceVO.setConnectionType(device.getConnectionType());
            deviceVO.setLastOnlineTime(device.getLastOnlineTime());
            deviceVO.setCreateTime(device.getCreateTime());
            deviceVOList.add(deviceVO);
        }
        return new PageResult<>(total, deviceVOList);
    }

    @Override
    public DeviceVO getDeviceById(Long id) {
        Device device = deviceMapper.getDeviceById(id);
        if (device == null) {
            throw new BusinessException("设备不存在");
        }
        DeviceVO deviceVO = new DeviceVO();
        deviceVO.setId(device.getId());
        deviceVO.setName(device.getName());
        deviceVO.setType(device.getType());
        deviceVO.setModel(device.getModel());
        deviceVO.setSerialNumber(device.getSerialNumber());
        deviceVO.setGroupName(device.getGroupName());
        deviceVO.setUnit(device.getUnit());
        deviceVO.setLocation(device.getLocation());
        deviceVO.setStatus(device.getStatus());
        deviceVO.setStatusText(getStatusText(device.getStatus()));
        deviceVO.setIp(device.getIp());
        deviceVO.setPort(device.getPort());
        deviceVO.setConnectionType(device.getConnectionType());
        deviceVO.setLastOnlineTime(device.getLastOnlineTime());
        deviceVO.setCreateTime(device.getCreateTime());
        return deviceVO;
    }

    @Override
    public Device registerDevice(DeviceRegisterDTO deviceRegisterDTO) {
        // 检查序列号是否已存在
        if (deviceMapper.getDeviceBySerialNumber(deviceRegisterDTO.getSerialNumber()) != null) {
            throw new BusinessException("设备序列号已存在");
        }
        // 创建设备
        Device device = new Device();
        device.setName(deviceRegisterDTO.getName());
        device.setType(deviceRegisterDTO.getType());
        device.setModel(deviceRegisterDTO.getModel());
        device.setSerialNumber(deviceRegisterDTO.getSerialNumber());
        device.setGroupId(deviceRegisterDTO.getGroupId());
        device.setUnit(deviceRegisterDTO.getUnit());
        device.setContact(deviceRegisterDTO.getContact());
        device.setContactPhone(deviceRegisterDTO.getContactPhone());
        device.setLocation(deviceRegisterDTO.getLocation());
        device.setLatitude(deviceRegisterDTO.getLatitude());
        device.setLongitude(deviceRegisterDTO.getLongitude());
        device.setIp(deviceRegisterDTO.getIp());
        device.setPort(deviceRegisterDTO.getPort());
        device.setConnectionType(deviceRegisterDTO.getConnectionType());
        device.setFirmwareVersion(deviceRegisterDTO.getFirmwareVersion());
        device.setStatus(0); // 初始状态为离线
        deviceMapper.createDevice(device);
        return device;
    }

    @Override
    public Device updateDevice(Long id, DeviceRegisterDTO deviceRegisterDTO) {
        // 检查设备是否存在
        Device existingDevice = deviceMapper.getDeviceById(id);
        if (existingDevice == null) {
            throw new BusinessException("设备不存在");
        }
        // 检查序列号是否已被其他设备使用
        Device deviceBySerialNumber = deviceMapper.getDeviceBySerialNumber(deviceRegisterDTO.getSerialNumber());
        if (deviceBySerialNumber != null && !deviceBySerialNumber.getId().equals(id)) {
            throw new BusinessException("设备序列号已被其他设备使用");
        }
        // 更新设备信息
        Device device = new Device();
        device.setId(id);
        device.setName(deviceRegisterDTO.getName());
        device.setType(deviceRegisterDTO.getType());
        device.setModel(deviceRegisterDTO.getModel());
        device.setSerialNumber(deviceRegisterDTO.getSerialNumber());
        device.setGroupId(deviceRegisterDTO.getGroupId());
        device.setUnit(deviceRegisterDTO.getUnit());
        device.setContact(deviceRegisterDTO.getContact());
        device.setContactPhone(deviceRegisterDTO.getContactPhone());
        device.setLocation(deviceRegisterDTO.getLocation());
        device.setLatitude(deviceRegisterDTO.getLatitude());
        device.setLongitude(deviceRegisterDTO.getLongitude());
        device.setIp(deviceRegisterDTO.getIp());
        device.setPort(deviceRegisterDTO.getPort());
        device.setConnectionType(deviceRegisterDTO.getConnectionType());
        device.setFirmwareVersion(deviceRegisterDTO.getFirmwareVersion());
        deviceMapper.updateDevice(device);
        return device;
    }

    @Override
    public Device updateDeviceStatus(Long id, DeviceStatusUpdateDTO deviceStatusUpdateDTO) {
        // 检查设备是否存在
        Device existingDevice = deviceMapper.getDeviceById(id);
        if (existingDevice == null) {
            throw new BusinessException("设备不存在");
        }
        // 更新设备状态
        deviceMapper.updateDeviceStatus(id, deviceStatusUpdateDTO.getStatus(), new Date());
        // 更新其他信息
        if (deviceStatusUpdateDTO.getIp() != null || deviceStatusUpdateDTO.getPort() != null) {
            Device device = new Device();
            device.setId(id);
            device.setIp(deviceStatusUpdateDTO.getIp());
            device.setPort(deviceStatusUpdateDTO.getPort());
            device.setLatitude(deviceStatusUpdateDTO.getLatitude());
            device.setLongitude(deviceStatusUpdateDTO.getLongitude());
            deviceMapper.updateDevice(device);
        }
        return deviceMapper.getDeviceById(id);
    }

    @Override
    public void deleteDevice(Long id) {
        // 检查设备是否存在
        if (deviceMapper.getDeviceById(id) == null) {
            throw new BusinessException("设备不存在");
        }
        // 删除设备
        deviceMapper.deleteDevice(id);
    }

    @Override
    public boolean connectDevice(Long id) {
        // 检查设备是否存在
        Device device = deviceMapper.getDeviceById(id);
        if (device == null) {
            throw new BusinessException("设备不存在");
        }
        // 模拟设备连接
        log.info("连接设备：{}", device.getName());
        // 更新设备状态为在线
        deviceMapper.updateDeviceStatus(id, 1, new Date());
        return true;
    }

    @Override
    public boolean disconnectDevice(Long id) {
        // 检查设备是否存在
        Device device = deviceMapper.getDeviceById(id);
        if (device == null) {
            throw new BusinessException("设备不存在");
        }
        // 模拟设备断开连接
        log.info("断开设备连接：{}", device.getName());
        // 更新设备状态为离线
        deviceMapper.updateDeviceStatus(id, 0, new Date());
        return true;
    }

    /**
     * 获取设备状态文本
     * @param status 设备状态
     * @return 状态文本
     */
    private String getStatusText(Integer status) {
        switch (status) {
            case 1:
                return "在线";
            case 0:
                return "离线";
            case 2:
                return "故障";
            case 3:
                return "维护中";
            default:
                return "未知";
        }
    }
}
