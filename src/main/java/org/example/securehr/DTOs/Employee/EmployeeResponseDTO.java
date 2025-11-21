package org.example.securehr.DTOs.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.securehr.Models.Users.User;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {
    private String name;
    private String position;
    private String department;
    private Date hireDate;
    private String createdBy;
}
