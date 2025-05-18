package com.bitstack.tasktracker.controller;

import com.bitstack.tasktracker.DTO.TaskResponseDTO;
import com.bitstack.tasktracker.model.Task;
import com.bitstack.tasktracker.model.User;
import com.bitstack.tasktracker.repository.TaskRepository;
import com.bitstack.tasktracker.repository.UserRepository;
import com.bitstack.tasktracker.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/tasks")
public class AppController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    
    private TaskResponseDTO mapToDto(Task task) {
		TaskResponseDTO dto = new TaskResponseDTO();
		dto.setId(task.getId());
		dto.setTitle(task.getTitle());
		dto.setDescription(task.getDescription());
		dto.setDueDate(task.getDueDate());
		dto.setCompleted(task.isCompleted());
		dto.setCreatedBy(task.getCreatedBy());
		dto.setUpdatedBy(task.getUpdatedBy());
		dto.setCreatedAt(task.getCreatedAt());
		dto.setUpdatedAt(task.getUpdatedAt());
		dto.setAssignedToEmail(task.getAssignedTo() != null ? task.getAssignedTo().getEmail() : null);
		dto.setOwnerEmail(task.getUser() != null ? task.getUser().getEmail() : null);
		return dto;
	}
    
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<Task> getAllTasks(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        if ("ADMIN".equals(user.getRole())) {
            return taskRepository.findAll(); // admin sees all tasks
        }
        
        return taskRepository.findByUser(user); // regular user sees only their tasks
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id, Authentication authentication) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        if (!task.getUser().getUsername().equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        return task;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Map<String, Object> payload, Authentication authentication) {
        String currentUsername = authentication.getName();

        // Extract fields from JSON
        String title = (String) payload.get("title");
        String description = (String) payload.get("description");
        String dueDateStr = (String) payload.get("dueDate");
        String assignedToUsername = (String) payload.get("assignedToUsername");

        if (title == null || description == null || dueDateStr == null) {
            return ResponseEntity.badRequest().body("Title, description, and dueDate are required.");
        }

        try {
            Task task = new Task();
            task.setTitle(title);
            task.setDescription(description);
            task.setDueDate(LocalDate.parse(dueDateStr));
            task.setCompleted(false);

            // The user who created the task
            User creator = userRepository.findByUsername(currentUsername)
                                         .orElseThrow(() -> new RuntimeException("Creator not found"));

            task.setUser(creator); // creator always set

            // Optional assignment logic
            if (assignedToUsername != null && !assignedToUsername.isEmpty()) {
                User assignee = userRepository.findByUsername(assignedToUsername)
                                              .orElseThrow(() -> new RuntimeException("Assignee user not found"));

                if (!creator.getRole().equals("ADMIN") && !creator.getUsername().equals(assignedToUsername)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("You are not allowed to assign tasks to other users.");
                }

                task.setAssignedTo(assignee);
            } else {
                task.setAssignedTo(creator); // fallback to self
            }

            Task savedTask = taskRepository.save(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating task: " + e.getMessage());
        }
    }


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails, Authentication authentication) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        if (!task.getUser().getUsername().equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setDueDate(taskDetails.getDueDate());
        task.setCompleted(taskDetails.isCompleted());
        task.setUpdatedBy(authentication.getName());
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        taskRepository.deleteById(id);
    }

    
    // ✅ Admin: Get tasks they created (even those assigned to others)
    @GetMapping("/created-by-me")
    public ResponseEntity<List<Task>> getTasksCreatedByAdmin(Authentication authentication) {
        return ResponseEntity.ok(taskRepository.findByCreatedBy(authentication.getName()));
    }

    // ✅ Admin: Get tasks they assigned to other users (delegated tasks)
    @GetMapping("/delegated")
    public ResponseEntity<List<Task>> getDelegatedTasks(Authentication authentication) {
        return ResponseEntity.ok(taskRepository.findDelegatedTasksByCreator(authentication.getName()));
    }

    // ✅ User: Get tasks assigned to them *by an admin*
    @GetMapping("/assigned-to-me/by-admin")
    public ResponseEntity<List<Task>> getTasksAssignedToMeByAdmin(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(taskRepository.findTasksAssignedToUserByCreator(username, "admin"));
    }


    @GetMapping("/tasks")
    public List<TaskResponseDTO> getTasks(Authentication auth) {
        User currentUser = userService.getCurrentUser(auth);
        List<Task> tasks = taskRepository.findByUser(currentUser);
        return tasks.stream().map(this::mapToDto).collect(Collectors.toList());
    }

}
