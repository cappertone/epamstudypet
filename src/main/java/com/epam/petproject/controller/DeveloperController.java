package com.epam.petproject.controller;

import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;
import com.epam.petproject.model.Developer;
import com.epam.petproject.model.Skill;
import com.epam.petproject.repository.DeveloperRepository;
import com.epam.petproject.service.DeveloperService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DeveloperController {
    private DeveloperService service;
    private AccountController accountController;
    private SkillController skillController;

    public DeveloperController(DeveloperService service, AccountController accountController, SkillController skillController) {
        this.service = service;
        this.accountController = accountController;
        this.skillController = skillController;
    }

    public DeveloperController(DeveloperService service) {
        this.service = service;
    }

    public List<Developer> getElementCollection() {
        return service.getAll();
    }

    public Developer getById(String id) {
        try {
            return service.getbyID(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Developer updateElement(Long id, String choice, String value) {
        choice = choice.toUpperCase().replace(" ","");
        Developer developer = service.getbyID(id);
        Set <Skill> skills = new HashSet<>();
        switch (choice) {
            case "ADDSKILL": {
                Skill newSkill = skillController.save(value);
                skills.add(newSkill);
                if (null != developer.getSkills()) {
                    skills.addAll(developer.getSkills());
                }
                return service.update(new Developer(id, developer.getName(), skills, developer.getAccount()));
            }
            case "REMOVESKILL":{
                    if (null != developer.getSkills()) {
                        skills = developer.getSkills().stream()
                                .filter(skill -> !skill.getSkillID().equals(getIDfromInput(value)))
                                .collect(Collectors.toSet());
                        System.out.println(skills);
                    }
                    return service.update(new Developer(id, developer.getName(), skills, developer.getAccount()));
            }
            case "ACCOUNT": {
                Account account = developer.getAccount();
                if (null != account) {
                    account.setStatus(AccountStatus.valueOf(value));
                    account = accountController.updateElement(account);
                } else {
                    account = accountController.save(new Account(null, AccountStatus.valueOf(value)));
                }
                developer = new Developer(id, developer.getName(), developer.getSkills(), account);
                return service.update(developer);
            }
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }
    }

    public Developer save(String name) {
        return service.save(new Developer(null, name, null, null));
    }

    public void deleteById(Long id) {
        service.delete(id);
    }

    public Long getIDfromInput(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}
