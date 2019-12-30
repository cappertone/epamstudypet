package com.epam.petproject.controller;

import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;
import com.epam.petproject.model.Developer;
import com.epam.petproject.model.Skill;
import com.epam.petproject.repository.JavaIODeveloperRepositoryImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DeveloperController {
    private JavaIODeveloperRepositoryImpl developerRepository = new JavaIODeveloperRepositoryImpl();
    private AccountController accountController = new AccountController();
    private SkillController skillController = new SkillController();

    public List<Developer> getElementCollection() {
        return developerRepository.getAll();
    }

    public Developer getById(String id) {
        try {
            return developerRepository.getById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Developer updateElement(Long id, String choice, String value) {
        choice = choice.toUpperCase().replace(" ","");
        Developer developer = developerRepository.getById(id);
        Set <Skill> skills = new HashSet<>();


        switch (choice) {
            case "ADDSKILL": {
                Skill newSkill = skillController.save(value);
                skills.add(newSkill);
                if (null != developer.getSkills()) {
                    skills.addAll(developer.getSkills());
                }
                return developerRepository.update(new Developer(id, developer.getName(), skills, developer.getAccount()));
            }
            case "REMOVESKILL":{
                    if (null != developer.getSkills()) {
                        skills = developer.getSkills().stream()
                                .filter(skill -> !skill.getSkillId().equals(getIDfromInput(value)))
                                .collect(Collectors.toSet());
                        System.out.println(skills);
                    }
                    return developerRepository.update(new Developer(id, developer.getName(), skills, developer.getAccount()));
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
                return developerRepository.update(developer);
            }
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }
    }

    public Developer save(String name) {
        return developerRepository.save(new Developer(null, name, null, null));
    }

    public void deleteById(Long id) {
        developerRepository.deleteById(id);
    }

    public Long getIDfromInput(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}
