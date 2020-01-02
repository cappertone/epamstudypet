package com.epam.petproject.controller;

import com.epam.petproject.model.Developer;
import com.epam.petproject.repository.JavaIODeveloperRepositoryImpl;
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

public class DeveloperControllerTest {

    private DeveloperController developerController = new DeveloperController();
    private List<Developer> devList;

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException, NoSuchFieldException, IllegalAccessException {
        File createdFile = folder.newFile("developersTmp.txt");
        Path tempPath = Paths.get(createdFile.getPath());
        JavaIODeveloperRepositoryImpl developerRepository = new JavaIODeveloperRepositoryImpl(tempPath);

        Field field = developerController.getClass().getDeclaredField("developerRepository");
        field.setAccessible(true);
        field.set(developerController, developerRepository);

        devList = new LinkedList<>();
        devList.add(new Developer(66787878978L, "Petya", null, null));
        devList.add(new Developer(78789789789L, "Ostap", null, null));
        devList.add(new Developer(56456754675L, "Bill", null, null));
        List<String> devStrings = devList.stream()
                .map(Developer::toString)
                .collect(Collectors.toList());
        Files.write(tempPath, devStrings, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }


    @Test
    public void getElementCollection() {
        assertEquals(devList.toString(), developerController.getElementCollection().toString());
    }

    @Test
    public void getById() {
        assertEquals(devList.get(1).toString(), developerController.getById("78789789789").toString());
    }

    @Test
    public void updateElement() {

        developerController.updateElement(66787878978L, "ADDSKILL", "JavaScript");
        assertNotEquals(devList.get(0).getSkills(), developerController.getById("66787878978").getSkills());

    }

    @Test
    public void save() {
        Developer responseDeveloper = developerController.save("Rob");
        assertNotNull(responseDeveloper);
    }

    @Test
    public void deleteById() {
        developerController.deleteById(66787878978L);
        assertEquals(devList.subList(1, 3).toString(), developerController.getElementCollection().toString());
    }
}