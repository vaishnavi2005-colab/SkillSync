package com.example.skillsync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.skillsync.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}