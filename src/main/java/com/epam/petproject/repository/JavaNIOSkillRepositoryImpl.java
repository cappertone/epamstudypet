package com.epam.petproject.repository;

import com.epam.petproject.model.Skill;

import java.io.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class JavaNIOSkillRepositoryImpl implements SkillRepository {
    private static final String SKILLFILEPATH = "skills.txt";
    private static final Path path = Paths.get(SKILLFILEPATH);


    @Override
    public <T> boolean createElement(T skill) {
        try {
            Files.write(path, new Skill(skill.toString()).toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Function<String, Skill> mapStringToSkill = Skill::new;

    @Override
    public List<String> getElementCollection() {
        List<String> strSkills = new LinkedList<>();
        try (BufferedReader br = Files.newBufferedReader(path)) {
            strSkills = br.lines()
                    .filter(s -> s.length() > 0)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strSkills;
    }

    @Override
    public <T> boolean deleteElement(T value) {
        try {
            File file = new File(SKILLFILEPATH);
            List<String> out = Files.lines(path)
                    .filter(line -> !line.equals(value.toString()))
                    .collect(Collectors.toList());
            Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public <T> T updateElement(T element, T value) {
        try {
            Charset charset = StandardCharsets.UTF_8;
            Files.write(path, new String(Files.readAllBytes(path), charset)
                    .replace(element.toString(), value.toString()).getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return element;
    }
}