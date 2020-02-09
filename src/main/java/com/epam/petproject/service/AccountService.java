package com.epam.petproject.service;

import com.epam.petproject.model.Account;
import com.epam.petproject.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
public class AccountService implements GenericService <Account, Long> {
    AccountRepository<Account, Long> repository;

    public AccountService(AccountRepository<Account, Long> repository) {
        this.repository = repository;
    }

    @Override
    public Account getbyID(Long aLong) {
        return repository.getById(aLong);
    }

    @Override
    public List<Account> getAll() {
        return repository.getAll();
    }

    @Override
    public Account save(Account account) {
        return repository.save(account);
    }

    @Override
    public void delete(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
    public Account update(Account account) {
        return repository.update(account);
    }
}
