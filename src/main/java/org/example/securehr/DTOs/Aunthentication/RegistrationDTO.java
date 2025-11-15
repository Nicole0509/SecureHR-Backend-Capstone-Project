package org.example.securehr.DTOs.Aunthentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.securehr.Models.Users.Roles;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    private String username;
    private String email;
    private String password;
    private Roles role;
}