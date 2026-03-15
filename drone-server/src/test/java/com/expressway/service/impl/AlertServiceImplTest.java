package com.expressway.service.impl;

import com.expressway.context.BaseContext;
import com.expressway.entity.Alert;
import com.expressway.mapper.AlertMapper;
import com.expressway.result.PageResult;
import org.junit.jupiter.api.AfterEach;
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
public class AlertServiceImplTest {

    @Mock
    private AlertMapper alertMapper;

    @InjectMocks
    private AlertServiceImpl alertService;

    private Alert alert;

    @BeforeEach
    public void setUp() {
        alert = new Alert();
        alert.setId(1L);
        alert.setDeviceId(1L);
        alert.setLevel(1);
        alert.setStatus(0);
        alert.setMessage("Test alert");
        
        // 设置当前用户ID
        BaseContext.setCurrentId(1L);
    }

    @AfterEach
    public void tearDown() {
        // 清除当前用户ID
        BaseContext.setCurrentId(null);
    }

    @Test
    public void testGetAlertList() {
        // 模拟alertMapper.getAlertCount返回总记录数
        when(alertMapper.getAlertCount(anyInt(), anyInt())).thenReturn(1);
        // 模拟alertMapper.getAlertList返回告警列表
        List<Alert> alerts = new ArrayList<>();
        alerts.add(alert);
        when(alertMapper.getAlertList(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(alerts);

        // 调用getAlertList方法
        PageResult result = alertService.getAlertList(1, 0, 1, 10);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertNotNull(result.getRecords());
        verify(alertMapper, times(1)).getAlertCount(1, 0);
        verify(alertMapper, times(1)).getAlertList(1, 0, 0, 10);
    }

    @Test
    public void testGetAlertById() {
        // 模拟alertMapper.getAlertById返回告警
        when(alertMapper.getAlertById(1L)).thenReturn(alert);

        // 调用getAlertById方法
        var result = alertService.getAlertById(1L);

        // 验证结果
        assertNotNull(result);
        verify(alertMapper, times(1)).getAlertById(1L);
    }

    @Test
    public void testProcessAlert() {
        // 模拟alertMapper.getAlertById返回告警
        when(alertMapper.getAlertById(1L)).thenReturn(alert);
        // 模拟alertMapper.processAlert
        doNothing().when(alertMapper).processAlert(anyLong(), anyInt(), any(Date.class), anyLong(), anyString());

        // 调用processAlert方法
        alertService.processAlert(1L, "Test process note");

        // 验证结果
        verify(alertMapper, times(2)).getAlertById(1L);
        verify(alertMapper, times(1)).processAlert(anyLong(), anyInt(), any(Date.class), anyLong(), anyString());
    }

    @Test
    public void testCloseAlert() {
        // 模拟alertMapper.getAlertById返回告警
        when(alertMapper.getAlertById(1L)).thenReturn(alert);
        // 模拟alertMapper.closeAlert
        doNothing().when(alertMapper).closeAlert(anyLong(), anyInt(), any(Date.class), anyLong());

        // 调用closeAlert方法
        alertService.closeAlert(1L);

        // 验证结果
        verify(alertMapper, times(2)).getAlertById(1L);
        verify(alertMapper, times(1)).closeAlert(anyLong(), anyInt(), any(Date.class), anyLong());
    }

    @Test
    public void testCreateAlert() {
        // 模拟alertMapper.createAlert
        doNothing().when(alertMapper).createAlert(any(Alert.class));

        // 调用createAlert方法
        Alert alert = new Alert();
        alert.setDeviceId(1L);
        alert.setLevel(1);
        alert.setMessage("Test alert");
        alertService.createAlert(alert);

        // 验证结果
        verify(alertMapper, times(1)).createAlert(any(Alert.class));
    }
}
