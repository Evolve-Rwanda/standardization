package com.example.springauth.services;

import com.example.springauth.repositories.AppSetupUserRepository;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<AppSetupUser> appSetupUser = appSetupUserRepository.findByUsername(username);
        if(appSetupUser.isPresent()){
            var userObj = appSetupUser.get();
            return User
                       .builder()
                       .username(userObj.getUsername())
                       .password(userObj.getPassword())
                       .roles("ADMIN")
                       .build();
        }else{
            throw new UsernameNotFoundException(username);
        }
    }

    @Transactional
    public AppSetupUser createUser(AppSetupUser appSetupUser){
        return appSetupUserRepository.save(appSetupUser);
    }
}
