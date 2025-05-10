package com.bitstack.tasktracker.repository;

import com.bitstack.tasktracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
