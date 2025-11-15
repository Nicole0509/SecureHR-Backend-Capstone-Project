package org.example.securehr.Services;

import org.example.securehr.DTOs.Aunthentication.LoginDTO;
import org.example.securehr.DTOs.Aunthentication.RegistrationDTO;
import org.example.securehr.Exceptions.ResourceAlreadyExists;
import org.example.securehr.Models.Users.Roles;
import org.example.securehr.Models.Users.User;
import org.example.securehr.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String register (RegistrationDTO registrationDTO){
        //Check if username or email already exists
        if (userRepo.existsByUsername(registrationDTO.getUsername())){
            throw new ResourceAlreadyExists("The username : " + registrationDTO.getUsername() + " already exists");
        } else if ( userRepo.existsByEmail(registrationDTO.getEmail())) {
            throw new ResourceAlreadyExists("The email : " + registrationDTO.getEmail() + " already exists");
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

    public String verify (LoginDTO loginDTO){
        Authentication  authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        if (authentication.isAuthenticated()){
            return jwtService.generateToken(loginDTO.getUsername());
        }

        return "Unauthenticated";
    }
}
