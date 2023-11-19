package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.AccountUserDto;
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

    //--- get all users ---//
    public List<UserDto> getAllUsers() {

        List<User> userList = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : userList) {
            UserDto userDto = new UserDto();
            transferUserToUserDto(user, userDto);

            userDtos.add(userDto);
        }
        return userDtos;
    }

    //--- getUser ---//
    public UserDto getUser(String username) {
        UserDto userDto;
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            userDto = new UserDto();
            transferUserToUserDto(user.get(), userDto);
        } else {
            throw new RecordNotFoundException("User with username: " + username + " not found");
        }
        return userDto;
    }

    //--- delete user ---//
    public void deleteUser(String username) {
        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(username);
        } else {
            throw new RecordNotFoundException("User with username: " + username + " not found");
        }
    }

    //--- create user with account ---//
    public UserDto createUserWithAccount(AccountUserDto accountUserDto) {

        UserDto userDto = new UserDto();
        userDto.setUsername(accountUserDto.getUsername());
        userDto.setPassword(passwordEncoder.encode(accountUserDto.getPassword()));

        User user = new User();
        if (accountUserDto.getRoleList() != null) {
            List<Role> userRoles = new ArrayList<>();
            for (String roleName : accountUserDto.getRoleList()) {
                Optional<Role> optionalRole = roleRepository.findById("ROLE_" + roleName);
                optionalRole.ifPresent(userRoles::add);
            }
            transferUserDtoToUser(user, userDto);
            user.setRoleList(userRoles);
        }

        Account account = new Account();
        transferAccountDtoToAccount(accountUserDto, account);

        account.setUser(user);
        user.setAccount(account);

        userRepository.save(user);
        accountRepository.save(account);

        UserDto storedUserDto = new UserDto();
        transferUserToUserDto(user, storedUserDto);

        return storedUserDto;
    }

    //--- helper methods for user to user dto ---//
    private static void transferUserToUserDto(User user, UserDto userDto) {
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());

        ArrayList<String> roleList = new ArrayList<>();
        for (Role role : user.getRoleList()) {
            roleList.add(role.getRoleName());
        }
        userDto.setRoleList(roleList.toArray(new String[0]));
    }

    //--- helper methods for user dto to user ---//
    private static void transferUserDtoToUser(User user, UserDto userDto) {
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
    }

    //--- transfer method for account dto to account dto - passing through account user dto ---//
    private void transferAccountDtoToAccount(AccountUserDto accountUserDto, Account account) {
        account.setFirstName(accountUserDto.getFirstName());
        account.setLastName(accountUserDto.getLastName());
        account.setEmail(accountUserDto.getEmail());
        account.setCompanyName(accountUserDto.getCompanyName());
    }
}
