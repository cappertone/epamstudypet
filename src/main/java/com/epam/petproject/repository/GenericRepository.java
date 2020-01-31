package com.epam.petproject.repository;

import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;


public interface GenericRepository<T, ID> {

    Collection<T> getAll() throws ClassNotFoundException, SQLException;

    T getById(ID id);

    T save(T element);

    T update(T element) throws SQLException;

    void deleteById(ID id);

    default Long generateID() {
        return ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
    }
}
