package com.epam.petproject.service;

import java.sql.SQLException;
import java.util.List;

public interface GenericService<T, ID> {
    T getbyID(ID id);

    List<T> getAll();

    T save(T t);

    void delete(ID id);

    T update(T t) throws SQLException;
}
