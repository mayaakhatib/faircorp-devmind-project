package com.emse.spring.faircorp;

//import io.swagger.models.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebMvc
public class SpringSecurityConfig {

    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";

    @Bean
    public UserDetailsService userDetailsService() {
        // We create a password encoder
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withUsername("admin").password(encoder.encode("passwordA")).roles(ROLE_ADMIN).build()
        );
        manager.createUser(
                User.withUsername("user").password(encoder.encode("passwordU")).roles(ROLE_USER).build()
        );
        return manager;
    }

    @Bean
    @Order(1) // (1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return  http
                .antMatcher("/api/**") // (2)
                .authorizeRequests(authorize -> authorize.anyRequest().hasRole("ADMIN")) // (3)
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .csrf().disable()
                .build();
    }

    @Bean
    @Order(2) // (1)
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        return  http
                .authorizeRequests(authorize -> authorize.anyRequest().authenticated()) // (1)
                .formLogin(withDefaults()) // (2)
                .httpBasic(withDefaults()) // (3)
                .build();
    }






}

