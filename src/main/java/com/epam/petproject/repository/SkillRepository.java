package com.epam.petproject.repository;

import java.util.List;

public interface SkillRepository extends GenericRepository<String, Long> {

    List<String> getElementCollection();

    <T> boolean deleteElement(T element);

    <T> boolean createElement(T element);

    <T> T updateElement(T element, T value);



}
