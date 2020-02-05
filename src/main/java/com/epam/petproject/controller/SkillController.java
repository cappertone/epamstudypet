package com.epam.petproject.controller;

import com.epam.petproject.model.Skill;
import com.epam.petproject.service.SkillService;

import java.util.List;

public class SkillController {
    private SkillService service;

    public SkillController(SkillService service) {
        this.service = service;
    }

    public List<Skill> getElementCollection() {
        return service.getAll();
    }

    public Skill getById(String id) {
        Skill skill = service.getbyID(getIDfromInput(id));
        if (getIDfromInput(id) != 0L) {
            return skill;
        }
        System.out.println("skill not found");
        return null;
    }

    public Skill updateElement(String id, String value) {
        try {
            if (getIDfromInput(id) != 0L && !value.equals("")) {
                return service.update(new Skill(getIDfromInput(id), value));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        System.out.println("invalid id or empty name");
        return null;
    }

    public Skill save(String input) {
        return service.save(new Skill(null, input));
    }

    public void deleteById(String input) {
        service.delete(getIDfromInput(input));
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
