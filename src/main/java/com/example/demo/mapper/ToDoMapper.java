package com.example.demo.mapper;

import com.example.demo.dto.ToDoDto;
import com.example.demo.entity.ToDoEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ToDoMapper {

    ToDoDto entityToDto(ToDoEntity entity);

    ToDoEntity dtoToEntity(ToDoDto dto);

    List<ToDoDto> entityToDtoList(List<ToDoEntity> entityList);

    List<ToDoEntity> dtoToEntityList(List<ToDoDto> dtoList);

}
