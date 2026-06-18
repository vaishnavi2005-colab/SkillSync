package com.example.skillsync.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.skillsync.model.Employee;
import com.example.skillsync.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {


private final EmployeeService employeeService;

public EmployeeController(
        EmployeeService employeeService) {

    this.employeeService = employeeService;
}

@PostMapping
public Employee addEmployee(
        @RequestBody Employee employee) {

    return employeeService.saveEmployee(employee);
}

@GetMapping
public List<Employee> getAllEmployees() {

    return employeeService.getAllEmployees();
}

@GetMapping("/{id}")
public Employee getEmployee(
        @PathVariable Long id) {

    return employeeService.getEmployeeById(id);
}

@PutMapping("/{id}")
public Employee updateEmployee(
        @PathVariable Long id,
        @RequestBody Employee employee) {

    return employeeService.updateEmployee(
            id,
            employee);
}

@DeleteMapping("/{id}")
public String deleteEmployee(
        @PathVariable Long id) {

    employeeService.deleteEmployee(id);

    return "Employee Deleted Successfully";
}

}
