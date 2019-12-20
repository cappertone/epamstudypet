package com.epam.petproject.controller;

import com.epam.petproject.repository.JavaNIOSkillRepositoryImpl;

import java.util.List;

public class SkilController implements GenericController {
    private JavaNIOSkillRepositoryImpl repoImpl = new JavaNIOSkillRepositoryImpl();

    @Override
    public List<String> getElementCollection() {
        return repoImpl.getElementCollection();
    }

    @Override
    public Object updateElement(Object element, Object value) {
        return repoImpl.updateElement(element, value);
    }

    @Override
    public boolean createElement(Object element) {
        return repoImpl.createElement(element);
    }

    @Override
    public boolean deleteElement(Object element) {
        return repoImpl.deleteElement(element);
    }
}
