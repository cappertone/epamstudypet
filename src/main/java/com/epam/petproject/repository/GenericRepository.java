package com.epam.petproject.repository;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;


public interface GenericRepository<T, ID> {

    Collection<T> getAll();

    T getById(ID id);

    T save(T element);

    T update(T element);

    void deleteById(ID id);

    default Long generateID() {
        return ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
    }
}
