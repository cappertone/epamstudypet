package com.epam.petproject.repository;

import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;
import com.epam.petproject.model.Skill;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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

public class JavaIOAccountRepositoryImplTest {

    JavaIOAccountRepositoryImpl accountRepository = new JavaIOAccountRepositoryImpl();
    private List<Account> accountList;
    private Path tempPath;
    private Account account = new Account(342345235L, AccountStatus.INACTIVE);

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();


    @Before
    public void setUp() throws IOException, NoSuchFieldException, IllegalAccessException {
        File createdFile = folder.newFile("accountsTmp.txt");
        tempPath = Paths.get(createdFile.getPath());
        Field field = accountRepository.getClass().getDeclaredField("path");
        field.setAccessible(true);
        field.set(accountRepository, tempPath);


        accountList = new LinkedList<>();
        accountList.add(new Account(3425234523445L, AccountStatus.ACTIVE ));
        accountList.add(new Account(1231234534545L, AccountStatus.INACTIVE));
        accountList.add(new Account(2345234545344L, AccountStatus.BANNED));
    }

    @Test
    public void save() throws IOException {
        accountRepository.save(account);
        String line;
        Account accountFromFile = null;
        BufferedReader bufferedReader = Files.newBufferedReader(tempPath);
        while ((line = bufferedReader.readLine()) != null) {
            accountFromFile = accountRepository.parseFromString(line);
        }
        assertEquals(account, accountFromFile);
    }

    @Test
    public void getAll() {
        accountList.forEach(account -> accountRepository.save(account));
        List<Account> resultList = accountRepository.getAll();
        assertEquals(accountList, resultList);
    }

    @Test
    public void getById() {
        accountList.forEach(account -> accountRepository.save(account));

        Account resultSkill = accountRepository.getById(1231234534545L);
        assertEquals(accountList.get(1), resultSkill);
    }

    @Test
    public void update() {
        accountList.forEach(account -> accountRepository.save(account));
        Account newAccount = new Account(2345234545344L, AccountStatus.ACTIVE);
        accountRepository.update(newAccount);
        Account resultAccount = accountRepository.getById(2345234545344L);
        assertEquals(newAccount.getAccountId(), resultAccount.getAccountId());
    }

    @Test
    public void deleteById() {
        Account accountToDelete = accountList.get(1);
        accountList.forEach(account -> accountRepository.save(account));
        accountRepository.deleteById(accountToDelete.getAccountId());
        accountList.remove(1);
        assertEquals(accountList,accountRepository.getAll());
    }

    @After
    public void finalize() {
        accountList.clear();
        folder.delete();
    }


}