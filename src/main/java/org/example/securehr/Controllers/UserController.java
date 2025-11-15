package org.example.securehr.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.example.securehr.DTOs.Aunthentication.LoginDTO;
import org.example.securehr.DTOs.Aunthentication.RegistrationDTO;
import org.example.securehr.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            description = "This end point creates a new user whose email and username must be unique otherwise an error is thrown.",
            summary = "User Sign Up",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Conflict",
                            responseCode = "409"
                    ),
            }
    )
    @PostMapping("/auth/register")
    public String register(@Valid @RequestBody RegistrationDTO registrationDTO){
        return userService.register(registrationDTO);
    }

    @Operation(
            description = "This end point logs in an existing user through username and password, then generates a token.",
            summary = "User Sign In",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Conflict",
                            responseCode = "409"
                    ),
            }
    )
    @PostMapping("/auth/login")
    public String login(@Valid @RequestBody LoginDTO loginDTO){
        return userService.verify(loginDTO);
    }

    @GetMapping("/user")
    public String view(){
        return "A list of all users :)";
    }

    @PatchMapping("/user")
    public String update(){
        return "User profile updated :)";
    }

    @DeleteMapping("/user")
    public String delete(){
        return "User profile deleted :)";
    }
}
