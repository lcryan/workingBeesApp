package com.example.workingbeesapp.security;

import com.example.workingbeesapp.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
    public ProviderManager AuthenticationManager(BeeUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(userDetailsService);
        return new ProviderManager(auth);
    }

    @Bean
    public BeeUserDetailsService userDetailsService() {
        return new BeeUserDetailsService(this.userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth

//                                .requestMatchers("/*").hasRole("ADMIN")
//                                .requestMatchers("/**").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users/{userId}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/companies").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/companies/{accountId}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/companies").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/companies/{companyId}").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/workingSpaces").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/workingSpaces/{workingSpaceId}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/workingSpaces").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/workingSpaces/{workingSpaceId}").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/roles").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/subscriptions").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/subscriptions").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE, "/subscriptions/{subscriptionId}").hasAnyRole("ADMIN", "USER")

                                .requestMatchers(HttpMethod.POST, "/extraservices").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/extraservices").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE, "/extraservices/{extraServiceId}").hasRole("ADMIN")

                                //TODO: not sure if this is the right way to do it //
                                .requestMatchers(HttpMethod.POST, "/auth").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/single/fileUpload").hasRole("ADMIN") // single upload
                                .requestMatchers(HttpMethod.GET, "/download/{fileName}").hasRole("ADMIN") // single download

//                                .requestMatchers(HttpMethod.POST, "/multiple/uploadDB").hasRole("ADMIN") // multiple upload
//                                .requestMatchers(HttpMethod.GET, "/downloadAllFromDB").hasRole("ADMIN") // multiple download

//                                .requestMatchers(HttpMethod.POST, "/users").permitAll()
//                                .requestMatchers(HttpMethod.POST, "/auth").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/*").permitAll()
//                                .requestMatchers(HttpMethod.DELETE, "/*").permitAll()

//                                .requestMatchers("/secret").hasRole("ADMIN")
//                                .requestMatchers("/hello").authenticated()
//                        .anyRequest().denyAll()


                                .requestMatchers("/*").permitAll()
                                .requestMatchers("/**").permitAll()
//                                .anyRequest().denyAll()
                                .anyRequest().permitAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class); // This will be solved once the JWT service is set up, hopefully //

        return http.build();
    }
}

