package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность задачи")
public class ToDoDto {
    @Schema(description = "Идентификатор", example = "1")
    private Long id;
    @Schema(description = "Название", example = "Сходить в магазин")
    private String title;
    @Schema(description = "Описание", example = "Сходить в магазин и купить продукты")
    private String description;

    public ToDoDto(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
