package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.CompanyDto;
import com.example.workingbeesapp.dtos.UserDto;
import com.example.workingbeesapp.models.Company;
import com.example.workingbeesapp.models.Role;
import com.example.workingbeesapp.models.User;
import com.example.workingbeesapp.repositories.CompanyRepository;
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

    private final CompanyRepository companyRepository;


    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, CompanyRepository companyRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
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

    // ------------------------------------------------------ //

/*    public String createUser(UserDto userDto) {
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

        List<Role> userRoles = new ArrayList<>();
        for (String roleName : userDto.getRoleList()) {
            Optional<Role> optionalRole = roleRepository.findById("ROLE_" + roleName);
            optionalRole.ifPresent(userRoles::add);
        }
        userRepository.save(newUser);

        return "User successfully created";
    }*/

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

    // ------------------------------------------------------ //

    // Create USER with Company and Role //
    public UserDto createUserWithCompany(CompanyDto companyDto) {

        // Create a new user for company //

        UserDto userDto = new UserDto();
        userDto.setUsername(companyDto.getUsername());
        userDto.setPassword(passwordEncoder.encode(companyDto.getPassword()));

        User user = new User();
        if (companyDto.getRoleList() != null) {
            List<Role> userRoles = new ArrayList<>();
            for (String roleName : companyDto.getRoleList()) {
                Optional<Role> optionalRole = roleRepository.findById("ROLE_" + roleName);
                optionalRole.ifPresent(userRoles::add);
            }
            // making a new User //
            userDtoToUser(user, userDto);
            user.setRoleList(userRoles);
        }

        // Create a new company-user //
        Company company = new Company();
        companyDtoToCompany(companyDto, company);
        // save both in repo //
        companyRepository.save(company);
        userRepository.save(user);

        UserDto savedUserDto = new UserDto();
        userToUserDto(user, savedUserDto);

        return savedUserDto;
    }

    // make CompanyDto to Company //

    private void companyDtoToCompany(CompanyDto companyDto, Company company) {
        company.setCompanyName(companyDto.getCompanyName());
        company.setUsername(companyDto.getUsername());
        company.setPassword(companyDto.getPassword());
        company.setRoleList(List.of(companyDto.getRoleList()));
    }
}


