package com.example.skillsync.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSkillDTO {

    private Long employeeId;

    private Long skillId;

    private String experienceLevel;
}