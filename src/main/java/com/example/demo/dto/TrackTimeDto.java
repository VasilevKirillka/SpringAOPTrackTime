package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность отслеживания времени")
public class TrackTimeDto {
    @Schema(description = "Идентификатор", example = "1")
    private Long id;
    @Schema(description = "Название метода", example = "getAllToDo")
    private String methodName;
    @Schema(description = "Время выполнения в мс", example = "45")
    private Long time;

    public TrackTimeDto(String methodName, Long time) {
        this.methodName = methodName;
        this.time = time;
    }
}
