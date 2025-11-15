package org.example.securehr.Controllers;

import jakarta.validation.Valid;
import org.example.securehr.DTOs.Aunthentication.RegistrationDTO;
import org.example.securehr.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth/register")
    public String register(@Valid @RequestBody RegistrationDTO registrationDTO){
        return userService.register(registrationDTO);
    }

    @GetMapping("/auth/login")
    public String login(){
        return userService.login();
    }

    @GetMapping("/hey")
    public String somethingprivate(){
        return "you are now authenticated";
    }
}
