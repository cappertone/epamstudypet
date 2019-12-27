package com.epam.petproject.controller;

import com.epam.petproject.model.Skill;
import com.epam.petproject.repository.JavaIOSkillRepositoryImpl;

import java.util.List;

public class SkillController {
    private JavaIOSkillRepositoryImpl repoImpl = new JavaIOSkillRepositoryImpl();

    public List<Skill> getElementCollection() {
        return repoImpl.getAll();
    }

    public Skill getById (Long id){
        return repoImpl.getById(id);
    }

    public Skill updateElement(Skill skill) {
        return repoImpl.update(skill);
    }

    public Skill save(Skill skill) {
        return repoImpl.save(skill);
    }

    public void deleteById(Long id) {
        repoImpl.deleteById(id);
    }

    public Skill parseSkill(String str){
        return repoImpl.parseFromString(str);
    }
}
