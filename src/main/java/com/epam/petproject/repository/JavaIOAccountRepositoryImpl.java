package com.epam.petproject.repository;

import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class JavaIOAccountRepositoryImpl implements AccountRepository<Account, Long> {
    private static final String ACCOUNTFILEPATH = "src/main/resources/files/accounts.txt";
    private static final Path path = Paths.get(ACCOUNTFILEPATH);

    private Long generateID() {
        return new Random().nextLong();
    }

    public Account parseFromString(String str) {
        return new Account(parseId(str), parseStatus(str));
    }

    private Long parseId(String str) {
        return Long.parseLong(str.substring(str.indexOf("=") + 1, str.indexOf(",")));
    }

    private AccountStatus parseStatus(String str) {
        return AccountStatus.valueOf(str.substring(str.indexOf("status=") + 7, (str.lastIndexOf("}"))).toUpperCase());
    }

    @Override
    public Account save(Account account) {
        if (null == account.getAccountId()) {
            account.setAccountId(generateID());
        }
        try {
            Files.write(path, account.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public List<Account> getAll() {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            List<Account> result = new LinkedList<>();
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
    public Account getById(Long id) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (id.equals(parseId(line))) {
                    return parseFromString(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account update(Account account) {
        Account old = getById(account.getAccountId());
        deleteById(account.getAccountId());
        try {
            Files.write(path, account.toString().getBytes(), StandardOpenOption.APPEND);
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
}
