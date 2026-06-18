package com.example.skillsync.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee_skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({
            "hibernateLazyInitializer",
            "handler"
    })
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties({
            "hibernateLazyInitializer",
            "handler"
    })
    private Skill skill;

    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;
}