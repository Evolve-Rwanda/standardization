package com.example.springauth.services;

import com.example.springauth.repositories.UserRepository;
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
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<AppUser> appUser = userRepository.findByUsername(username);
        if(appUser.isPresent()){
            var userObj = appUser.get();
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
    public AppUser createUser(AppUser user){
        return userRepository.save(user);
    }
}
