package com.example.skillsync.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.skillsync.model.Task;
import com.example.skillsync.service.TaskCompletionService;

@RestController
@RequestMapping("/tasks")
public class TaskCompletionController {

private final TaskCompletionService service;

public TaskCompletionController(
        TaskCompletionService service) {

    this.service = service;
}

@PutMapping("/complete/{id}")
public Task completeTask(
        @PathVariable Long id) {

    return service.completeTask(id);
}

}
