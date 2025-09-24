package com.app.accountservice.service;
import com.app.accountservice.clients.CustomerRestClient;
import com.app.accountservice.dtos.AccountDTO;
import com.app.accountservice.entities.Account;
import com.app.accountservice.mapper.AccountMapper;
import com.app.accountservice.model.Customer;
import com.app.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRestClient customerRestClient;
    private final AccountMapper accountMapper;


    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        if (accountDTO.getCustomerId() == null || accountDTO.getCustomerId().describeConstable().isEmpty()) {
            throw new IllegalArgumentException("CustomerId ne peut pas être null ou vide");
        }

        Account account = accountMapper.toEntity(accountDTO);
        account.setAccountId(UUID.randomUUID().toString());
        account.setCreatedAt(LocalDate.now());
        account.setCustomerId(accountDTO.getCustomerId());
        System.out.println(accountDTO.getCustomerId());

        Account savedAccount = accountRepository.save(account);

        AccountDTO dto = accountMapper.toDTO(savedAccount);

        try {
            Customer customer = customerRestClient.getCustomerById(savedAccount.getCustomerId());
            dto.setCustomer(customer);
        } catch (Exception e) {
            log.warn("Impossible de récupérer le customer avec id {}: {}", savedAccount.getCustomerId(), e.getMessage());
            dto.setCustomer(null);
        }

        return dto;
    }


    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream().map(account -> {
            AccountDTO dto = accountMapper.toDTO(account);
            dto.setCustomer(customerRestClient.getCustomerById(account.getCustomerId()));
            return dto;
        }).toList();
    }
    @Override
    public AccountDTO getAccountById(String accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        AccountDTO dto = accountMapper.toDTO(account);
        dto.setCustomer(customerRestClient.getCustomerById(account.getCustomerId()));

        return dto;
    }

    @Override
    public AccountDTO deposit(String accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        account.setBalance(account.getBalance() + amount);
        Account updatedAccount = accountRepository.save(account);

        AccountDTO dto = accountMapper.toDTO(updatedAccount);
        dto.setCustomer(customerRestClient.getCustomerById(updatedAccount.getCustomerId()));

        return dto;
    }


    @Override
    public AccountDTO withdraw(String accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be positive");
        }

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        Account updatedAccount = accountRepository.save(account);

        AccountDTO dto = accountMapper.toDTO(updatedAccount);
        dto.setCustomer(customerRestClient.getCustomerById(updatedAccount.getCustomerId()));

        return dto;
    }


    @Override
    public AccountDTO updateAccount(String accountId, AccountDTO accountDTO) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(accountDTO.getBalance());
        account.setCurrency(accountDTO.getCurrency());
        account.setType(accountDTO.getType());
        account.setCustomerId(accountDTO.getCustomerId());

        Account updatedAccount = accountRepository.save(account);

        AccountDTO dto = accountMapper.toDTO(updatedAccount);
        dto.setCustomer(customerRestClient.getCustomerById(updatedAccount.getCustomerId()));

        return dto;
    }

    @Override
    public void deleteAccount(String accountId) {
        accountRepository.deleteById(accountId);
    }
}

