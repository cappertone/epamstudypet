package com.epam.petproject.repository;

import com.epam.petproject.model.Skill;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;


public class JavaIOSkillRepositoryImpl implements SkillRepository<Skill, Long> {
    private static final String SKILLFILEPATH = "src/main/resources/files/skills.txt";
    private static final Path path = Paths.get(SKILLFILEPATH);

    @Override
    public Skill save(Skill skill) {
        if (null == skill.getSkillId()) {
            skill.setSkillId(generateID());
        }
        try {
            Files.write(path, skill.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public List<Skill> getAll() {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            List<Skill> result = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                result.add(parseFromString(line));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Skill getById(Long id) {
        try {
            BufferedReader reader = Files.newBufferedReader(path);
            String line;
            while ((line = reader.readLine()) != null) {
                if (parseId(line).equals(id)) {
                    return parseFromString(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Skill update(Skill skill) {
        Skill old = getById(skill.getSkillId());
        deleteById(old.getSkillId());
        try {
            Files.write(path, skill.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return old;
    }

    @Override
    public void deleteById(Long id) {
        try {
            List<String> out = Files.lines(path)
                    .filter(line -> !line.contains(id.toString()))
                    .collect(Collectors.toList());
            Files.write(path, out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Skill parseFromString(String str) {
        return new Skill(parseId(str), parseName(str));
    }

    private Long parseId(String str) {
        return Long.parseLong(str.substring(str.indexOf("skillID=") + 8, str.lastIndexOf(",")));
    }

    private String parseName(String str) {
        return str.substring(str.indexOf("name=") + 6, str.length() - 1);
    }
}