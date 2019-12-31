package com.epam.petproject.repository;

import com.epam.petproject.model.Developer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class JavaIODeveloperRepositoryImplTest {
    private JavaIODeveloperRepositoryImpl devRepository = new JavaIODeveloperRepositoryImpl();
    private List<Developer> devList;
    private Developer developer = new Developer(342345L, "Vaso", null, null);


    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();


    @Before
    public void setUp() throws IOException, NoSuchFieldException, IllegalAccessException {
        File createdFile = folder.newFile("developersTmp.txt");
        Path tempPath = Paths.get(createdFile.getPath());
        Field field = devRepository.getClass().getDeclaredField("path");
        field.setAccessible(true);
        field.set(devRepository, tempPath);


        devList = new LinkedList<>();
        devList.add(new Developer(66787878978L, "Petya", null, null));
        devList.add(new Developer(78789789789L, "Ostap", null, null));
        devList.add(new Developer(56456754675L, "Bill", null, null));
    }

    @Test
    public void save(){
        devRepository.save(developer);
        Developer devFromFile = devRepository.getById(developer.getId());
        assertEquals(developer.toString(), devFromFile.toString());
    }

    @Test
    public void getAll() {
        devList.forEach(skill -> devRepository.save(skill));
        List<Developer> resultList = devRepository.getAll();
        assertEquals(devList.toString(), resultList.toString());
    }

    @Test
    public void getById() {
        devRepository.save(developer);
        Developer resultDev = devRepository.getById(342345L);
        assertEquals(developer.toString(), resultDev.toString());
    }

    @Test
    public void update() {
        devRepository.save(developer);
        Developer newDev = new Developer(developer.getId(), "Bob", null, null);
        devRepository.update(newDev);
        Developer resultSkill = devRepository.getById(342345L);
        assertEquals(newDev.getName(), resultSkill.getName());
    }

    @Test
    public void deleteById() {
        Developer devToDelete = new Developer(78789789789L, "Ostap", null, null);
        devList.forEach(skill -> devRepository.save(skill));
        devRepository.deleteById(devToDelete.getId());
        devList.remove(1);
        assertEquals(devList.toString(), devRepository.getAll().toString());
    }

    @After
    public void finalize() {
        devList.clear();
        folder.delete();
    }
}