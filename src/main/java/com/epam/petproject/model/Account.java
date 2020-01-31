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

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", status=" + status +
                '}' + '\n';
    }
}
