package com.madepozo.backend.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.madepozo.backend.domain.dto.TaskListSummaryDTO;
import com.madepozo.backend.domain.entities.TaskList;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, UUID> {

    @EntityGraph(attributePaths = "tasks")
    Optional<TaskList> findDetailedById(UUID id);

    @Query("""
                SELECT new com.madepozo.backend.domain.dto.TaskListSummaryDTO(
                    tl.id,
                    tl.title,
                    tl.description,
                    COUNT(t),
                    CASE
                        WHEN COUNT(t) = 0 THEN 0.0
                        ELSE CAST(
                            SUM(CASE WHEN t.status = com.madepozo.backend.domain.enums.TaskStatus.CLOSED
                                THEN 1L ELSE 0L END) AS double
                        ) / COUNT(t)
                    END
                )
                FROM TaskList tl
                LEFT JOIN tl.tasks t
                GROUP BY tl.id, tl.title, tl.description
                ORDER BY tl.createdAt DESC
            """)
    List<TaskListSummaryDTO> findAllSummaries();
}
