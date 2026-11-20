package com.madepozo.backend.mappers.impl;

import org.springframework.stereotype.Component;

import com.madepozo.backend.domain.dto.TaskDTO;
import com.madepozo.backend.domain.entities.Task;
import com.madepozo.backend.mappers.TaskMapper;

@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task fromDTO(TaskDTO taskDTO) {
        return new Task(
                taskDTO.id(),
                taskDTO.title(),
                taskDTO.description(),
                taskDTO.dueDate(),
                taskDTO.status(),
                taskDTO.priority(),
                null, null,
                null);
    }

    @Override
    public TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                task.getPriority());
    }

}
