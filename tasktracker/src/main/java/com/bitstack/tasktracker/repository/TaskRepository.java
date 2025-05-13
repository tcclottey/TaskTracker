package com.bitstack.tasktracker.repository;

import com.bitstack.tasktracker.model.Task;
import com.bitstack.tasktracker.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// === Task Repository ===
public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByUser(User user);  // Custom query to fetch tasks by user
}


