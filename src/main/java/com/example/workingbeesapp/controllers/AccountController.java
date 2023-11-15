package com.example.workingbeesapp.controllers;

import com.example.workingbeesapp.dtos.AccountDto;
import com.example.workingbeesapp.dtos.AccountUserDto;
import com.example.workingbeesapp.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Get Account List //
    @GetMapping("")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accountDtoList = accountService.getAllAccounts();
        return ResponseEntity.ok(accountDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        AccountDto accountDto = accountService.getOneAccount(id);
        return ResponseEntity.ok(accountDto);
    }

    @PostMapping("")
    public ResponseEntity<Object> createNewAccount(@Validated @RequestBody AccountUserDto accountUserDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                sb.append(fe.getField());
                sb.append(" : ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            AccountDto accountDto = accountService.createAccount(accountUserDto);
            return ResponseEntity.created(null).body(accountDto);
        }
    }

/*    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @Validated @RequestBody AccountDto newAccount) {
        AccountDto accountDto1 = accountService.updateAccount(id, newAccount);
        return ResponseEntity.ok().body(accountDto1);
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
