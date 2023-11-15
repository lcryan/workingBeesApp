package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.AccountDto;
import com.example.workingbeesapp.dtos.UserDto;
import com.example.workingbeesapp.exceptions.RecordNotFoundException;
import com.example.workingbeesapp.models.Account;
import com.example.workingbeesapp.models.Role;
import com.example.workingbeesapp.models.User;
import com.example.workingbeesapp.repositories.AccountRepository;
import com.example.workingbeesapp.repositories.RoleRepository;
import com.example.workingbeesapp.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final AccountRepository accountRepository;


    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, AccountRepository accountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
    }

    // GET ALL USERS //
    public List<UserDto> getAllUsers() {

        List<User> userList = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : userList) {
            UserDto userDto = new UserDto();
            userToUserDto(user, userDto);

            userDtos.add(userDto);
        }
        return userDtos;
    }

    public UserDto getUser(String username) {
        UserDto userDto;
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            userDto = new UserDto();
            userToUserDto(user.get(), userDto);
        } else {
            throw new RecordNotFoundException("User with username: " + username + " not found");
        }
        return userDto;
    }

    public void deleteUser(String username) {
        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(username);
        } else {
            throw new RecordNotFoundException("User with username: " + username + " not found");
        }
    }
    // ------------------------------------------------------ //

    public UserDto createUserWithAccount(AccountDto accountDto) {

        UserDto userDto = new UserDto();
        userDto.setUsername(accountDto.getUsername());
        userDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));

        User user = new User();
        if (accountDto.getRoleList() != null) {
            List<Role> userRoles = new ArrayList<>();
            for (String roleName : accountDto.getRoleList()) {
                Optional<Role> optionalRole = roleRepository.findById("ROLE_" + roleName);
                optionalRole.ifPresent(userRoles::add);
            }
            userDtoToUser(user, userDto);
            user.setRoleList(userRoles);
        }

        Account account = new Account();
        accountDtoToAccount(accountDto, account);

        accountRepository.save(account);
        userRepository.save(user);

        user.setAccount(account);

        userRepository.save(user);
        accountRepository.save(account);

        UserDto savedUserDto = new UserDto();
        userToUserDto(user, savedUserDto);

        return savedUserDto;
    }

    // HELPER METHODS USER TO USER DTO AND USER DTO TO USER //
    private static void userToUserDto(User user, UserDto userDto) {
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());

        ArrayList<String> roleList = new ArrayList<>();
        for (Role role : user.getRoleList()) {
            roleList.add(role.getRoleName());
        }
        userDto.setRoleList(roleList.toArray(new String[0]));
    }

    private static void userDtoToUser(User user, UserDto userDto) {
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
    }

    // Transfer method AccountDto to Account //
    private void accountDtoToAccount(AccountDto accountDto, Account account) {
        account.setCompanyName(accountDto.getCompanyName());
        account.setUsername(accountDto.getUsername());
        account.setPassword(accountDto.getPassword());
        account.setRoleList(account.getRoleList());
    }
}
