package com.epam.petproject.model;

import java.util.Objects;

public class Account {
    Long accountId;
    AccountStatus status;

    public Account(Long accountId, AccountStatus status) {
        this.accountId = accountId;
        this.status = status;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", status=" + status +
                '}' + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(getAccountId(), account.getAccountId()) &&
                getStatus() == account.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountId(), getStatus());
    }
}
