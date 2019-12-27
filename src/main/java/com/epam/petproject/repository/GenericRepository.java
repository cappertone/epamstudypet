package com.epam.petproject.repository;

import com.epam.petproject.model.Skill;

import java.util.Collection;
import java.util.List;


public interface GenericRepository<T, ID> {

    Collection<T> getAll();
    T getById(ID id);
    T save(T element);
    T update(T element);
    void deleteById(ID id);


}
