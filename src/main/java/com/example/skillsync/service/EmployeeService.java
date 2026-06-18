package com.example.skillsync.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.skillsync.model.Employee;
import com.example.skillsync.repository.EmployeeRepository;

@Service
public class EmployeeService {

private final EmployeeRepository employeeRepository;

public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
}

public Employee saveEmployee(Employee employee) {
    return employeeRepository.save(employee);
}

public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
}

public Employee getEmployeeById(Long id) {
    return employeeRepository.findById(id).orElse(null);
}

public Employee updateEmployee(
        Long id,
        Employee updatedEmployee) {

    Employee employee =
            employeeRepository.findById(id)
                    .orElse(null);

    if (employee == null) {
        return null;
    }

    employee.setName(updatedEmployee.getName());
    employee.setEmail(updatedEmployee.getEmail());
    employee.setAvailability(
            updatedEmployee.getAvailability());
    employee.setMaxCapacity(
            updatedEmployee.getMaxCapacity());
    employee.setCurrentTaskCount(
            updatedEmployee.getCurrentTaskCount());

    return employeeRepository.save(employee);
}

public void deleteEmployee(Long id) {
    employeeRepository.deleteById(id);
}


}
