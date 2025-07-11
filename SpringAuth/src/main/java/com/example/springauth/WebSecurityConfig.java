package com.example.springauth;

import com.example.springauth.authentication.CustomAuthenticationSuccessHandler;
import com.example.springauth.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Autowired
    private AppUserService appUserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected UserDetailsService appUserDetailsService() {
        return appUserService;
    }


    @Bean
    protected AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        // find a way to disable credentials erasure
        //provider.setEraseCredentialsAfterAuthentication(false); // cannot resolve methods
        return provider;
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((request) -> {
                    // Allow access to the index page
                    request.requestMatchers("/","/index","/login", "/admin_registration", "/upload").permitAll();
                    // Always allow access to the css, javascript, and images folder
                    request.requestMatchers("/css/**","/javascript/**","/images/**").permitAll();
                    // Require authentication for all other pages
                    request.anyRequest().authenticated();
                })
                .formLogin((form) -> form
                        .loginPage("/login")
                        //.defaultSuccessUrl("/home") // works for a default non-role based authentication success redirect
                        .successHandler(customAuthenticationSuccessHandler())
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());
        return http.build();
    }


}