package org.example.securehr.Controllers;

import org.example.securehr.DTOs.Employee.EmployeeRequestDTO;
import org.example.securehr.Repositories.EmployeeRepository;
import org.example.securehr.Services.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String view(){
        return "Here's a list of employees";
    }

    @PostMapping
    public String registerEmployee (EmployeeRequestDTO employeeDTO){
        return employeeService.registerEmployee(employeeDTO);
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
