package org.example.securehr.Services;

import jakarta.servlet.http.HttpServletRequest;
import org.example.securehr.DTOs.Aunthentication.LoginDTO;
import org.example.securehr.DTOs.Aunthentication.RegistrationDTO;
import org.example.securehr.DTOs.User.UserInputDTO;
import org.example.securehr.DTOs.User.UserResponseDTO;
import org.example.securehr.Exceptions.ResourceAlreadyExists;
import org.example.securehr.Exceptions.ResourceNotFound;
import org.example.securehr.Models.Users.Roles;
import org.example.securehr.Models.Users.User;
import org.example.securehr.Repositories.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepo;

    private final AuthenticationManager authManager;

    private final JWTService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(UserRepository userRepo, AuthenticationManager authManager, JWTService jwtService) {
        this.userRepo = userRepo;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    private UserResponseDTO userResponse (User user){
        return new UserResponseDTO(user.getUsername(), user.getEmail(), user.getRole());
    }

    private void grantAccessByRole(Long id, HttpServletRequest request){
        String username = jwtService.getUserNameFromRequest(request); //extract username from token

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFound("User not found with username: " + username));

        if(user.getRole().equals(Roles.USER)) { //restrict USER role to access other people's profiles
            grantUserAccess(user, id);
        }
    }

    private void grantUserAccess(User user, Long id){
        if(!user.getId().equals(id)) {
            throw new AccessDeniedException("You are not allowed to access this resource");
        }
    }

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

    public UserResponseDTO getUserById(Long id, HttpServletRequest request){
        grantAccessByRole(id, request); //check role to grant access accordingly

        return userRepo.findById(id)
                .map(this::userResponse)
                .orElseThrow(() -> new ResourceNotFound("User with id '" + id + "' was not found!"));
    }

    public UserResponseDTO updateUser(Long id, UserInputDTO userInputDTO, HttpServletRequest request){
        //Logged-in user
        String username = jwtService.getUserNameFromRequest(request);

        User actor = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFound("Logged-in user not found"));

        return userRepo.findById(id)
                .map(target -> {

                    if(actor.getRole().equals(Roles.USER)) { //if the Jwt role is USER

                        if(!actor.getId().equals(id)) {
                            throw new AccessDeniedException("You cannot update another user's account");
                        }

                        //USER can update everything but their role
                        target.setUsername(userInputDTO.getUsername());
                        target.setEmail(userInputDTO.getEmail());
                        target.setPassword(encoder.encode(userInputDTO.getPassword()));

                    } else if (actor.getRole().equals(Roles.ADMIN)) { //if the Jwt role is ROLE

                        if(actor.getId().equals(id)) {

                            // Admin updating their own credentials except role
                            target.setUsername(userInputDTO.getUsername());
                            target.setEmail(userInputDTO.getEmail());
                            target.setPassword(encoder.encode(userInputDTO.getPassword()));
                        } else {

                            // Admin updating someone else's role
                            target.setRole(userInputDTO.getRole());
                        }
                    }

                    userRepo.save(target);

                    return userResponse(target);
                }).orElseThrow(() -> new ResourceNotFound("User with id '" + id + "' was not found!"));
    }
}