package com.epam.petproject.controller;

import com.epam.petproject.model.Skill;
import com.epam.petproject.repository.JavaIOSkillRepositoryImpl;
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

    private List<Skill> skillList;
    private Skill skill = new Skill(342345L, "Hadoop");

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException, NoSuchFieldException, IllegalAccessException {
        File createdFile = folder.newFile("skillsTmp.txt");
        Path tempPath = Paths.get(createdFile.getPath());
        JavaIOSkillRepositoryImpl skillRepository = new JavaIOSkillRepositoryImpl(tempPath);

        Field field = skillController.getClass().getDeclaredField("repoImpl");
        field.setAccessible(true);
        field.set(skillController, skillRepository);

        skillList = new LinkedList<>();
        skillList.add(new Skill(3425234523445L, "Java"));
        skillList.add(new Skill(1231234534545L, "Junit"));
        skillList.add(new Skill(2345234545344L, "SQL"));
        List<String> skillsStrings = skillList.stream()
                .map(Skill::toString)
                .collect(Collectors.toList());
        Files.write(tempPath, skillsStrings, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }


    @Test
    public void getElementCollection() {
        assertEquals(skillList, skillController.getElementCollection());
    }

    @Test
    public void getById() {
        assertEquals(skillList.get(0),skillController.getById("3425234523445"));
    }

    @Test
    public void updateElement() {
        Skill skill = new Skill(3425234523445L, "Java8");
        skillController.updateElement("3425234523445", "Java8");
        assertEquals(skill,skillController.getById("3425234523445"));

    }

    @Test
    public void save() {
        Skill responseSkill = skillController.save(skill.getSkillName());
        assertEquals(skill.getSkillName(), responseSkill.getSkillName());
    }

    @Test
    public void deleteById() {
        skillController.deleteById("3425234523445");
        assertEquals(skillList.subList(1,3), skillController.getElementCollection());
    }
}