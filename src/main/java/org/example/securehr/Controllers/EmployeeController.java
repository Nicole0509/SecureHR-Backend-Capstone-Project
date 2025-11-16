package org.example.securehr.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    public EmployeeResponseDTO registerEmployee (HttpServletRequest request, @Valid @RequestBody EmployeeRequestDTO employeeDTO){
        return employeeService.registerEmployee(request, employeeDTO);
    }

    @Operation(
            description = "This endpoint shows a single employee to both the USER and ADMIN.",
            summary = "Get An Employee By Id",
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
    @GetMapping("/{id}")
    public EmployeeResponseDTO getEmployeeById (HttpServletRequest request, @PathVariable Long id) {
        return employeeService.getEmployeeById(request,id);
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
