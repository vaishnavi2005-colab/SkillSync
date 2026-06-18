package com.example.skillsync.controller;

import org.springframework.web.bind.annotation.*;

import com.example.skillsync.dto.TaskRequestDTO;
import com.example.skillsync.model.Task;
import com.example.skillsync.service.SmartAllocationService;

@RestController
@RequestMapping("/allocation")
public class AllocationController {

private final SmartAllocationService service;

public AllocationController(
        SmartAllocationService service) {

    this.service = service;
}

@PostMapping("/assign")
public Task assignTask(
        @RequestBody TaskRequestDTO dto) {

    return service.createAndAssignTask(dto);
}

@PostMapping("/assign-existing/{taskId}")
public Task assignExistingTask(
        @PathVariable Long taskId) {

    return service.allocateTask(taskId);
}


}
