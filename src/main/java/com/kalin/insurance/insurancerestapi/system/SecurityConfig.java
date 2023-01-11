package com.kalin.insurance.insurancerestapi.system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the api.
 */
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/clients/**").hasAnyRole("AGENT", "ADMIN")
                .antMatchers("/cars/**").hasAnyRole("AGENT", "ADMIN")
                .antMatchers("/covers/**").hasAnyRole("AGENT", "ADMIN")
                .antMatchers("/policies/**").hasAnyRole("AGENT", "ADMIN")
                .antMatchers(HttpMethod.POST, "/addresses/types/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/addresses/types/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/addresses/types/**").hasRole("ADMIN")
                .antMatchers("/addresses/**").hasAnyRole("AGENT", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager users() {
        UserDetails agent = User
                .withUsername("agent")
                .password(passwordEncoder().encode("agent"))
                .roles("AGENT")
                .build();
        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(agent, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
