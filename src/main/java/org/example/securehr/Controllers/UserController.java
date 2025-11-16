package org.example.securehr.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.securehr.DTOs.Aunthentication.LoginDTO;
import org.example.securehr.DTOs.Aunthentication.RegistrationDTO;
import org.example.securehr.DTOs.User.UserRequestDTO;
import org.example.securehr.DTOs.User.UserResponseDTO;
import org.example.securehr.Services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Users")
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
    public UserResponseDTO register(@Valid @RequestBody RegistrationDTO registrationDTO){
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

    @Operation(
            description = "This endpoint allows to patch a User's credentials or for the admin to assign roles to other user's except themself. The rest of the data is conserved as is in the DB.",
            summary = "Patches A User's credentials",
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
    @PatchMapping("/user/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO, HttpServletRequest request){
        return userService.updateUser(id, userRequestDTO, request);
    }

    @Operation(
            description = "This endpoint allows ADMIN role to view all users. This method also renders everything in form of pages and applies some sorting as per the user requirements.",
            summary = "Get All users with their associated employees",
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
            }
    )
    @GetMapping("/user")
    public Page<UserResponseDTO> getAllUsers(HttpServletRequest request,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "username") String sortBy,
                                             @RequestParam(defaultValue = "desc") String direction){
        return userService.getAllUsers(request, page, size, sortBy, direction);
    }

    @Operation(
            description = "This endpoint allows a USER to delete one's account or the ADMIN to delete any account of choice.",
            summary = "Deletes A user's Credentials",
            responses = {
                    @ApiResponse(
                            description = "Success/No Content",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400"
                    ),
            }
    )
    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest request){
        return userService.delete(id,request);
    }
}
