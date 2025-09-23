package com.app.accountservice.controller;

import com.app.accountservice.dtos.AccountDTO;
import com.app.accountservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public AccountDTO createAccount(@RequestBody AccountDTO accountDTO) {
        return accountService.createAccount(accountDTO);
    }

    @GetMapping

    public List<AccountDTO> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("account/{accountId}")
    public AccountDTO getAccount(@PathVariable String accountId) {
        return accountService.getAccountById(accountId);
    }

    @PostMapping("/{accountId}/deposit")
    public AccountDTO deposit(@PathVariable String accountId, @RequestBody double amount) {
        return accountService.deposit(accountId,amount);
    }

    @PostMapping("/{accountId}/withdraw")
    public AccountDTO withdraw(@PathVariable String accountId,@RequestBody double amount) {
        return accountService.withdraw(accountId,amount);
    }

    @PutMapping("/{accountId}")
    public AccountDTO updateAccount(@PathVariable String accountId, @RequestBody AccountDTO accountDTO) {
        return accountService.updateAccount(accountId,accountDTO);
    }


    @DeleteMapping("/{accountId}")
    public void deleteAccount(@PathVariable String accountId) {
        accountService.deleteAccount(accountId);
    }
}
