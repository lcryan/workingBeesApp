package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.AccountDto;
import com.example.workingbeesapp.models.Account;
import com.example.workingbeesapp.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountService accountService;

    @Test
    void getOneAccount() {
        Long accountId = 1L;
        Account mockAccount = new Account();
        mockAccount.setId(accountId);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));

        AccountDto result = accountService.getOneAccount(accountId);

        assertNotNull(result);
        assertEquals(accountId, result.getId());
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void getAllAccounts() {
    }

    @Test
    void createAccount() {
    }

    @Test
    void deleteAccount() {
    }
}