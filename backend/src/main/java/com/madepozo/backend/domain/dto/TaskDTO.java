package com.madepozo.backend.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.madepozo.backend.domain.enums.TaskPriority;
import com.madepozo.backend.domain.enums.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskDTO(
        UUID id,
        @NotBlank(message = "Task title is requierd") @Size(max = 150, message = "Task title must not exceed 150 characters.") String title,

        @Size(max = 1000, message = "Task description must not exceed 1000 characters.") String description,
        LocalDateTime dueDate,
        TaskStatus status,
        TaskPriority priority) {
}
