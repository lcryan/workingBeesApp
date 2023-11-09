package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.AccountDto;
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


    // Get one account //

    public AccountDto getOneAccount(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            return transferAccountToAccountDto(optionalAccount.get());
        } else {
            throw new IdNotFoundException("Account with id: " + id + " could not be found.");
        }
    }

    // Get all accounts //
    public List<AccountDto> getAllAccounts() {

        List<Account> accountList = accountRepository.findAll();
        List<AccountDto> accountDtoList = new ArrayList<>();

        for (Account account : accountList) {
            AccountDto accountDto = transferAccountToAccountDto(account);
            accountDtoList.add(accountDto);
        }
        return accountDtoList;
    }

    // Create account //
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = new Account();
        Account newAccount = transferAccountDtoToAccount(accountDto);
        Account savedAccount = accountRepository.save(newAccount);

        return transferAccountToAccountDto(savedAccount);
    }

    // Update account //

    public AccountDto updateAccount(Long id, AccountDto accountDto) {

        if (accountRepository.findById(id).isPresent()) {

            Account account = accountRepository.findById(id).get();

            Account account1 = transferAccountDtoToAccount(accountDto);

            account1.setId(account.getId());

            accountRepository.save(account1);

            return transferAccountToAccountDto(account1);
        } else {
            throw new IdNotFoundException("Account with id: " + id + " could not be found.");
        }
    }

    // Delete account //
    public void deleteAccount(Long id) {
        if (accountRepository.existsById(id)) {
            Optional<Account> optionalAccount = accountRepository.findById(id);
            Account obsoleteAccount = optionalAccount.get();

            accountRepository.delete(obsoleteAccount);
        } else {
            throw new RecordNotFoundException("Item of type Account with id: " + id + " could not be found.");
        }
    }


    // transfer methods model to dto and back //

    public AccountDto transferAccountToAccountDto(Account account) {
        var accountDto = new AccountDto();

        accountDto.setId(account.getId());
        accountDto.setFirstName(account.getFirstName());
        accountDto.setLastName(account.getLastName());
        accountDto.setEmail(account.getEmail());
        accountDto.setCompanyName(account.getCompanyName());
        accountDto.setPassword(account.getPassword());
        accountDto.setUsername(account.getUsername());

        return accountDto;
    }

    public Account transferAccountDtoToAccount(AccountDto accountDto) {
        var account = new Account();

        account.setId(accountDto.getId());
        account.setFirstName(accountDto.getFirstName());
        account.setLastName(accountDto.getLastName());
        account.setEmail(accountDto.getEmail());
        account.setCompanyName(accountDto.getCompanyName());
        account.setPassword(accountDto.getPassword());
        account.setUsername(accountDto.getUsername());

        return account;
    }

}
