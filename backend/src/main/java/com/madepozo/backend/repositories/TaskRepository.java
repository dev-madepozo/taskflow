package com.madepozo.backend.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madepozo.backend.domain.entities.Task;
import com.madepozo.backend.domain.enums.TaskStatus;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    Page<Task> findByTaskListId(UUID taskListId, Pageable pageable);

    Optional<Task> findByTaskListIdAndId(UUID taskListId, UUID taskId);

    void deleteByTaskListIdAndId(UUID taskListId, UUID taskId);

    Page<Task> findByTaskList_Id(UUID taskListId, Pageable pageable);

    Page<Task> findByTaskList_IdAndStatus(UUID taskListId, TaskStatus status, Pageable pageable);
}
