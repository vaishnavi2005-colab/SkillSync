package com.example.skillsync.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.skillsync.dto.EmployeeSkillDTO;
import com.example.skillsync.model.Employee;
import com.example.skillsync.model.EmployeeSkill;
import com.example.skillsync.model.ExperienceLevel;
import com.example.skillsync.model.Skill;
import com.example.skillsync.repository.EmployeeRepository;
import com.example.skillsync.repository.SkillRepository;
import com.example.skillsync.service.EmployeeSkillService;

@RestController
@RequestMapping("/employee-skills")
public class EmployeeSkillController {


private final EmployeeSkillService service;
private final EmployeeRepository employeeRepository;
private final SkillRepository skillRepository;

public EmployeeSkillController(
        EmployeeSkillService service,
        EmployeeRepository employeeRepository,
        SkillRepository skillRepository) {

    this.service = service;
    this.employeeRepository = employeeRepository;
    this.skillRepository = skillRepository;
}

@PostMapping
public String assignSkill(
        @RequestBody EmployeeSkillDTO dto) {

    try {

        System.out.println("========== DTO RECEIVED ==========");
        System.out.println("Employee ID = " + dto.getEmployeeId());
        System.out.println("Skill ID = " + dto.getSkillId());
        System.out.println("Experience = " + dto.getExperienceLevel());

        Employee employee =
                employeeRepository.findById(dto.getEmployeeId())
                        .orElseThrow(() ->
                                new RuntimeException("Employee Not Found"));

        Skill skill =
                skillRepository.findById(dto.getSkillId())
                        .orElseThrow(() ->
                                new RuntimeException("Skill Not Found"));

        EmployeeSkill employeeSkill =
                new EmployeeSkill();

        employeeSkill.setEmployee(employee);
        employeeSkill.setSkill(skill);

        employeeSkill.setExperienceLevel(
                ExperienceLevel.valueOf(
                        dto.getExperienceLevel()));

        service.save(employeeSkill);

        return "Skill Assigned Successfully";

    } catch (Exception e) {

        e.printStackTrace();

        return "ERROR : " + e.getMessage();
    }
}

@GetMapping
public List<EmployeeSkill> getAllMappings() {
    return service.getAll();
}

@DeleteMapping("/{id}")
public String deleteMapping(
        @PathVariable Long id) {

    service.delete(id);

    return "Mapping Deleted Successfully";
}


}
