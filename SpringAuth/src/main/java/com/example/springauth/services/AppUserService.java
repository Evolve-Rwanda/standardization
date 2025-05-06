package com.example.springauth.services;

import com.example.springauth.models.jpa.AppUser;
import com.example.springauth.repositories.AppUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AppUserService implements UserDetailsService {


    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);
        if(appUser.isPresent()){
            var userObj = appUser.get();
            return User
                    .builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles("APP_ADMIN")
                    .build();
        }else{
            throw new UsernameNotFoundException(username);
        }
    }

    @Transactional
    public AppUser save(AppUser appUser){
        return appUserRepository.save(appUser);
    }
}
