package com.epam.petproject.repository;

import com.epam.petproject.model.Skill;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface AccountRepository<T,ID> extends GenericRepository<T,ID> {

    @Override
    List<T> getAll();
    @Override
    T update(T account);
    @Override
    void deleteById(ID id);
    @Override
    T save(T account);
    @Override
    T getById(ID id);
}
