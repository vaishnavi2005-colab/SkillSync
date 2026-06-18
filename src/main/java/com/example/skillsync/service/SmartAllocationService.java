package com.example.skillsync.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.skillsync.dto.TaskRequestDTO;
import com.example.skillsync.model.AvailabilityStatus;
import com.example.skillsync.model.Employee;
import com.example.skillsync.model.EmployeeSkill;
import com.example.skillsync.model.Skill;
import com.example.skillsync.model.Task;
import com.example.skillsync.model.TaskComplexity;
import com.example.skillsync.model.TaskStatus;
import com.example.skillsync.repository.EmployeeRepository;
import com.example.skillsync.repository.EmployeeSkillRepository;
import com.example.skillsync.repository.SkillRepository;
import com.example.skillsync.repository.TaskRepository;

@Service
public class SmartAllocationService {

private final TaskRepository taskRepository;
private final SkillRepository skillRepository;
private final EmployeeSkillRepository employeeSkillRepository;
private final EmployeeRepository employeeRepository;

public SmartAllocationService(
        TaskRepository taskRepository,
        SkillRepository skillRepository,
        EmployeeSkillRepository employeeSkillRepository,
        EmployeeRepository employeeRepository) {

    this.taskRepository = taskRepository;
    this.skillRepository = skillRepository;
    this.employeeSkillRepository = employeeSkillRepository;
    this.employeeRepository = employeeRepository;
}

public Task createAndAssignTask(TaskRequestDTO dto) {

    Skill skill = skillRepository.findById(
            dto.getRequiredSkillId())
            .orElseThrow(() ->
                    new RuntimeException("Skill not found"));

    Employee employee = findBestEmployee(skill);

    Task task = new Task();

    task.setTitle(dto.getTitle());
    task.setDescription(dto.getDescription());
    task.setRequiredSkill(skill);

    task.setComplexity(
            TaskComplexity.valueOf(
                    dto.getComplexity()));

    task.setDeadline(dto.getDeadline());

    task.setCreatedAt(LocalDateTime.now());

    if (employee != null) {

        task.setAssignedEmployee(employee);

        task.setStatus(TaskStatus.IN_PROGRESS);

        employee.setCurrentTaskCount(
                employee.getCurrentTaskCount() + 1);

        employeeRepository.save(employee);

    } else {

        task.setStatus(TaskStatus.PENDING);
    }

    return taskRepository.save(task);
}

public Employee findBestEmployee(Skill skill) {

    List<EmployeeSkill> matchingEmployees =
            employeeSkillRepository.findBySkill(skill);

    Optional<EmployeeSkill> bestEmployee =
            matchingEmployees.stream()

                    .filter(es ->
                            es.getEmployee().getAvailability()
                                    == AvailabilityStatus.AVAILABLE)

                    .filter(es ->
                            es.getEmployee().getCurrentTaskCount()
                                    < es.getEmployee().getMaxCapacity())

                    .min(Comparator.comparingInt(
                            es -> es.getEmployee()
                                    .getCurrentTaskCount()));

    return bestEmployee
            .map(EmployeeSkill::getEmployee)
            .orElse(null);
}

public Task allocateTask(Long taskId) {

    Task task = taskRepository.findById(taskId)
            .orElseThrow(() ->
                    new RuntimeException("Task not found"));

    if (task.getStatus() != TaskStatus.PENDING) {
        return task;
    }

    Employee employee =
            findBestEmployee(task.getRequiredSkill());

    if (employee == null) {
        throw new RuntimeException(
                "No suitable employee available");
    }

    task.setAssignedEmployee(employee);
    task.setStatus(TaskStatus.IN_PROGRESS);

    employee.setCurrentTaskCount(
            employee.getCurrentTaskCount() + 1);

    employeeRepository.save(employee);

    return taskRepository.save(task);
}


}
