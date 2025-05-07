package com.example.springauth.services;

import com.example.springauth.models.jpa.AppUser;
import com.example.springauth.repositories.AppSetupUserRepository;
import com.example.springauth.repositories.AppUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import com.example.springauth.models.jpa.AppSetupUser;


@Service
public class AppSetupUserService implements UserDetailsService {


    @Autowired
    private AppSetupUserRepository appSetupUserRepository;

    @Autowired
    private AppUserRepository appUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<AppSetupUser> appSetupUser = appSetupUserRepository.findByUsername(username);
        if(appSetupUser.isPresent()){
            System.out.println("The found user is an admin.");
            var userObj = appSetupUser.get();
            return User
                       .builder()
                       .username(userObj.getUsername())
                       .password(userObj.getPassword())
                       .roles("ADMIN")
                       .build();
        }else{
            System.out.println("Couldn't find the user - " + appSetupUser + " - " + appSetupUser.toString());
            Optional<AppUser> appUser = appUserRepository.findByUsername(username);
            if(appUser.isPresent()){
                System.out.println("The found user is an a regular user.");
                var userObj = appUser.get();
                return User
                        .builder()
                        .username(userObj.getUsername())
                        .password(userObj.getPassword())
                        .roles("APP_ADMIN")
                        .build();
            }
            System.out.println("Couldn't find the user - " + appUser + " - " + appUser.toString());
            throw new UsernameNotFoundException(username);
        }
    }


    @Transactional
    public AppSetupUser createUser(AppSetupUser appSetupUser){
        return appSetupUserRepository.save(appSetupUser);
    }
}
