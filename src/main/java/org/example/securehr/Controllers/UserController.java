package org.example.securehr.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.securehr.DTOs.Aunthentication.LoginDTO;
import org.example.securehr.DTOs.Aunthentication.RegistrationDTO;
import org.example.securehr.DTOs.User.UserInputDTO;
import org.example.securehr.DTOs.User.UserResponseDTO;
import org.example.securehr.Services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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

    @Operation(
            description = "This endpoint shows a single user, the corresponding email and the role." +
                    "P.S.: An ADMIN can access all kinds of ids but a USER can only access their own id alone.",
            summary = "Get A User By Id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
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
    @GetMapping("/user/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id, HttpServletRequest request){
        return userService.getUserById(id, request);
    }

    @PatchMapping("/user/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserInputDTO userInputDTO, HttpServletRequest request){
        return userService.updateUser(id, userInputDTO, request);
    }

    @DeleteMapping("/user")
    public String delete(){
        return "User profile deleted :)";
    }
}
