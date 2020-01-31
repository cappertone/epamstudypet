package com.epam.petproject.service;

import com.epam.petproject.model.Developer;
import com.epam.petproject.repository.DeveloperRepository;
import com.epam.petproject.repository.jdbc.JDBCDeveloperRepository;

import java.util.List;

public class DeveloperService implements GenericService<Developer, Long> {
    private DeveloperRepository<Developer,Long> developerRepository;

    public DeveloperService(DeveloperRepository<Developer, Long> developerRepository) {
        this.developerRepository = developerRepository;
    }

    @Override
    public Developer getbyID(Long aLong) {
        return developerRepository.getById(aLong);
    }

    @Override
    public List <Developer> getAll() {
        return developerRepository.getAll();
    }

    @Override
    public Developer save(Developer developer) {
        return developerRepository.save(developer);
    }

    @Override
    public void delete(Long aLong) {
        developerRepository.deleteById(aLong);
    }

    @Override
    public Developer update(Developer developer) {
        return developerRepository.update(developer);
    }


}
