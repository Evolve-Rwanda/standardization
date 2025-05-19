package com.example.springauth.services;

import com.example.springauth.repositories.AppUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import com.example.springauth.models.jpa.AppUser;


@Service
public class AppUserService implements UserDetailsService {


    @Autowired
    private AppUserRepository appUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<AppUser> appSetupUser = appUserRepository.findByUsername(username);
        if(appSetupUser.isPresent()){
            System.out.println("The found user is an admin.");
            var userObj = appSetupUser.get();
            return User
                       .builder()
                       .username(userObj.getUsername())
                       .password(userObj.getPassword())
                       .roles(userObj.getRole())
                       .build();
        }else{
            throw new UsernameNotFoundException(username);
        }
    }


    @Transactional
    public AppUser createUser(AppUser appUser){
        return appUserRepository.save(appUser);
    }
}
