package com.bitstack.tasktracker.DTO;


import java.time.LocalDate;

public class TaskCreateRequestDTO {

    private String title;
    private String description;
    private LocalDate dueDate;
    private String assignedToUsername; // Optional: only admins may use this

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getAssignedToUsername() { return assignedToUsername; }
    public void setAssignedToUsername(String assignedToUsername) { this.assignedToUsername = assignedToUsername; }
}
