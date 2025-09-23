package com.app.accountservice.service;

import com.app.accountservice.dtos.AccountDTO;
import com.app.accountservice.entities.Account;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);

    List<AccountDTO> getAllAccounts();

    AccountDTO getAccountById(String accountId);

    AccountDTO deposit(String accountId, double amount);

    AccountDTO withdraw(String accountId, double amount);

    AccountDTO updateAccount(String accountId, AccountDTO accountDTO);

    void deleteAccount(String accountId);
}
