package com.expressway.service.impl;

import com.expressway.dto.RealTimeDetectionDTO;
import com.expressway.entity.Detection;
import com.expressway.mapper.DetectionMapper;
import com.expressway.result.PageResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DetectionServiceImplTest {

    @Mock
    private DetectionMapper detectionMapper;

    @InjectMocks
    private DetectionServiceImpl detectionService;

    private Detection detection;
    private RealTimeDetectionDTO realTimeDetectionDTO;

    @BeforeEach
    public void setUp() {
        detection = new Detection();
        detection.setId(1L);
        detection.setDeviceId(1L);
        detection.setDetectionCount(2);
        detection.setModelVersion("yolov8n");
        detection.setInferenceTime(100);

        realTimeDetectionDTO = new RealTimeDetectionDTO();
        realTimeDetectionDTO.setDeviceId(1L);
        realTimeDetectionDTO.setImage("base64encodedimage");
    }

    @Test
    public void testRealTimeDetection() {
        // 模拟detectionMapper.createDetection
        doNothing().when(detectionMapper).createDetection(any(Detection.class));
        // 模拟detectionMapper.createDetectionResult
        doNothing().when(detectionMapper).createDetectionResult(any(com.expressway.entity.DetectionResult.class));

        // 调用realTimeDetection方法
        var result = detectionService.realTimeDetection(realTimeDetectionDTO);

        // 验证结果
        assertNotNull(result);
        verify(detectionMapper, times(1)).createDetection(any(Detection.class));
    }

    @Test
    public void testGetDetectionHistory() {
        // 模拟detectionMapper.getDetectionHistoryCount返回总记录数
        when(detectionMapper.getDetectionHistoryCount(anyLong(), anyString(), anyString(), anyString())).thenReturn(1);
        // 模拟detectionMapper.getDetectionHistory返回检测历史列表
        List<Detection> detections = new ArrayList<>();
        detections.add(detection);
        when(detectionMapper.getDetectionHistory(anyLong(), anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(detections);

        // 调用getDetectionHistory方法
        PageResult result = detectionService.getDetectionHistory(1L, "2023-01-01", "2023-01-31", "person", 1, 10);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertNotNull(result.getRecords());
        verify(detectionMapper, times(1)).getDetectionHistoryCount(1L, "2023-01-01", "2023-01-31", "person");
        verify(detectionMapper, times(1)).getDetectionHistory(1L, "2023-01-01", "2023-01-31", "person", 0, 10);
    }

    @Test
    public void testAnalyzeDetectionResult() {
        // 模拟detectionMapper.getDetectionById返回检测记录
        when(detectionMapper.getDetectionById(anyLong())).thenReturn(detection);
        // 模拟detectionMapper.getDetectionResultsByDetectionId返回检测结果
        when(detectionMapper.getDetectionResultsByDetectionId(anyLong())).thenReturn(new ArrayList<>());

        // 调用analyzeDetectionResult方法
        var result = detectionService.analyzeDetectionResult(1L);

        // 验证结果
        assertNotNull(result);
        verify(detectionMapper, times(1)).getDetectionById(1L);
        verify(detectionMapper, times(1)).getDetectionResultsByDetectionId(1L);
    }
}
