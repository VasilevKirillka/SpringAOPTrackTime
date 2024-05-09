package com.example.demo.service;

import com.example.demo.dto.TrackTimeDto;
import com.example.demo.entity.TrackTimeEntity;
import com.example.demo.mapper.TimeMapper;
import com.example.demo.repository.TimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimeService {

    private final TimeRepository timeRepository;
    private final TimeMapper mapper;


    public void addTime(String methodName, long endTime) {
        var dto = createToDo(methodName, endTime);
        var entity = mapper.dtoToEntity(dto);
        timeRepository.save(entity);
    }

    public TrackTimeDto getTimeDto(Long id) {
        Optional<TrackTimeEntity> optionalEntity = timeRepository.findById(id);
        if (optionalEntity.isPresent()) {
            TrackTimeEntity entity = optionalEntity.get();
            TrackTimeDto dto = mapper.entityToDto(entity);
            return dto;
        } else {
            return null;
        }
    }

    public List<TrackTimeDto> getMethodsByName(String prefix) {
        return mapper.entityToDtoList(timeRepository.findByMethodNameStartingWith(prefix));
    }

    public List<TrackTimeDto> getAllMethods() {
        return mapper.entityToDtoList(timeRepository.findAll());
    }

    public Long getTotalTimeMethodsByName(String prefix) {
        return mapper.entityToDtoList(timeRepository.findByMethodNameStartingWith(prefix))
                .stream().mapToLong(TrackTimeDto::getTime).sum();
    }

    private TrackTimeDto createToDo(String methodName, long endTime) {
        TrackTimeDto dto = new TrackTimeDto();
        dto.setMethodName(methodName);
        dto.setTime(endTime);
        return dto;
    }
}
