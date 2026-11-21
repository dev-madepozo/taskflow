package com.madepozo.backend.mappers;

import com.madepozo.backend.domain.dto.TaskListDTO;
import com.madepozo.backend.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDTO(TaskListDTO taskListDTO);

    TaskListDTO toDTO(TaskList taskList);
}
