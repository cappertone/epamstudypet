package com.epam.petproject.repository;

import com.epam.petproject.controller.AccountController;
import com.epam.petproject.controller.SkillController;
import com.epam.petproject.model.Account;
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
    private final String DEVELOPERSFILEPATH = "src/main/resources/files/developers.txt";
    private Path path = Paths.get(DEVELOPERSFILEPATH);
    private AccountController accountController = new AccountController();

    public JavaIODeveloperRepositoryImpl() {
    }
    public JavaIODeveloperRepositoryImpl(Path path) {
        this.path = path;
    }


    private Developer parseFromString(String str) {
        return new Developer(parseId(str), parseName(str), parseSkills(str), parseAccount(str));
    }

    private String parseName(String str) {
        return str.substring(str.indexOf("name='") + 6, str.indexOf("\',"));
    }

    private Long parseId(String str) {
        return Long.parseLong(str.substring(str.indexOf("id=") + 3, str.indexOf(",")));
    }

    private Set<Skill> parseSkills(String str) {
        try {
            SkillController skillController = new SkillController();
            String skills = str.substring(str.indexOf("[")+1, str.indexOf("]"));
            Set<String> items = new HashSet<>(Arrays.asList(skills.trim().replace(" ","").split(",")));
            Set<Skill> skillSet = new HashSet<>();
            for (String l : items) {
                skillSet.add(skillController.getById(l));
            }
            return skillSet;
        } catch (Exception e) {
            return null;
        }
    }

    private Account parseAccount(String str) {
        try {
            Long id = Long.parseLong(str.substring(str.indexOf("account=") + 8));
            return accountController.getById(id);
        } catch (Exception e) {
            return null;
        }
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
        if (null == developer.getId()) {
            developer.setId(generateID());
        }
        try {
            Files.write(path, (developerToFile(developer)).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return developer;
    }

    private String developerToFile(Developer developer) {
        String devToString = developer.toString();
        if (null != developer.getSkills()) {
            Set<Long> skillsIds = developer.getSkills().stream()
                    .map(Skill::getSkillId).collect(Collectors.toSet());
            String skills = devToString.substring(devToString.indexOf("["), devToString.indexOf("]") + 1);
            return devToString.replace(skills, skillsIds.toString()) + '\n';
        } else if(null!=developer.getAccount()){
            String account = devToString.substring(devToString.indexOf("account"), devToString.lastIndexOf("}")-1);
            String accountdev = "accountID="+developer.getAccount().getAccountId();
            return devToString.replace(account, accountdev);
        }
        return developer.toString();
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
        deleteById(old.getId());
        try {
            Files.write(path, developerToFile(developer).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return old;
    }
}
