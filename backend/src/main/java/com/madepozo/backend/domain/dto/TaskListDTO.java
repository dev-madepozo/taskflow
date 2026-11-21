package com.madepozo.backend.domain.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskListDTO(
        UUID id,
        @NotBlank(message = "Task list title is required.") @Size(max = 150, message = "Task list title must not exceed 150 characters.") String title,

        @Size(max = 1000, message = "Task list description must not exceed 1000 characters.") String description,
        Integer count,
        Double progress,
        List<TaskDTO> tasks) {
}
