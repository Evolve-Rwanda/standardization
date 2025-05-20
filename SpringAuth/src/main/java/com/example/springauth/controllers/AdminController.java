package com.example.springauth.controllers;

import com.example.springauth.models.app.AdminModel;
import com.example.springauth.models.jpa.AppUser;
import com.example.springauth.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class AdminController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppUserService appUserService;

    @GetMapping("/admin_registration")
    public String adminRegistration(Model model) {
        model.addAttribute("adminRegistrationForm", new AdminModel());
        return "admin_registration";
    }

    @PostMapping("/admin_registration")
    public String adminRegistration(
            @ModelAttribute("adminRegistrationForm")
            AdminModel adminModel,
            Model model
    ) {

        String firstName = adminModel.getFirstName();
        String lastName = adminModel.getLastName();
        String otherNames = adminModel.getOtherNames();
        String phoneNumber = adminModel.getPhoneNumber();
        String username = adminModel.getUsername();
        String formPassword = adminModel.getPassword();
        String confirmFormPassword = adminModel.getRepeatPassword();
        String role = "SUPER_ADMIN";
        String password = passwordEncoder.encode(formPassword);

        boolean passwordsMatch = formPassword.equals(confirmFormPassword);

        if (passwordsMatch) {

            AppUser appUser = new AppUser(
                    firstName,
                    lastName,
                    otherNames,
                    phoneNumber,
                    username,
                    password,
                    role
            );

            AppUser returnedUser = appUserService.createUser(appUser);

            model.addAttribute(
                    "successfulRegistration",
                    String.format(
                            "successfully created admin user with email %s",
                            returnedUser.getUsername()
                    )
            );

        }else {
            model.addAttribute(
                    "unsuccessfulRegistration",
                    "Provided passwords do not match"
            );
        }

        model.addAttribute(
                "adminRegistrationForm",
                new AdminModel()
        );

        return "admin_registration";
    }

}
