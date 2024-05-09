package com.example.demo.controller;

import com.example.demo.dto.ToDoDto;
import com.example.demo.service.ToDoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
@Tag(name = "Управление списком задач", description = "Методы добавления и вывода задачи/списка задач")
public class ToDoController {

    private final ToDoService toDoService;

    @Operation(summary = "Получение задачи по ее id",
            description = "Метод выводит задачу по ее id номеру")
    @GetMapping("/{id}")
    public ResponseEntity<?> getToDo(@PathVariable("id") @Parameter(description = "Идентификатор задачи") Long id) {
        return ResponseEntity.ok().body(toDoService.getToDo(id));
    }

    @Operation(summary = "Получение списка всех задач",
            description = "Метод выводит список всех задач из БД")
    @GetMapping("/all")
    public ResponseEntity<?> getAllToDo() {
        return ResponseEntity.ok().body(toDoService.getAllToDO());
    }

    @Operation(summary = "Добавление задачи",
            description = "Метод добавляет задачу с ее названием и описанием в БД")
    @PostMapping()
    public ResponseEntity<?> addToDo(@RequestBody ToDoDto toDoDto) {
        return ResponseEntity.ok().body(toDoService.addToDo(toDoDto));
    }

    @Operation(summary = "Добавление списка задач",
            description = "Метод добавляет список задач с их названиями и описаниями в БД")
    @PostMapping("/all")
    public ResponseEntity<?> addToDos(@RequestBody List<ToDoDto> toDoDtoList) {
        return ResponseEntity.ok().body(toDoService.addAllToDo(toDoDtoList));
    }

    @Operation(summary = "Удаление задачи",
            description = "Метод удаляет задачу по id")
    @DeleteMapping("/del/{id}")
    public ResponseEntity<?> deleteToDoById(@PathVariable("id") Long id) {
        toDoService.deleteToDo(id);
        return ResponseEntity.ok().body(String.format("Задача с id = %s удалена", id));
    }

}
