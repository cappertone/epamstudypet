package com.epam.petproject.controller;


import com.epam.petproject.model.Account;
import com.epam.petproject.service.AccountService;

import java.util.List;

public class AccountController {
    private AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }


    public List<Account> getElementCollection() {
        return service.getAll();
    }

    public Account getById(Long id) {
        return service.getbyID(id);
    }

    public Account updateElement(Account account) {
        return service.update(account);
    }

    public Account save(Account account) {
        return service.save(account);
    }

    public void deleteById(Long id) {
        service.delete(id);
    }

}
