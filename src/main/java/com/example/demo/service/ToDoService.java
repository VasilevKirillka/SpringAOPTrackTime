package com.example.demo.service;

import com.example.demo.annotation.TrackAsyncTime;
import com.example.demo.annotation.TrackTime;
import com.example.demo.dto.ToDoDto;
import com.example.demo.entity.ToDoEntity;
import com.example.demo.exception.ToDoException;
import com.example.demo.mapper.ToDoMapper;
import com.example.demo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final ToDoMapper mapper;

    @TrackTime
    public ToDoDto getToDo(Long id) {
        Optional<ToDoEntity> optionalEntity = toDoRepository.findById(id);
        if (optionalEntity.isPresent()) {
            ToDoEntity entity = optionalEntity.get();
            ToDoDto dto = mapper.entityToDto(entity);
            return dto;
        } else {
            throw new ToDoException(String.format("id = %s не найден", id)); // или вернуть пустой объект ToDoDto
        }
    }

    @TrackTime
    public List<ToDoDto> getAllToDO() {
        var entity = toDoRepository.findAll();
        var dto = mapper.entityToDtoList(entity);
        return dto;
    }

    @TrackAsyncTime
    public ToDoDto addToDo(ToDoDto dto) {
        if (dto != null && validate(dto)) {
            ToDoDto newToDo = createToDoDto(dto);
            ToDoEntity newEntity = mapper.dtoToEntity(newToDo);
            toDoRepository.save(newEntity);
            return mapper.entityToDto(newEntity);
        } else {
            throw new ToDoException("Некорректные данные для ToDoDto");
        }
    }

    @TrackAsyncTime
    public List<ToDoDto> addAllToDo(List<ToDoDto> dtoList) {
        if (!dtoList.isEmpty()) {
            List<ToDoDto> list = new ArrayList<>();
            dtoList.forEach(dto -> {
                if (validate(dto)) {
                    ToDoDto newToDo = createToDoDto(dto);
                    list.add(newToDo);
                } else {
                    throw new ToDoException("Некорректные данные для ToDoDto");
                }
            });
            List<ToDoEntity> entityList = mapper.dtoToEntityList(list);
            toDoRepository.saveAll(entityList);
            return mapper.entityToDtoList(entityList);
        } else {
            throw new ToDoException("Список Dto пустой");
        }
    }

    public void deleteToDo(Long id) {
        Optional<ToDoEntity> optionalEntity = toDoRepository.findById(id);
        if (optionalEntity.isPresent()) {
            toDoRepository.deleteById(id);
        } else {
            throw new ToDoException(String.format("id = %s не найден", id)); // или вернуть пустой объект ToDoDto
        }
    }

    private static ToDoDto createToDoDto(ToDoDto dto) {
        ToDoDto newToDo = new ToDoDto();
        newToDo.setTitle(dto.getTitle());
        newToDo.setDescription(dto.getDescription());
        return newToDo;
    }

    private static boolean validate(ToDoDto dto) {
        return dto.getTitle() != null && dto.getDescription() != null;
    }

}
