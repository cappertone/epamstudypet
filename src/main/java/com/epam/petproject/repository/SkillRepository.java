package com.epam.petproject.repository;




import java.sql.SQLException;
import java.util.List;

public interface SkillRepository<T, ID> extends GenericRepository<T,ID> {

    @Override
    List<T> getAll();
    @Override
    T update(T skill);
    @Override
    void deleteById(ID id);
    @Override
    T save(T skill);
    @Override
    T getById(ID id);
}
