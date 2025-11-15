package org.example.securehr.Services;

import org.example.securehr.DTOs.Aunthentication.RegistrationDTO;
import org.example.securehr.Models.Users.Roles;
import org.example.securehr.Models.Users.User;
import org.example.securehr.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public String register (RegistrationDTO registrationDTO){
        User user = new User();

        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword());
        user.setRole(registrationDTO.getRole());

        userRepo.save(user);

        return "Registered Successfully";
    }

    public String login (){
        return "Login Successfully";
    }
}
