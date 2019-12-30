package com.epam.petproject.controller;

import com.epam.petproject.model.Skill;
import com.epam.petproject.repository.JavaIOSkillRepositoryImpl;

import java.util.List;

public class SkillController {
    private JavaIOSkillRepositoryImpl repoImpl = new JavaIOSkillRepositoryImpl();

    public List<Skill> getElementCollection() {
        return repoImpl.getAll();
    }

    public Skill getById(String id) {
        Skill skill = repoImpl.getById(getIDfromInput(id));
        if (getIDfromInput(id) != 0L) {
            return skill;
        }
        System.out.println("skill not found");
        return null;
    }


    public Skill updateElement(String id, String value) {
        try {
            if (getIDfromInput(id) != 0L && !value.equals("")) {
                return repoImpl.update(new Skill(getIDfromInput(id), value));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        System.out.println("invalid id or empty name");
        return null;
    }

    public Skill save(String input) {
        return repoImpl.save(new Skill(null, input));
    }

    public void deleteById(String input) {
        repoImpl.deleteById(getIDfromInput(input));
    }

    private Long getIDfromInput(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("wrong id value");
            return 0L;
        }
    }

}
