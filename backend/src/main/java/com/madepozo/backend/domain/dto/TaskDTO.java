package com.madepozo.backend.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.madepozo.backend.domain.enums.TaskPriority;
import com.madepozo.backend.domain.enums.TaskStatus;

public record TaskDTO(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskStatus status,
        TaskPriority priority) {
}
