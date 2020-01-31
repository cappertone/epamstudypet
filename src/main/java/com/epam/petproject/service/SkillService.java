package com.epam.petproject.service;

import com.epam.petproject.model.Skill;
import com.epam.petproject.repository.SkillRepository;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
@Slf4j
public class SkillService implements GenericService <Skill, Long> {
    private SkillRepository<Skill, Long> repository;

    public SkillService(SkillRepository<Skill, Long> repository) {
        this.repository = repository;
    }

    @Override
    public Skill getbyID(Long aLong) {
        return repository.getById(aLong);
    }

    @Override
    public List<Skill> getAll() {
        return repository.getAll();
    }

    @Override
    public Skill save(Skill skill) {
        return repository.save(skill);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
    public Skill update(Skill skill){
        return repository.update(skill);
    }
}
