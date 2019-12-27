package com.epam.petproject.controller;


import com.epam.petproject.model.Account;
import com.epam.petproject.repository.JavaIOAccountRepositoryImpl;

import java.util.List;

public class AccountController {
    private JavaIOAccountRepositoryImpl accountImpl = new JavaIOAccountRepositoryImpl();

    public List<Account> getElementCollection() {
        return accountImpl.getAll();
    }

    public Account getById(Long id) {
        return accountImpl.getById(id);
    }

    public Account updateElement(Account account) {
        return accountImpl.update(account);
    }

    public Account save(Account account) {
        return accountImpl.save(account);
    }

    public void deleteById(Long id) {
        accountImpl.deleteById(id);
    }

    public Account parseAcoount(String str){
        return accountImpl.parseFromString(str);
    }
}
