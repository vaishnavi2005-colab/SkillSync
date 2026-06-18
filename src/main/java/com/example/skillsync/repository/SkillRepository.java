package com.example.skillsync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.skillsync.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}