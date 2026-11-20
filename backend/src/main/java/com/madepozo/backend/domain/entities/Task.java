package com.madepozo.backend.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.madepozo.backend.domain.enums.TaskPriority;
import com.madepozo.backend.domain.enums.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", nullable = true, length = 1000)
    private String description;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    TaskPriority priority;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_list_id", nullable = false)
    private TaskList taskList;

    public Task() {
    }

    public Task(UUID id, String title, String description, LocalDateTime dueDate, TaskStatus status,
            TaskPriority priority,
            LocalDateTime createdAt, LocalDateTime updatedAt, TaskList taskList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.taskList = taskList;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, dueDate, status, priority, createdAt, updatedAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title)
                && Objects.equals(description, task.description) && Objects.equals(dueDate, task.dueDate)
                && status == task.status && priority == task.priority && Objects.equals(createdAt, task.createdAt)
                && Objects.equals(updatedAt, task.updatedAt) && Objects.equals(taskList, task.taskList);
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", title=" + title + ", description=" + description + ", dueDate=" + dueDate
                + ", status=" + status + ", priority=" + priority + ", createdAt=" + createdAt + ", updatedAt="
                + updatedAt + ", taskList=" + taskList + "]";
    }

}
