package com.example.skillsync.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TaskRequestDTO {

    private String title;

    private String description;

    private Long requiredSkillId;

    private String complexity;

    private LocalDateTime deadline;
}
