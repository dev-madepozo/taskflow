package com.madepozo.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.madepozo.backend.domain.dto.TaskListSummaryDTO;
import com.madepozo.backend.domain.entities.TaskList;

public interface TaskListService {

    List<TaskListSummaryDTO> getAllTaskLists();

    TaskList createTaskList(TaskList taskList);

    Optional<TaskList> getTaskList(UUID id);

    TaskList updateTaskList(UUID taskListId, TaskList taskList);

    void deleteTaskList(UUID taskListId);
}
