package com.example.springauth.controllers;


import com.example.springauth.models.app.AdminModel;
import com.example.springauth.models.jpa.AppUser;
import com.example.springauth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class AdminRegistration {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @GetMapping("/admin_registration")
    public String adminRegistration(Model model) {
        return "admin_registration";
    }

    @PostMapping("/admin_registration")
    public String adminRegistration(@ModelAttribute("adminRegistrationForm")AdminModel adminModel, Model model) {
        String username = adminModel.getUsername();
        String password = passwordEncoder.encode(adminModel.getPassword());
        AppUser appUser = new AppUser(username, password);
        userService.createUser(appUser);
        model.addAttribute("adminRegistrationForm", new AdminModel());
        model.addAttribute("successfulRegistration", "Admin registration successful");
        return "admin_registration";
    }

}
