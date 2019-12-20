package com.epam.petproject.repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface GenericRepository<T, L extends Number> {

    Collection<T> getElementCollection();

    <T> boolean deleteElement(T element);

    <T> boolean createElement(T element);

    <T> T updateElement(T element, T value);


    default <T> boolean duplicateChecker(List<T> tlist) {
        Set<T> dupes = new HashSet<>();
        for (T i : tlist) {
            if (!dupes.add(i)) {
                return true;
            }
        }
        return false;
    }

}
