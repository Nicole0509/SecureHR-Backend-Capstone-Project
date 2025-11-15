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

    @PostMapping("/auth/login")
    public String login(@Valid @RequestBody LoginDTO loginDTO){
        return userService.verify(loginDTO);
    }

    @GetMapping("/hey")
    public String somethingprivate(){
        return "you are now authenticated :)";
    }
}
