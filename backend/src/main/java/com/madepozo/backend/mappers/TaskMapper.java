package com.madepozo.backend.mappers;

import com.madepozo.backend.domain.dto.TaskDTO;
import com.madepozo.backend.domain.entities.Task;

public interface TaskMapper {

    Task fromDTO(TaskDTO taskDTO);

    TaskDTO toDTO(Task task);
}
