package com.epam.petproject.model;

import lombok.Data;

@Data
public class Account {
   private Long accountId;
   private AccountStatus status;

    public Account(Long accountId, AccountStatus status) {
        this.accountId = accountId;
        this.status = status;
    }

    public Long getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", status=" + status +
                '}' + '\n';
    }
}
