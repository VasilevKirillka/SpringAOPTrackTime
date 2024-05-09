package com.example.demo.service;

import com.example.demo.dto.TrackTimeDto;
import com.example.demo.entity.TrackTimeEntity;
import com.example.demo.mapper.TimeMapper;
import com.example.demo.repository.TimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeServiceTest {

    @Mock
    private TimeRepository timeRepository;

    @Mock
    private TimeMapper mapper;

    @InjectMocks
    private TimeService timeService;

    @BeforeEach
    void init() {
        timeService = new TimeService(timeRepository, mapper);
    }


    @Test
    void getTimeDto() {
        Long id = 1L;
        TrackTimeEntity timeEntity = new TrackTimeEntity(id, "methodName", 45L);

        TrackTimeDto timeDto = new TrackTimeDto("methodName", 45L);

        when(timeRepository.findById(id)).thenReturn(Optional.of(timeEntity));
        when(mapper.entityToDto(timeEntity)).thenReturn(timeDto);

        TrackTimeDto result = timeService.getTimeDto(id);

        assertNotNull(result);
        assertEquals(timeDto, result);
    }

    @Test
    void getMethodsByName() {
        String prefix = "methodName";
        List<TrackTimeEntity> timeEntityList = List.of(new TrackTimeEntity(1L, "methodName", 45L),
                new TrackTimeEntity(2L, "methodsName", 55L));

        List<TrackTimeDto> timeDtoList = List.of(new TrackTimeDto(1L, "methodName", 45L),
                new TrackTimeDto(2L, "methodsName", 55L));

        when(timeRepository.findByMethodNameStartingWith(prefix)).thenReturn(timeEntityList);
        when(mapper.entityToDtoList(timeEntityList)).thenReturn(timeDtoList);


        List<TrackTimeDto> result = timeService.getMethodsByName(prefix);

        assertNotNull(result);
        assertEquals(timeDtoList, result);
    }

}