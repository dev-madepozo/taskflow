package com.madepozo.backend.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.madepozo.backend.domain.entities.Task;
import com.madepozo.backend.domain.enums.TaskStatus;

public interface TaskService {

    Page<Task> getAllTasksByTaskListId(UUID taskListId, TaskStatus status, Pageable pageable);

    Task createTask(UUID taskListId, Task task);

    Optional<Task> getTaskById(UUID taskListId, UUID taskId);

    Task updateTask(UUID taskListId, UUID taskId, Task task);

    void deleteTask(UUID taskListId, UUID taskId);
}
