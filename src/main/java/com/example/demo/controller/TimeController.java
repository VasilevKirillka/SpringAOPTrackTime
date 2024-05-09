package com.example.demo.controller;

import com.example.demo.service.TimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.TrackTimeDto;

import java.util.List;

@RestController
@RequestMapping("/time")
@RequiredArgsConstructor
@Tag(name = "Управление данными о времени", description = "Методы для получения статистики по времени выполнения методов")
public class TimeController {

    private final TimeService timeService;


    @Operation(
            summary = "Получение метода по id",
            description = "Выводит статистику метода по id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<TrackTimeDto> getTimeDtoById(@PathVariable("id") @Parameter(description = "Идентификатор сущности по отслеживанию времени") Long id) {
        return ResponseEntity.ok().body(timeService.getTimeDto(id));
    }

    @Operation(
            summary = "Получение метода по названию",
            description = "Выводит статистику метода по названию"
    )
    @GetMapping("/methodName/{prefix}")
    public ResponseEntity<List<TrackTimeDto>> getMethodsByName(@PathVariable @Parameter(description = "Название метода(начальные символы)") String prefix) {
        return ResponseEntity.ok().body(timeService.getMethodsByName(prefix));
    }

    @Operation(
            summary = "Получение статистики всех методов",
            description = "Выводит статистику всех методов"
    )
    @GetMapping
    public ResponseEntity<List<TrackTimeDto>> getAllAddMethods() {
        return ResponseEntity.ok().body(timeService.getAllMethods());
    }

    @Operation(
            summary = "Получение общего времени выполнения метода",
            description = "Выводит статистику по общему времени выполнения метода/методов по названию"
    )
    @GetMapping("/totalTimeMethodName/{prefix}")
    public ResponseEntity<String> getTotalTimeMethodsByName(@PathVariable @Parameter(description = "Название метода(начальные символы)") String prefix) {
        return ResponseEntity.ok()
                .body(String.format("Общее время выполнения методов %s составило %s мс",
                        prefix, timeService.getTotalTimeMethodsByName(prefix)));
    }
}
