package com.bitstack.tasktracker.service;

import com.bitstack.tasktracker.DTO.TaskResponseDTO;
import com.bitstack.tasktracker.model.Task;
import com.bitstack.tasktracker.model.User;
import com.bitstack.tasktracker.repository.TaskRepository;
import com.bitstack.tasktracker.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // ==== Task Services ====
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
    
    public TaskResponseDTO mapToDto(Task task) {
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

    
    	
    // ==== User Services ====
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }


    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean userExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
