package com.madepozo.backend.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.madepozo.backend.domain.dto.PageResponse;
import com.madepozo.backend.domain.dto.TaskDTO;
import com.madepozo.backend.domain.entities.Task;
import com.madepozo.backend.domain.enums.TaskStatus;
import com.madepozo.backend.mappers.TaskMapper;
import com.madepozo.backend.services.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/task-lists/{taskListId}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public PageResponse<TaskDTO> listTasksByTaskListId(
            @PathVariable UUID taskListId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TaskStatus status) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("title").ascending().and(Sort.by("id").ascending()));
        Page<TaskDTO> tasks = taskService
                .getAllTasksByTaskListId(taskListId, status, pageable).map(taskMapper::toDTO);
        return PageResponse.from(tasks);
    }

    @PostMapping
    public TaskDTO createTask(@PathVariable UUID taskListId, @Valid @RequestBody TaskDTO taskDTO) {
        Task createdTask = taskService.createTask(taskListId, taskMapper.fromDTO(taskDTO));
        return taskMapper.toDTO(createdTask);
    }

    @GetMapping("/{taskId}")
    public Optional<TaskDTO> getTaskById(@PathVariable UUID taskListId, @PathVariable UUID taskId) {
        return taskService.getTaskById(taskListId, taskId).map(taskMapper::toDTO);
    }

    @PutMapping("/{taskId}")
    public TaskDTO updateTask(@PathVariable UUID taskListId, @PathVariable UUID taskId,
            @Valid @RequestBody TaskDTO taskDTO) {
        Task updatedTask = taskService.updateTask(taskListId, taskId, taskMapper.fromDTO(taskDTO));
        return taskMapper.toDTO(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable UUID taskListId, @PathVariable UUID taskId) {
        taskService.deleteTask(taskListId, taskId);
    }
}
