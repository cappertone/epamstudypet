package com.epam.petproject.repository;

import com.epam.petproject.model.Skill;

import java.util.Collection;

public interface AccountRepository {
    Collection<Skill> getElementCollection();

    <T> boolean deleteElement(T element);

    <T> boolean createElement(T element);

    <T> boolean updateElement(T element, T value);
}
