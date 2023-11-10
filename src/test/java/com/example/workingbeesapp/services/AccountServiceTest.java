package com.example.workingbeesapp.services;
import com.example.workingbeesapp.dtos.AccountDto;
import com.example.workingbeesapp.exceptions.IdNotFoundException;
import com.example.workingbeesapp.exceptions.RecordNotFoundException;
import com.example.workingbeesapp.models.Account;
import com.example.workingbeesapp.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    @Test
    void getOneAccount() {
        Long id = 1L;
        AccountDto accountDto = new AccountDto();
        accountDto.setId(id);
        accountDto.setFirstName("Cobain");
        accountDto.setLastName("Kurt");
        accountDto.setEmail("kurt@aberdeen.com");
        accountDto.setUsername("Kurt");
        accountDto.setPassword("HeartShapedBox");
        accountDto.setRoleList(new String[]{"ROLE_USER"});

        when(accountRepository.findById(id)).thenReturn(java.util.Optional.of(accountService.transferAccountDtoToAccount(accountDto)));
        AccountDto result = accountService.getOneAccount(id);

        assertEquals(accountDto.getId(), result.getId());
        assertEquals(accountDto.getFirstName(), result.getFirstName());
        assertEquals(accountDto.getLastName(), result.getLastName());
        assertEquals(accountDto.getEmail(), result.getEmail());
        assertEquals(accountDto.getUsername(), result.getUsername());
        assertEquals(accountDto.getPassword(), result.getPassword());

    }

    @Test
    void getOneAccountNotFound() {
        Long id = 1L;
        when(accountRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(IdNotFoundException.class, () -> accountService.getOneAccount(id));
    }

    @Test
    void getAllAccounts() {

        Account accountOne = new Account();
        accountOne.setId(1L); // id is one here - specific to testing //
        accountOne.setFirstName("Courtney");
        accountOne.setLastName("Love");
        accountOne.setCompanyName("The Cobain Coop.");
        accountOne.setUsername("Courtney");
        accountOne.setPassword("DollParts");
        accountOne.setEmail("courtney@love.com");

        Account accountTwo = new Account();
        accountTwo.setId(2L); // id is two here - specific to testing //
        accountTwo.setFirstName("Frances");
        accountTwo.setLastName("Cobain");
        accountTwo.setCompanyName("Bean Brother Inc.");
        accountTwo.setUsername("Frances");
        accountTwo.setPassword("Bean");
        accountTwo.setEmail("francesbean@love.com");

        List<Account> testAccounts = accountService.transferAccountDtoListToAccountList(accountService.getAllAccounts());
        testAccounts.add(accountOne);
        testAccounts.add(accountTwo);

        when(accountRepository.findAll()).thenReturn(testAccounts);

        List<AccountDto> result = accountService.getAllAccounts();

        assertEquals(testAccounts.size(), result.size());
    }

    @Test
    void createAccount() {
        // Set up input
        AccountDto newAccountDto = new AccountDto();
        newAccountDto.setFirstName("Kurt");
        newAccountDto.setLastName("Cobain");
        newAccountDto.setCompanyName("Nirvana Coop.");
        newAccountDto.setUsername("Kurt");
        newAccountDto.setPassword("HeartShapedBox");
        newAccountDto.setEmail("kurt@aberdeen.com");

        // Set up expected output
        Account expectedAccount = new Account();
        expectedAccount.setFirstName("Kurt");
        expectedAccount.setLastName("Cobain");
        expectedAccount.setCompanyName("Nirvana Coop.");
        expectedAccount.setUsername("Kurt");
        expectedAccount.setPassword("HeartShapedBox");
        expectedAccount.setEmail("kurt@aberdeen.com");


        // Set up mock repository
        when(accountRepository.save(Mockito.any(Account.class))).thenReturn(expectedAccount);

        // Run the test
        AccountDto result = accountService.createAccount(newAccountDto);

        // Verify the results
        assertEquals(expectedAccount.getFirstName(), result.getFirstName());
        assertEquals(expectedAccount.getLastName(), result.getLastName());
        assertEquals(expectedAccount.getCompanyName(), result.getCompanyName());
        assertEquals(expectedAccount.getUsername(), result.getUsername());
        assertEquals(expectedAccount.getPassword(), result.getPassword());
        assertEquals(expectedAccount.getEmail(), result.getEmail());
    }


    @Test
    void updateAccount() {

        Long id = 1L;
        AccountDto accountDto = new AccountDto();

        accountDto.setId(id);
        accountDto.setFirstName("Kurt");
        accountDto.setLastName("Cobain");
        accountDto.setCompanyName("Nirvana Coop.");
        accountDto.setUsername("Kurt");
        accountDto.setPassword("HeartShapedBox");
        accountDto.setEmail("kurt@aberdeen.com");


        Account existingAccount = new Account();

        existingAccount.setId(id);
        existingAccount.setFirstName("Frances");
        existingAccount.setLastName("Bean Cobain");
        existingAccount.setCompanyName("Bean Brother Inc.");
        existingAccount.setUsername("Frances");
        existingAccount.setPassword("Bean");
        existingAccount.setEmail("frances@love.com");


        when(accountRepository.findById(id)).thenReturn(Optional.of(existingAccount));

        AccountDto updatedAccountDto = accountService.updateAccount(id, accountDto);

        verify(accountRepository, times(1)).findById(id);
        verify(accountRepository, times(1)).save(any(Account.class)); // testing if the save method is called on the repository //

        // Assert //
        assertNotNull(updatedAccountDto);
        assertEquals(id, updatedAccountDto.getId());
        assertEquals(accountDto.getFirstName(), updatedAccountDto.getFirstName());
        assertEquals(accountDto.getLastName(), updatedAccountDto.getLastName());
        assertEquals(accountDto.getCompanyName(), updatedAccountDto.getCompanyName());
        assertEquals(accountDto.getUsername(), updatedAccountDto.getUsername());
        assertEquals(accountDto.getPassword(), updatedAccountDto.getPassword());
        assertEquals(accountDto.getEmail(), updatedAccountDto.getEmail());
    }

    @Test
    void getUpdatedAccountNotFound() {

        Long id = 1L;
        when(accountRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> accountService.updateAccount(id, new AccountDto()));
    }

    @Test
    void deleteAccount() {
        Long id = 1L;
        Account existingAccount = new Account();
        existingAccount.setId(id);
        existingAccount.setFirstName("Kurt");
        existingAccount.setLastName("Cobain");
        existingAccount.setCompanyName("Nirvana Coop.");
        existingAccount.setUsername("Kurt");
        existingAccount.setPassword("HeartShapedBox");
        existingAccount.setEmail("kurt@aberdeen.com");

        when(accountRepository.existsById(id)).thenReturn(true);
        when(accountRepository.findById(id)).thenReturn(Optional.of(existingAccount));

        accountService.deleteAccount(id);

        Mockito.verify(accountRepository, times(1)).delete(existingAccount);

    }


    @Test
    void testDeleteAccountThrowsRecordNotFoundException() {
        // Arrange
        Long nonExistingAccountId = 2L;
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.existsById(nonExistingAccountId)).thenReturn(false);

        AccountService someAccount = new AccountService(accountRepository);

        // Act and Assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> someAccount.deleteAccount(nonExistingAccountId));
        assertEquals("Item of type Account with id: " + nonExistingAccountId + " could not be found.", exception.getMessage());

        // Verify that delete was not called
        verify(accountRepository, never()).delete(any());
    }

    @Test
    void transferAccountToAccountDto() {

        AccountDto accountDto = new AccountDto();
        accountDto.setId(1L);
        accountDto.setFirstName("Kurt");
        accountDto.setLastName("Cobain");
        accountDto.setCompanyName("Nirvana Coop.");
        accountDto.setUsername("Kurt");
        accountDto.setPassword("HeartShapedBox");
        accountDto.setEmail("kurt@aberdeen.com");

        Account account = accountService.transferAccountDtoToAccount(accountDto);

        assertEquals(accountDto.getId(), account.getId());
        assertEquals(accountDto.getFirstName(), account.getFirstName());
        assertEquals(accountDto.getLastName(), account.getLastName());
        assertEquals(accountDto.getCompanyName(), account.getCompanyName());
        assertEquals(accountDto.getUsername(), account.getUsername());
        assertEquals(accountDto.getPassword(), account.getPassword());
        assertEquals(accountDto.getEmail(), account.getEmail());
    }

    @Test
    void transferAccountDtoToAccount() {

        Account account = new Account();
        account.setId(1L);
        account.setFirstName("Kurt");
        account.setLastName("Cobain");
        account.setCompanyName("Nirvana Coop.");
        account.setUsername("Kurt");
        account.setPassword("HeartShapedBox");
        account.setEmail("kurt@aberdeen.com");

        AccountDto accountDto = accountService.transferAccountToAccountDto(account);

        assertEquals(account.getId(), accountDto.getId());
        assertEquals(account.getFirstName(), accountDto.getFirstName());
        assertEquals(account.getLastName(), accountDto.getLastName());
        assertEquals(account.getCompanyName(), accountDto.getCompanyName());
        assertEquals(account.getUsername(), accountDto.getUsername());
        assertEquals(account.getPassword(), accountDto.getPassword());
        assertEquals(account.getEmail(), accountDto.getEmail());

    }

    @Test
    void transferAccountDtoListToAccountList() {

        AccountDto accountDtoOne = new AccountDto();
        accountDtoOne.setId(1L);
        accountDtoOne.setFirstName("Kurt");
        accountDtoOne.setLastName("Cobain");
        accountDtoOne.setCompanyName("Nirvana Coop.");
        accountDtoOne.setUsername("Kurt");
        accountDtoOne.setPassword("HeartShapedBox");
        accountDtoOne.setEmail("kurt@aberdeen.com");

        AccountDto accountDtoTwo = new AccountDto();
        accountDtoTwo.setId(2L);
        accountDtoTwo.setFirstName("Courtney");
        accountDtoTwo.setLastName("Love");
        accountDtoTwo.setCompanyName("The Cobain Coop.");
        accountDtoTwo.setUsername("Courtney");
        accountDtoTwo.setPassword("DollParts");
        accountDtoTwo.setEmail("courtney@love.com");

        List<AccountDto> accountDtoList = List.of(accountDtoOne, accountDtoTwo);

        List<Account> accountList = accountService.transferAccountDtoListToAccountList(accountDtoList);

        assertEquals(accountDtoList.size(), accountList.size());

    }
}