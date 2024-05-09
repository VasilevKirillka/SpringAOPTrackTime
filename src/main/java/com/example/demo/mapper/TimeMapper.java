package com.example.demo.mapper;

import com.example.demo.dto.ToDoDto;
import com.example.demo.dto.TrackTimeDto;
import com.example.demo.entity.ToDoEntity;
import com.example.demo.entity.TrackTimeEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface TimeMapper {

    TrackTimeDto entityToDto(TrackTimeEntity entity);

    TrackTimeEntity dtoToEntity(TrackTimeDto dto);

    List<TrackTimeDto> entityToDtoList(List<TrackTimeEntity> entityList);

    List<TrackTimeEntity> dtoToEntityList(List<TrackTimeDto> dtoList);
}
