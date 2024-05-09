package com.example.demo.service;

import com.example.demo.dto.ToDoDto;
import com.example.demo.entity.ToDoEntity;
import com.example.demo.exception.ToDoException;
import com.example.demo.mapper.ToDoMapper;
import com.example.demo.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToDoServiceTest {

    @Mock
    private ToDoRepository toDoRepository;

    @Mock
    private ToDoMapper mapper;

    @InjectMocks
    private ToDoService toDoService;

    @BeforeEach
    void init(){
        toDoService= new ToDoService(toDoRepository, mapper);
    }


    @Test
    public void testGetToDo_ValidId_ReturnsToDoDto() {
        Long id = 1L;
        ToDoEntity toDoEntity = new ToDoEntity(id, "Hello", "World");

        ToDoDto toDoDto = new ToDoDto("Hello", "World");

        when(toDoRepository.findById(id)).thenReturn(Optional.of(toDoEntity));
        when(mapper.entityToDto(toDoEntity)).thenReturn(toDoDto);

        ToDoDto result = toDoService.getToDo(id);

        assertNotNull(result);
        assertEquals(toDoDto, result);
    }

    @Test
    public void testGetToDo_InvalidId_ThrowsException() {
        Long id = 1L;
        when(toDoRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ToDoException.class, () -> toDoService.getToDo(id));
    }

    @Test
    public void testGetAllToDo() {
        Long id = 1L;
        Long id2 = 2L;

        List<ToDoEntity> toDoEntityList = List.of(new ToDoEntity(id, "Hello", "World"),
                new ToDoEntity(id2, "Hello", "World"));

        List<ToDoDto> toDoDtoList = List.of(new ToDoDto(id,"Hello", "World"),
                new ToDoDto(id,"Hello", "World"));

        when(toDoRepository.findAll()).thenReturn(toDoEntityList);
        when(mapper.entityToDtoList(toDoEntityList)).thenReturn(toDoDtoList);

        List <ToDoDto> result = toDoService.getAllToDO();

        assertNotNull(result);
        assertEquals(toDoDtoList.size(), result.size());
    }





    @Test
    public void testAddToDo_ValidData() {
        ToDoDto inputDto = new ToDoDto("Hello", "World");
        ToDoEntity entity = new ToDoEntity(1L, "Hello", "World");

        when(mapper.dtoToEntity(inputDto)).thenReturn(entity);
        when(toDoRepository.save(entity)).thenReturn(entity);
        when(mapper.entityToDto(entity)).thenReturn(inputDto);

        ToDoDto result = toDoService.addToDo(inputDto);

        assertNotNull(result);
        assertEquals("Hello", result.getTitle());
        assertEquals("World", result.getDescription());
        verify(toDoRepository, times(1)).save(entity);
    }

    @Test
    public void testAddToDo_InvalidData() {
        // Arrange
        ToDoDto invalidDto = new ToDoDto("Task 2", null);

        // Act and Assert
        assertThrows(ToDoException.class, () -> toDoService.addToDo(invalidDto));
    }

    @Test
    public void testAddToDoTest_ValidData() {
        ToDoDto inputDto = new ToDoDto("Название задачи", "Описание задачи");
        ToDoEntity savedEntity = new ToDoEntity(1L, "Название задачи1", "Описание задачи");

        when(mapper.dtoToEntity(any())).thenReturn(savedEntity);
        when(toDoRepository.save(any())).thenReturn(savedEntity);
        when(mapper.entityToDto(any())).thenReturn(inputDto);

        ToDoDto result = toDoService.addToDo(inputDto);


        assertNotNull(result);
        verify(mapper, times(1)).dtoToEntity(any());
        verify(mapper, times(1)).entityToDto(any());
        verify(toDoRepository, times(1)).save(any());
        assertEquals(inputDto.getTitle(), result.getTitle());
        assertEquals(inputDto.getDescription(), result.getDescription());
    }

    @Test
    public void testAddAllToDo_EmptyList() {
        List<ToDoDto> emptyList = List.of();
        assertThrows(ToDoException.class, () -> toDoService.addAllToDo(emptyList));
    }
}

