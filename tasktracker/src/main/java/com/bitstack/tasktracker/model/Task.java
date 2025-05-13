package com.bitstack.tasktracker.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.persistence.Id;

import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Represents a task assigned to a user")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the task", example = "1001")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Title of the task", example = "Finish API integration")
    private String title;

    @Schema(description = "Detailed description of the task", example = "Connect frontend with backend using REST API")
    private String description;

    @Schema(description = "Due date for the task", example = "2025-05-20")
    private LocalDate dueDate;

    @Schema(description = "Indicates if the task is completed", example = "false")
    private boolean completed;

    // Auditing Fields
    @CreatedBy
    @Schema(description = "Username who created the task", example = "admin")
    private String createdBy;

    @LastModifiedBy
    @Schema(description = "Username who last updated the task", example = "johndoe")
    private String updatedBy;

    @CreatedDate
    @Schema(description = "When the task was created", example = "2025-05-10T10:00:00")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Schema(description = "When the task was last updated", example = "2025-05-12T08:30:00")
    private LocalDateTime updatedAt;

    // Relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    // Getters and setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() { return dueDate; }

    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public boolean isCompleted() { return completed; }

    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getCreatedBy() { return createdBy; }

    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getUpdatedBy() { return updatedBy; }

    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
