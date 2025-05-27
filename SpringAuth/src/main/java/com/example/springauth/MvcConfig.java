package com.example.springauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/control_panel").setViewName("control_panel");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/create_user_profile").setViewName("user_profile");
        registry.addViewController("/update_user_profile").setViewName("update_user_profile");
        registry.addViewController("/create_user_role").setViewName("role");
        registry.addViewController("/create_user_privilege").setViewName("privilege");
        registry.addViewController("/role_privilege_mapping").setViewName("role_privilege");
        registry.addViewController("/user_role_mapping").setViewName("user_role");
        registry.addViewController("/change_password").setViewName("change_password");
        registry.addViewController("/upload").setViewName("upload");
    }

}
