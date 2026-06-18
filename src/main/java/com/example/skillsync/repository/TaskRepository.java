package com.example.skillsync.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.skillsync.model.Task;
import com.example.skillsync.model.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {

List<Task> findByStatus(TaskStatus status);

List<Task> findByStatusOrderByCreatedAtAsc(
        TaskStatus status);

List<Task> findByDeadlineBeforeAndStatusNot(
        LocalDateTime deadline,
        TaskStatus status);

}
