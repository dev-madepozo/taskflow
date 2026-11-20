package com.madepozo.backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madepozo.backend.domain.entities.TaskList;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, UUID> {

}
