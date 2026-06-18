package com.example.skillsync.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.skillsync.model.Employee;
import com.example.skillsync.model.Task;
import com.example.skillsync.model.TaskStatus;
import com.example.skillsync.repository.EmployeeRepository;
import com.example.skillsync.repository.TaskRepository;

@Service
public class TaskService {

private final TaskRepository taskRepository;
private final EmployeeRepository employeeRepository;

public TaskService(
        TaskRepository taskRepository,
        EmployeeRepository employeeRepository) {

    this.taskRepository = taskRepository;
    this.employeeRepository = employeeRepository;
}

public Task saveTask(Task task) {
    return taskRepository.save(task);
}

public List<Task> getAllTasks() {
    return taskRepository.findAll();
}

public Task getTaskById(Long id) {
    return taskRepository.findById(id).orElse(null);
}

public Task updateTask(Long id, Task updatedTask) {

    Task task = taskRepository.findById(id).orElse(null);

    if (task == null) {
        return null;
    }

    if (task.getStatus() != TaskStatus.COMPLETED
            && updatedTask.getStatus() == TaskStatus.COMPLETED) {

        task.setCompletedAt(LocalDateTime.now());

        Employee employee =
                task.getAssignedEmployee();

        if (employee != null) {

            employee.setCurrentTaskCount(
                    Math.max(
                            employee.getCurrentTaskCount() - 1,
                            0));

            employee.setCompletedTaskCount(
                    employee.getCompletedTaskCount() + 1);

            employeeRepository.save(employee);
        }
    }

    task.setTitle(updatedTask.getTitle());
    task.setDescription(updatedTask.getDescription());
    task.setRequiredSkill(updatedTask.getRequiredSkill());
    task.setComplexity(updatedTask.getComplexity());
    task.setStatus(updatedTask.getStatus());
    task.setDeadline(updatedTask.getDeadline());
    task.setAssignedEmployee(updatedTask.getAssignedEmployee());

    return taskRepository.save(task);
}

public void deleteTask(Long id) {
    taskRepository.deleteById(id);
}

}
