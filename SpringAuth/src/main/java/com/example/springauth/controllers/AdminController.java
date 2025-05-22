package com.example.springauth.controllers;

import com.example.springauth.authentication.AuthenticationUtility;
import com.example.springauth.dialects.postgres.ConnectionSettings;
import com.example.springauth.dialects.postgres.PostgresUpdate;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.dialects.postgres.SchemaSettings;
import com.example.springauth.models.app.AdminModel;
import com.example.springauth.models.jpa.AppUser;
import com.example.springauth.models.json.PwdChangeJSONModel;
import com.example.springauth.schemas.Schema;
import com.example.springauth.schemas.SchemaNameGiver;
import com.example.springauth.services.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class AdminController {


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppUserService appUserService;


    String sqlDialect = "POSTGRES";
    QueryExecutor queryExecutor = ConnectionSettings.getConnectionSettings().getQueryExecutor();
    Schema userManagementSchema = SchemaSettings.getInstance().getSchema(SchemaNameGiver.getUserManagementSchemaName());


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

    @GetMapping("/change_password")
    public String changePassword(Model model) {
        String currentUsername = AuthenticationUtility.getCurrentUsername();
        model.addAttribute("current_username", currentUsername);
        return "change_password";
    }

    @PostMapping(value ="/change_password", consumes = "application/json")
    public String changePassword(
            @RequestBody
            String passwordChangeModelJSON,
            Model model
    ) {

        String returnPage = "change_password";
        ObjectMapper objectMapper = new ObjectMapper();

        try{

            PwdChangeJSONModel pwdChangeJSONModel = objectMapper.readValue(passwordChangeModelJSON, PwdChangeJSONModel.class);
            String providedUsername = pwdChangeJSONModel.getUsername();
            String providedCurrentPassword = pwdChangeJSONModel.getCurrentPassword();
            String providedNewPassword = pwdChangeJSONModel.getNewPassword();
            String providedConfirmedNewPassword = pwdChangeJSONModel.getConfirmNewPassword();

            if (
                 (providedUsername == null || providedCurrentPassword == null || providedNewPassword == null) &&
                 (providedUsername.isEmpty() || providedCurrentPassword.isEmpty() || providedNewPassword.isEmpty())
            )
            {
                model.addAttribute(
                        "unsuccessfulChangePassword",
                        "Missing required password information"
                );
                return returnPage;
            }
            System.out.println("Initiating password change for user " + providedUsername);
            String providedOldEncodedPassword = passwordEncoder.encode(providedCurrentPassword);
            String newProvidedEncodedPassword = passwordEncoder.encode(providedNewPassword);
            //boolean doPasswordsMatch = AuthenticationUtility.doPasswordsMatch(providedOldEncodedPassword);
            //System.out.println("Old passwords match: " + doPasswordsMatch);
            if (providedNewPassword.equals(providedConfirmedNewPassword)) {
                appUserService.updateUserPassword(providedUsername, newProvidedEncodedPassword);
                // update the dynamically generated table
                String role = AuthenticationUtility.getUserRole();
                System.out.println("The user role is " + role);
                // create a utility class for user role names.
                if (role != null && !role.equalsIgnoreCase("ROLE_SUPER_ADMIN")) {
                    this.updateDynamicallyGeneratedUserTable(providedUsername, newProvidedEncodedPassword);
                }
            }

        }catch (Exception e) {
            // log error
            System.out.println("Exception while changing password: " + e.getMessage());
        }
        return returnPage;
    }

    private void updateDynamicallyGeneratedUserTable(String username, String password) {
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresUpdate postgresUpdate = new PostgresUpdate(queryExecutor, userManagementSchema);
            postgresUpdate.updatePassword(username, password);
        }
    }

}
