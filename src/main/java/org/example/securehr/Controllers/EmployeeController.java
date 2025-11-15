package org.example.securehr.Controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @GetMapping
    public String view(){
        return "Here's a list of employees";
    }

    @PostMapping
    public String create(){
        return "Employee created successfully!";
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
