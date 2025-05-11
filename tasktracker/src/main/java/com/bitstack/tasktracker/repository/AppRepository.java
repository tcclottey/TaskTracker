package com.bitstack.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitstack.tasktracker.model.Task;

public interface AppRepository extends JpaRepository<Task, Long> {
}
