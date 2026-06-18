package com.example.skillsync.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.skillsync.model.Skill;
import com.example.skillsync.repository.SkillRepository;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill saveSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skill getSkillById(Long id) {
        return skillRepository.findById(id).orElse(null);
    }

    public Skill updateSkill(Long id, Skill updatedSkill) {

        Skill skill = skillRepository.findById(id).orElse(null);

        if (skill == null) {
            return null;
        }

        skill.setSkillName(updatedSkill.getSkillName());

        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }
}