package com.epam.petproject.controller;

import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;
import com.epam.petproject.repository.JavaIOAccountRepositoryImpl;
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

public class AccountControllerTest {

    private AccountController accountController = new AccountController();
    private List<Account> accountList;
    private Account account = new Account(7567546L, AccountStatus.INACTIVE);

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException, NoSuchFieldException, IllegalAccessException {
        File createdFile = folder.newFile("accountsTmp.txt");
        Path tempPath = Paths.get(createdFile.getPath());
        JavaIOAccountRepositoryImpl accountRepository = new JavaIOAccountRepositoryImpl(tempPath);

        Field field = accountController.getClass().getDeclaredField("accountImpl");
        field.setAccessible(true);
        field.set(accountController, accountRepository);

        accountList = new LinkedList<>();
        accountList.add(new Account(3425234523445L, AccountStatus.ACTIVE ));
        accountList.add(new Account(1231234534545L, AccountStatus.INACTIVE));
        accountList.add(new Account(2345234545344L, AccountStatus.BANNED));
        List<String> accountsStrings = accountList.stream()
                .map(Account::toString)
                .collect(Collectors.toList());
        Files.write(tempPath, accountsStrings, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }


    @Test
    public void getElementCollection() {
        assertEquals(accountList, accountController.getElementCollection());
    }

    @Test
    public void getById() {
        assertEquals(accountList.get(0),accountController.getById(3425234523445L));
    }

    @Test
    public void updateElement() {
        Account account = new Account(3425234523445L, AccountStatus.INACTIVE);
        accountController.updateElement(account);
        assertEquals(account,accountController.getById(3425234523445L));

    }

    @Test
    public void save() {
        Account responseAccount = accountController.save(account);
        assertEquals(account, responseAccount);
    }

    @Test
    public void deleteById() {
        accountController.deleteById(3425234523445L);
        assertEquals(accountList.subList(1,3), accountController.getElementCollection());
    }
}