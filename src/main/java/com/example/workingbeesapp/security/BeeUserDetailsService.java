package com.example.workingbeesapp.security;

import com.example.workingbeesapp.models.User;
import com.example.workingbeesapp.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class BeeUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public BeeUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new BeeUserDetails(user);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
