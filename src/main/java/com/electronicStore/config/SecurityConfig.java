package com.electronicStore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        // this User class is from spring security
        UserDetails normalUser = User.builder()
                .username("india")
                .password(passwordEncoder().encode("newdelhi"))
                .roles("NORMAL")
                .build();

        UserDetails adminUser = User.builder()
                .username("japan")
                .password(passwordEncoder().encode("tokyo"))
                .roles("ADMIN")
                .build();

//        InMemoryUserDetailsManager --> implementing class of UserDetailsService

        return new InMemoryUserDetailsManager(normalUser,adminUser);

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
