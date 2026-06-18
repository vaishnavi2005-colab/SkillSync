package com.example.skillsync.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.skillsync.model.Employee;
import com.example.skillsync.model.EmployeeSkill;
import com.example.skillsync.model.Task;
import com.example.skillsync.model.TaskStatus;
import com.example.skillsync.repository.EmployeeRepository;
import com.example.skillsync.repository.EmployeeSkillRepository;
import com.example.skillsync.repository.TaskRepository;

@Service
public class TaskCompletionService {

private final TaskRepository taskRepository;
private final EmployeeRepository employeeRepository;
private final EmployeeSkillRepository employeeSkillRepository;

public TaskCompletionService(
        TaskRepository taskRepository,
        EmployeeRepository employeeRepository,
        EmployeeSkillRepository employeeSkillRepository) {

    this.taskRepository = taskRepository;
    this.employeeRepository = employeeRepository;
    this.employeeSkillRepository = employeeSkillRepository;
}

public Task completeTask(Long taskId) {

    Task task = taskRepository.findById(taskId)
            .orElseThrow();

    task.setStatus(TaskStatus.COMPLETED);
    task.setCompletedAt(LocalDateTime.now());

    Employee employee = task.getAssignedEmployee();

    if (employee != null) {

        employee.setCurrentTaskCount(
                Math.max(
                        employee.getCurrentTaskCount() - 1,
                        0));

        employee.setCompletedTaskCount(
                employee.getCompletedTaskCount() + 1);

        employeeRepository.save(employee);
    }

    taskRepository.save(task);

    assignPendingTask();

    return task;
}

private void assignPendingTask() {

    List<Task> pendingTasks =
            taskRepository.findByStatusOrderByCreatedAtAsc(
                    TaskStatus.PENDING);

    for (Task pendingTask : pendingTasks) {

        List<EmployeeSkill> matchingEmployees =
                employeeSkillRepository.findBySkill(
                        pendingTask.getRequiredSkill());

        for (EmployeeSkill es : matchingEmployees) {

            Employee employee = es.getEmployee();

            if (employee.getCurrentTaskCount()
                    < employee.getMaxCapacity()) {

                pendingTask.setAssignedEmployee(employee);

                pendingTask.setStatus(
                        TaskStatus.IN_PROGRESS);

                employee.setCurrentTaskCount(
                        employee.getCurrentTaskCount() + 1);

                employeeRepository.save(employee);
                taskRepository.save(pendingTask);

                return;
            }
        }
    }
}

}
