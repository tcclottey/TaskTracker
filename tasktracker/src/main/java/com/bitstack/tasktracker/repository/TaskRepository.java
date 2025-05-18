package com.bitstack.tasktracker.repository;

import com.bitstack.tasktracker.model.Task;
import com.bitstack.tasktracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Tasks directly assigned to this user (regardless of creator)
    List<Task> findByUser(User user);

    // Tasks created by this user
    List<Task> findByCreatedBy(String createdBy);

    // Tasks created by user but assigned to someone else
    @Query("SELECT t FROM Task t WHERE t.createdBy = :creator AND t.user.username <> :creator")
    List<Task> findDelegatedTasksByCreator(String creator);

    // Tasks assigned to a user by a specific creator (e.g., admin)
    @Query("SELECT t FROM Task t WHERE t.user.username = :assigneeUsername AND t.createdBy = :adminUsername")
    List<Task> findTasksAssignedToUserByCreator(String assigneeUsername, String adminUsername);
}
