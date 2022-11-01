package com.example.ankets.controllers;

import com.example.ankets.model.User;
import com.example.ankets.repositories.UserRepository;
import com.example.ankets.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String getHome(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("admin", userService.isUserAdmin(user));
        return "main";
    }

    @GetMapping("/login")
    public String getLogin(Map<String, Object> model) {
        return "login";
    }

    @GetMapping("/hello")
    public String getHello(Map<String, Object> model) {
        return "hello";
    }


    @GetMapping("/main")
    public String getMain(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("admin", userService.isUserAdmin(user));
        return "main";
    }


    @GetMapping("/registration")
    public String getRegistration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String postRegistration(User user, Model model) {
        userService.addUser(user);
        return "redirect:/login";
    }

    @GetMapping("/registrationadmin")
    public String getRegistrationAdmin(Model model) {
        userService.createAdmin();
        return "login";
    }
}
