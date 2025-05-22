package com.example.springauth.authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;


public class AuthenticationUtility {


    public static String getCurrentUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails){
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null;
    }

    public static boolean doPasswordsMatch(String password){
        UserDetails userDetails = getCurrentUserDetails();
        if (userDetails != null){
            return userDetails.getPassword().equals(password);
        }
        return false;
    }

    public static String getUserRole(){
        UserDetails userDetails = getCurrentUserDetails();
        if (userDetails != null){
            return userDetails.getAuthorities().iterator().next().getAuthority();
        }
        return null;
    }

    public static UserDetails getCurrentUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails){
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }


}
