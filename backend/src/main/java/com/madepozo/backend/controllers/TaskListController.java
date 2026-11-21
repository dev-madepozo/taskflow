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

import com.madepozo.backend.domain.dto.TaskListDTO;
import com.madepozo.backend.domain.dto.TaskListSummaryDTO;
import com.madepozo.backend.domain.entities.TaskList;
import com.madepozo.backend.mappers.TaskListMapper;
import com.madepozo.backend.services.TaskListService;

@RestController
@RequestMapping("/task-lists")
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public List<TaskListSummaryDTO> listTaskLists() {
        return taskListService.getAllTaskLists();
    }

    @GetMapping("/{taskListId}")
    public Optional<TaskListDTO> getTaskList(@PathVariable UUID taskListId) {
        return taskListService.getTaskList(taskListId).map(taskListMapper::toDTO);
    }

    @PostMapping
    public TaskListDTO createTaskList(@RequestBody TaskListDTO taskListDTO) {
        TaskList createdTaskList = taskListService.createTaskList(
                taskListMapper.fromDTO(taskListDTO));

        return taskListMapper.toDTO(createdTaskList);
    }

    @PutMapping("/{taskListId}")
    public TaskListDTO updateTaskList(@PathVariable UUID taskListId, @RequestBody TaskListDTO taskListDTO) {
        TaskList updatedTaskList = taskListService.updateTaskList(taskListId, taskListMapper.fromDTO(taskListDTO));
        return taskListMapper.toDTO(updatedTaskList);
    }

    @DeleteMapping("/{taskListId}")
    public void deleteTaskList(@PathVariable UUID taskListId) {
        taskListService.deleteTaskList(taskListId);
    }
}
