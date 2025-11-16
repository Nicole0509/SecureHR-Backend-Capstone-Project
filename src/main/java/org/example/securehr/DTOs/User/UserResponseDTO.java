package org.example.securehr.DTOs.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.securehr.Models.Employees.Employee;
import org.example.securehr.Models.Users.Roles;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String username;
    private String email;
    private Roles role;
//    private List<Employee> employees = new ArrayList<>();
}
