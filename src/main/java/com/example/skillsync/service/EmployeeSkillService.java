package com.example.skillsync.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.skillsync.model.EmployeeSkill;
import com.example.skillsync.repository.EmployeeSkillRepository;

@Service
public class EmployeeSkillService {

    private final EmployeeSkillRepository repository;

    public EmployeeSkillService(EmployeeSkillRepository repository) {
        this.repository = repository;
    }

    public EmployeeSkill save(EmployeeSkill employeeSkill) {
        return repository.save(employeeSkill);
    }

    public List<EmployeeSkill> getAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}