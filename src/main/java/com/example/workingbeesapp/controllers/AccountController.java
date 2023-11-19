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

    //--- get all accounts ---//
    @GetMapping("")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accountDtoList = accountService.getAllAccounts();
        return ResponseEntity.ok(accountDtoList);
    }

    //--- get account ---//
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        AccountDto accountDto = accountService.getOneAccount(id);
        return ResponseEntity.ok(accountDto);
    }

    //--- create new account ---//
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

    //--- delete account ---//
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
