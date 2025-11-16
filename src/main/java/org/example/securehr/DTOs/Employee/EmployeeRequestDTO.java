package org.example.securehr.DTOs.Employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmployeeRequestDTO {
    @NotNull(message = "name is mandatory")
    @NotBlank(message = "name is mandatory")
    private String name;

    @NotNull(message = "position is mandatory")
    @NotBlank(message = "position is mandatory")
    private String position;

    @NotNull(message = "department is mandatory")
    @NotBlank(message = "department is mandatory")
    private String department;
}
