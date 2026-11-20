package com.madepozo.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.madepozo.backend.domain.entities.Task;

public interface TaskService {

    List<Task> getAllTasksByTaskListId(UUID taskListId);

    Task createTask(UUID taskListId, Task task);

    Optional<Task> getTaskById(UUID taskListId, UUID taskId);

    Task updateTask(UUID taskListId, UUID taskId, Task task);

    void deleteTask(UUID taskListId, UUID taskId);
}
