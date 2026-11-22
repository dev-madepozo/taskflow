package com.madepozo.backend.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.madepozo.backend.domain.dto.TaskListSummaryDTO;
import com.madepozo.backend.domain.entities.TaskList;
import com.madepozo.backend.exceptions.ResourceNotFoundException;
import com.madepozo.backend.repositories.TaskListRepository;
import com.madepozo.backend.services.TaskListService;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskListSummaryDTO> getAllTaskLists() {
        return taskListRepository.findAllSummaries();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (null != taskList.getId()) {
            throw new IllegalArgumentException("Task list already has an ID.");
        }

        if (null == taskList.getTitle() || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task List title must be present.");
        }

        LocalDateTime now = LocalDateTime.now();

        return taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                now,
                now,
                null));
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findDetailedById(id);
    }

    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        TaskList existingTaskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new ResourceNotFoundException("Task List not found with ID: " + taskListId));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdatedAt(LocalDateTime.now());

        return taskListRepository.save(existingTaskList);
    }

    @Override
    public void deleteTaskList(UUID taskListId) {
        taskListRepository.deleteById(taskListId);
    }
}
