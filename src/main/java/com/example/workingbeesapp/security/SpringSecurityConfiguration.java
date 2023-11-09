package com.example.workingbeesapp.security;

import com.example.workingbeesapp.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SpringSecurityConfiguration(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(userDetailsService);
        return new ProviderManager(auth);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // defining the authorization rules below per entity //


                        // for authentication //
                        .requestMatchers(HttpMethod.POST, "/authentication").permitAll()

                        // for users //
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/users/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")

                        // for roles //
                        .requestMatchers(HttpMethod.GET, "/roles").hasRole("ADMIN")

                        // for accounts //
                        .requestMatchers(HttpMethod.POST, "/accounts").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/accounts").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/accounts/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/accounts/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/accounts/{id}").hasRole("ADMIN")

                        // for companies //
                        .requestMatchers(HttpMethod.POST, "/companies").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/companies").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/companies/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/companies/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/companies/{id}").hasRole("ADMIN")

                        // for working spaces //
                        .requestMatchers(HttpMethod.POST, "/workingSpaces").hasRole("ADMIN") // only admin assigns working spaces //
                        .requestMatchers(HttpMethod.GET, "/workingSpaces").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/workingSpaces/{workingSpaceId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/workingSpaces/{workingSpaceId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/workingSpaces/{workingSpaceId}").hasRole("ADMIN")

                        // for subscriptions //
                        .requestMatchers(HttpMethod.POST, "/subscriptions").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/subscriptions").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/subscriptions/{subscriptionId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/subscriptions/{subscriptionId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/subscriptions/{subscriptionId}").hasRole("ADMIN")

                        // for extra services //
                        .requestMatchers(HttpMethod.POST, "/extraservices").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/extraservices").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/extraservices/{extraServiceId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/extraservices/{extraServiceId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/extraservices/{extraServiceId}").hasRole("ADMIN")

                        // for file upload and download //
                        .requestMatchers(HttpMethod.POST, "/single/fileUpload").hasRole("ADMIN") // single upload
                        .requestMatchers(HttpMethod.GET, "/download/{fileName}").hasAnyRole("ADMIN", "USER") // single download

                        // for teams //
                        .requestMatchers(HttpMethod.POST, "/teams").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/teams").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/teams/{teamId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/teams/{teamId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/teams/{teamId}").hasAnyRole("ADMIN", "USER")

                        .anyRequest().permitAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class); // This will be solved once the JWT service is set up, hopefully //

        return http.build();
    }
}

