package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.UserDto;
import com.example.workingbeesapp.exceptions.IdNotFoundException;
import com.example.workingbeesapp.models.Role;
import com.example.workingbeesapp.models.User;
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


    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }

    public UserDto getUser(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User user1 = user.get();
            UserDto userDto = new UserDto();
            userToUserDto(user1, userDto);
            return (userDto);
        } else {
            throw new IdNotFoundException("User not found with ID: " + userId);
        }
    }


    public List<UserDto> getAllUsers() {

        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : userList) {
            UserDto userDto = new UserDto();
            userToUserDto(user, userDto);

            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    public String createUser(UserDto userDto) {
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
    }


    private static void userToUserDto(User user, UserDto userDto) {
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());

        ArrayList<String> roleList = new ArrayList<>();
        for (Role role : user.getRoleList()) {
            roleList.add(role.getRoleName());
        }
        userDto.setRoleList(roleList.toArray(new String[0]));
    }

    private static void userDtoToUser(User user, UserDto userDto) {
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());

    }
}

// TODO : check why null in postman on lastname, firstname, email when sending GET request for user //
