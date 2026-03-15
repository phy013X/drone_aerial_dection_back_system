package com.expressway.service.impl;

import com.expressway.dto.DeviceRegisterDTO;
import com.expressway.dto.DeviceStatusUpdateDTO;
import com.expressway.entity.Device;
import com.expressway.mapper.DeviceMapper;
import com.expressway.result.PageResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceImplTest {

    @Mock
    private DeviceMapper deviceMapper;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    private Device device;
    private DeviceRegisterDTO deviceRegisterDTO;
    private DeviceStatusUpdateDTO deviceStatusUpdateDTO;

    @BeforeEach
    public void setUp() {
        device = new Device();
        device.setId(1L);
        device.setName("Test Device");
        device.setSerialNumber("device-001");
        device.setStatus(1);

        deviceRegisterDTO = new DeviceRegisterDTO();
        deviceRegisterDTO.setName("Test Device");
        deviceRegisterDTO.setSerialNumber("device-001");

        deviceStatusUpdateDTO = new DeviceStatusUpdateDTO();
        deviceStatusUpdateDTO.setStatus(1);
    }

    @Test
    public void testGetDeviceList() {
        // 模拟deviceMapper.getDeviceCount返回总记录数
        when(deviceMapper.getDeviceCount(anyInt())).thenReturn(1);
        // 模拟deviceMapper.getDeviceList返回设备列表
        List<Device> devices = new ArrayList<>();
        devices.add(device);
        when(deviceMapper.getDeviceList(anyInt(), anyInt(), anyInt())).thenReturn(devices);

        // 调用getDeviceList方法
        PageResult result = deviceService.getDeviceList(1, 1, 10);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertNotNull(result.getRecords());
        verify(deviceMapper, times(1)).getDeviceCount(1);
        verify(deviceMapper, times(1)).getDeviceList(1, 0, 10);
    }

    @Test
    public void testGetDeviceById() {
        // 模拟deviceMapper.getDeviceById返回设备
        when(deviceMapper.getDeviceById(1L)).thenReturn(device);

        // 调用getDeviceById方法
        var result = deviceService.getDeviceById(1L);

        // 验证结果
        assertNotNull(result);
        verify(deviceMapper, times(1)).getDeviceById(1L);
    }

    @Test
    public void testRegisterDevice() {
        // 模拟deviceMapper.getDeviceBySerialNumber返回null
        when(deviceMapper.getDeviceBySerialNumber("device-001")).thenReturn(null);
        // 模拟deviceMapper.createDevice
        doNothing().when(deviceMapper).createDevice(any(Device.class));

        // 调用registerDevice方法
        deviceService.registerDevice(deviceRegisterDTO);

        // 验证结果
        verify(deviceMapper, times(1)).getDeviceBySerialNumber("device-001");
        verify(deviceMapper, times(1)).createDevice(any(Device.class));
    }

    @Test
    public void testUpdateDevice() {
        // 模拟deviceMapper.getDeviceById返回设备
        when(deviceMapper.getDeviceById(1L)).thenReturn(device);
        // 模拟deviceMapper.getDeviceBySerialNumber返回null
        when(deviceMapper.getDeviceBySerialNumber("device-001")).thenReturn(null);
        // 模拟deviceMapper.updateDevice
        doNothing().when(deviceMapper).updateDevice(any(Device.class));

        // 调用updateDevice方法
        deviceService.updateDevice(1L, deviceRegisterDTO);

        // 验证结果
        verify(deviceMapper, times(1)).getDeviceById(1L);
        verify(deviceMapper, times(1)).getDeviceBySerialNumber("device-001");
        verify(deviceMapper, times(1)).updateDevice(any(Device.class));
    }

    @Test
    public void testDeleteDevice() {
        // 模拟deviceMapper.getDeviceById返回设备
        when(deviceMapper.getDeviceById(1L)).thenReturn(device);
        // 模拟deviceMapper.deleteDevice
        doNothing().when(deviceMapper).deleteDevice(1L);

        // 调用deleteDevice方法
        deviceService.deleteDevice(1L);

        // 验证结果
        verify(deviceMapper, times(1)).getDeviceById(1L);
        verify(deviceMapper, times(1)).deleteDevice(1L);
    }

    @Test
    public void testUpdateDeviceStatus() {
        // 模拟deviceMapper.getDeviceById返回设备
        when(deviceMapper.getDeviceById(1L)).thenReturn(device);
        // 模拟deviceMapper.updateDeviceStatus
        doNothing().when(deviceMapper).updateDeviceStatus(anyLong(), anyInt(), any(Date.class));

        // 调用updateDeviceStatus方法
        deviceService.updateDeviceStatus(1L, deviceStatusUpdateDTO);

        // 验证结果
        verify(deviceMapper, times(2)).getDeviceById(1L);
        verify(deviceMapper, times(1)).updateDeviceStatus(anyLong(), anyInt(), any(Date.class));
    }

    @Test
    public void testConnectDevice() {
        // 模拟deviceMapper.getDeviceById返回设备
        when(deviceMapper.getDeviceById(1L)).thenReturn(device);
        // 模拟deviceMapper.updateDeviceStatus
        doNothing().when(deviceMapper).updateDeviceStatus(anyLong(), anyInt(), any(Date.class));

        // 调用connectDevice方法
        deviceService.connectDevice(1L);

        // 验证结果
        verify(deviceMapper, times(1)).getDeviceById(1L);
        verify(deviceMapper, times(1)).updateDeviceStatus(anyLong(), anyInt(), any(Date.class));
    }

    @Test
    public void testDisconnectDevice() {
        // 模拟deviceMapper.getDeviceById返回设备
        when(deviceMapper.getDeviceById(1L)).thenReturn(device);
        // 模拟deviceMapper.updateDeviceStatus
        doNothing().when(deviceMapper).updateDeviceStatus(anyLong(), anyInt(), any(Date.class));

        // 调用disconnectDevice方法
        deviceService.disconnectDevice(1L);

        // 验证结果
        verify(deviceMapper, times(1)).getDeviceById(1L);
        verify(deviceMapper, times(1)).updateDeviceStatus(anyLong(), anyInt(), any(Date.class));
    }
}
