package com.example.skillsync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.skillsync.model.EmployeeSkill;
import com.example.skillsync.model.Skill;

public interface EmployeeSkillRepository
        extends JpaRepository<EmployeeSkill, Long> {

    List<EmployeeSkill> findBySkill(Skill skill);
}