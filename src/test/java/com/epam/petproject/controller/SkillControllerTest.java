package com.epam.petproject.controller;

import com.epam.petproject.model.Skill;
import com.epam.petproject.repository.JavaIOSkillRepositoryImpl;
import com.epam.petproject.repository.SkillRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class SkillControllerTest {
    private SkillController skillController = new SkillController();
    private JavaIOSkillRepositoryImpl skillRepository = new JavaIOSkillRepositoryImpl();

    private List<String> valueList;
    private List<Skill>skillList;
    private Skill skill = new Skill(342345L, "Hadoop");


    @Before
    public void setUp() throws IOException, NoSuchFieldException, IllegalAccessException {

        valueList = new LinkedList<>();
        valueList.add("Java");
        valueList.add("Junit");
        valueList.add("SQL");
    }


    @Test
    public void getElementCollection() {
       assertNotNull(skillController.getElementCollection());
    }

    @Test
    public void getById() {
    }

    @Test
    public void updateElement() {
    }

    @Test
    public void save() {
    Skill responseSkill = skillController.save(skill.getSkillName());
    assertEquals(skill.getSkillName(), responseSkill.getSkillName());
    }

    @Test
    public void deleteById() {
    }
}