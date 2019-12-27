package com.epam.petproject.repository;

import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.List;

public interface  DeveloperRepository<T, ID> extends GenericRepository<T,ID>{
    @Override
    List<T> getAll();
    @Override
    T update(T developer);
    @Override
    void deleteById(ID id);
    @Override
    T save(T developer);
    @Override
    T getById(ID id);
}
