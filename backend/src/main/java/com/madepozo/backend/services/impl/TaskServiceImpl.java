package com.madepozo.backend.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.madepozo.backend.domain.entities.Task;
import com.madepozo.backend.domain.entities.TaskList;
import com.madepozo.backend.domain.enums.TaskPriority;
import com.madepozo.backend.domain.enums.TaskStatus;
import com.madepozo.backend.exceptions.ResourceNotFoundException;
import com.madepozo.backend.repositories.TaskListRepository;
import com.madepozo.backend.repositories.TaskRepository;
import com.madepozo.backend.services.TaskService;

import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (null == task.getTitle() || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task must have a title.");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task List not found with ID: " + taskListId));

        LocalDateTime now = LocalDateTime.now();

        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                now,
                now,
                taskList);

        return taskRepository.save(taskToSave);
    }

    @Override
    public List<Task> getAllTasksByTaskListId(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    public Optional<Task> getTaskById(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if (null == task.getPriority()) {
            throw new IllegalArgumentException("Task must have a valid priority.");
        }

        if (null == task.getStatus()) {
            throw new IllegalArgumentException("Task must have a valid status.");
        }

        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found."));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        existingTask.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
