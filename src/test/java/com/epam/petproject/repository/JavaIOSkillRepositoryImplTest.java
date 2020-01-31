package com.epam.petproject.repository;

import com.epam.petproject.model.Skill;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class JavaIOSkillRepositoryImplTest {
    JavaIOSkillRepositoryImpl skillRepository = new JavaIOSkillRepositoryImpl();
    private List<Skill> skillList;
    private Path tempPath;
    private Skill skill = new Skill(342345L, "Hadoop");


    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();


    @Before
    public void setUp() throws IOException, NoSuchFieldException, IllegalAccessException {
        File createdFile = folder.newFile("skillsTmp.txt");
        tempPath = Paths.get(createdFile.getPath());
        Field field = skillRepository.getClass().getDeclaredField("path");
        field.setAccessible(true);
        field.set(skillRepository, tempPath);


        skillList = new LinkedList<>();
        skillList.add(new Skill(3425234523445L, "Java"));
        skillList.add(new Skill(1231234534545L, "Junit"));
        skillList.add(new Skill(2345234545344L, "SQL"));
    }

    @Test
    public void save() throws IOException{
        skillRepository.save(skill);
        String line;
        Skill skillFromFile = null;
        BufferedReader bufferedReader = Files.newBufferedReader(tempPath);
        while ((line = bufferedReader.readLine()) != null) {
            skillFromFile = skillRepository.parseFromString(line);
        }
        assertEquals(skill, skillFromFile);
    }

    @Test
    public void getAll() {

        skillList.forEach(skill -> skillRepository.save(skill));
        List<Skill> resultList = skillRepository.getAll();
        assertEquals(skillList, resultList);
    }

    @Test
    public void getById() {
        skillRepository.save(skill);
        Skill resultSkill = skillRepository.getById(342345L);
        assertEquals(skill, resultSkill);
    }

    @Test
    public void update() {
        skillRepository.save(skill);
        Skill newSkill = new Skill(skill.getSkillID(), "ApacheSpark");
        skillRepository.update(newSkill);
        Skill resultSkill = skillRepository.getById(342345L);
        assertEquals(newSkill.getName(), resultSkill.getName());
    }

    @Test
    public void deleteById() {
        Skill skillToDelete = new Skill(2345234545344L, "SQL");
        skillList.forEach(skill -> skillRepository.save(skill));
        skillRepository.deleteById(skillToDelete.getSkillID());
        skillList.remove(2);
        assertEquals(skillList, skillRepository.getAll());
    }

    @After
    public void finalize() {
        skillList.clear();
        folder.delete();
    }
}