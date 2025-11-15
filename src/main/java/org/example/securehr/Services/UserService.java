package org.example.securehr.Services;

import org.example.securehr.DTOs.Aunthentication.RegistrationDTO;
import org.example.securehr.Models.Users.Roles;
import org.example.securehr.Models.Users.User;
import org.example.securehr.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String register (RegistrationDTO registrationDTO){
        //Check if username or email already exists
        if (userRepo.existsByUsername(registrationDTO.getUsername()) || userRepo.existsByEmail(registrationDTO.getEmail())){
            System.out.println("Username or Email is already in use");
            throw new RuntimeException("Username or Email is already in use");
        }

        //Create a new user
        User user = new User();

        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(encoder.encode(registrationDTO.getPassword()));
        user.setRole(Roles.USER);

        userRepo.save(user);

        return "Registered Successfully";
    }

    public String login (){
        return "Login Successfully";
    }
}
