package com.epam.petproject.controller;

import com.epam.petproject.model.Developer;
import com.epam.petproject.model.Skill;
import com.epam.petproject.repository.JavaIODeveloperRepositoryImpl;

import java.util.List;
import java.util.Set;

public class DeveloperController {
    private JavaIODeveloperRepositoryImpl developerRepository = new JavaIODeveloperRepositoryImpl();

    public List<Developer> getElementCollection() {
        return developerRepository.getAll();
    }

    public Developer getById(Long id) {
        return developerRepository.getById(id);
    }

    public Developer updateElement(Developer developer) {
        return developerRepository.update(developer);
    }

    public Developer save(Developer developer) {
        return developerRepository.save(developer);
    }

    public void deleteById(Long id) {
        developerRepository.deleteById(id);
    }

    public Developer parseDeveloper(String str){
        return developerRepository.parseFromString(str);
    }

    public Set<Skill> parseSkills(String str){
        return developerRepository.parseSkills(str);
    }
}
