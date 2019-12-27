package com.epam.petproject.repository;

import com.epam.petproject.controller.SkillController;
import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;
import com.epam.petproject.model.Developer;
import com.epam.petproject.model.Skill;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class JavaIODeveloperRepositoryImpl implements DeveloperRepository<Developer, Long> {
    private static final String DEVELOPERSFILEPATH = "src/main/resources/files/developers.txt";
    private static final Path path = Paths.get(DEVELOPERSFILEPATH);

    private Long generateID() {
        return new Random().nextLong();
    }

    public Developer parseFromString(String str) {
        return new Developer(parseId(str), parseSkills(str), parseAccount(str));
    }

    private Long parseId(String str) {
        return Long.parseLong(str.substring(str.indexOf("id=") + 3, str.indexOf(",")));
    }

    public Set<Skill> parseSkills(String str) {
        SkillController skillController = new SkillController();
        String skills = str.substring(str.indexOf("skills=") + 8, str.indexOf("],"));
        Set<String> items = new HashSet<>(Arrays.asList(skills.split("}")));
        Set<Skill> skillSet = new HashSet<>();
        for (String s : items) {
            skillSet.add(skillController.parseSkill(s));
        }
        return skillSet;
    }

    private Account parseAccount(String str) {
        Long id = Long.parseLong(str.substring(str.indexOf("accountId=") + 10, str.indexOf(", status")));
        String status = str.substring(str.indexOf("status=") + 7, str.lastIndexOf("}"));
        return new Account(id, AccountStatus.valueOf(status));
    }

    @Override
    public List<Developer> getAll() {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            List<Developer> result = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                if (!line.equals("}")) {
                    stringBuilder.append(line);
                } else {
                    result.add(parseFromString(stringBuilder.toString()));
                    stringBuilder.delete(0, stringBuilder.length());
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Developer getById(Long id) {
        List<Developer> devList = getAll();
        for (Developer developer : devList) {
            if (developer.getId().equals(id)) {
                return developer;
            }
        }
        return null;
    }

    @Override
    public Developer save(Developer developer) {
        if(null==developer.getId() || null==developer.getAccount().getAccountId()){
            developer.setId(generateID());
            developer.getAccount().setAccountId(generateID());
        }
        try {
            Files.write(path, developer.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public void deleteById(Long id) {
        try {
            List<Developer> devList = getAll().stream()
                    .filter(developer -> !developer.getId().equals(id))
                    .collect(Collectors.toList());
            List<String> out = devList.stream()
                    .map(Developer::toString)
                    .collect(Collectors.toList());
            Files.write(path, out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Developer update(Developer developer) {
        Developer old = getById(developer.getId());
        deleteById(developer.getId());
        try {
            Files.write(path, developer.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return old;
    }
}
