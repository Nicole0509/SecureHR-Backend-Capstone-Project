package org.example.securehr.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/auth/register")
    public String register(){
        return "you can now register";
    }

    @GetMapping("/auth/login")
    public String login(){
        return "you can now login";
    }

    @GetMapping("/hey")
    public String somethingprivate(){
        return "you are bow authenticated";
    }
}
