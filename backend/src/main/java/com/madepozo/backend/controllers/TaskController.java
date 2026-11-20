package com.madepozo.backend.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madepozo.backend.domain.dto.TaskDTO;
import com.madepozo.backend.domain.entities.Task;
import com.madepozo.backend.mappers.TaskMapper;
import com.madepozo.backend.services.TaskService;

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
    public List<TaskDTO> listTasksByTaskListId(@PathVariable UUID taskListId) {
        return taskService.getAllTasksByTaskListId(taskListId)
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    @PostMapping
    public TaskDTO createTask(@PathVariable UUID taskListId, @RequestBody TaskDTO taskDTO) {
        Task createdTask = taskService.createTask(taskListId, taskMapper.fromDTO(taskDTO));
        return taskMapper.toDTO(createdTask);
    }

    @GetMapping("/{taskId}")
    public Optional<TaskDTO> getTaskById(@PathVariable UUID taskListId, @PathVariable UUID taskId) {
        return taskService.getTaskById(taskListId, taskId).map(taskMapper::toDTO);
    }

    @PutMapping("/{taskId}")
    public TaskDTO updateTask(@PathVariable UUID taskListId, @PathVariable UUID taskId, @RequestBody TaskDTO taskDTO) {
        Task updatedTask = taskService.updateTask(taskListId, taskId, taskMapper.fromDTO(taskDTO));
        return taskMapper.toDTO(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable UUID taskListId, @PathVariable UUID taskId) {
        taskService.deleteTask(taskListId, taskId);
    }
}
