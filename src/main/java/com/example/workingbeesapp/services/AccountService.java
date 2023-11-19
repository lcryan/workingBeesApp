package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.AccountDto;
import com.example.workingbeesapp.dtos.AccountUserDto;
import com.example.workingbeesapp.exceptions.IdNotFoundException;
import com.example.workingbeesapp.exceptions.RecordNotFoundException;
import com.example.workingbeesapp.models.Account;
import com.example.workingbeesapp.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;

    }

    //--- get account ---//
    public AccountDto getOneAccount(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            AccountDto accountDto = new AccountDto();
            transferAccountToAccountDto(account, accountDto);
            return (accountDto);
        } else {
            throw new IdNotFoundException("Item of type Account with id: " + id + " could not be found.");
        }
    }

    //--- get all accounts ---//
    public List<AccountDto> getAllAccounts() {

        List<Account> accountList = accountRepository.findAll();
        List<AccountDto> accountDtoList = new ArrayList<>();

        for (Account account : accountList) {
            AccountDto accountDto = new AccountDto();
            transferAccountToAccountDto(account, accountDto);

            accountDtoList.add(accountDto);
        }
        return accountDtoList;
    }

    //---create account ---//
    public AccountDto createAccount(AccountUserDto accountUserDto) {

        Account account = new Account();
        AccountUserDtoToAccount(accountUserDto, account);

        Account storedAccount = accountRepository.save(account);

        AccountDto storedAccountDto = new AccountDto();
        transferAccountToAccountDto(storedAccount, storedAccountDto);

        return storedAccountDto;
    }


    //--- delete account ---//
    public void deleteAccount(Long id) {
        if (accountRepository.existsById(id)) {
            Optional<Account> optionalAccount = accountRepository.findById(id);
            Account obsoleteAccount = optionalAccount.get();

            accountRepository.delete(obsoleteAccount);
        } else {
            throw new RecordNotFoundException("Item of type Account with id: " + id + " could not be found.");
        }
    }

    //--- transfer account user dto to account ---//
    private void AccountUserDtoToAccount(AccountUserDto accountUserDto, Account account) {
        /*       account.setId(accountDto.getId());*/
        account.setFirstName(accountUserDto.getFirstName());
        account.setLastName(accountUserDto.getLastName());
        account.setEmail(accountUserDto.getEmail());
        account.setCompanyName(accountUserDto.getCompanyName());
    }

    //--- transfer account to account dto---//
    private static void transferAccountToAccountDto(Account account, AccountDto accountDto) {
        accountDto.setId(account.getId());
        accountDto.setFirstName(account.getFirstName());
        accountDto.setLastName(account.getLastName());
        accountDto.setEmail(account.getEmail());
        accountDto.setCompanyName(account.getCompanyName());
    }
}
