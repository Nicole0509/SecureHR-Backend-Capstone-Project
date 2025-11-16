package org.example.securehr.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.example.securehr.DTOs.Employee.EmployeeRequestDTO;
import org.example.securehr.DTOs.Employee.EmployeeResponseDTO;
import org.example.securehr.Repositories.EmployeeRepository;
import org.example.securehr.Services.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@Tag(name = "Employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String view(){
        return "Here's a list of employees";
    }

    @Operation(
            description = "This end point creates a new employee.",
            summary = "USER creates a new Employee",
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
    @PostMapping
    public EmployeeResponseDTO registerEmployee (HttpServletRequest request, @RequestBody EmployeeRequestDTO employeeDTO){
        return employeeService.registerEmployee(request, employeeDTO);
    }

    @PatchMapping
    public String update(){
        return "Employee updated successfully!";
    }

    @DeleteMapping
    public String delete(){
        return "Employee deleted successfully!";
    }
}
