package org.example.securehr.Services;

import jakarta.persistence.Cacheable;
import jakarta.transaction.Transactional;
import org.example.securehr.DTOs.Employee.EmployeeRequestDTO;
import org.example.securehr.Repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Cacheable
public class EmployeeService {
    private final EmployeeRepository employeeRepo;

    public EmployeeService(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public String registerEmployee (EmployeeRequestDTO employeeDTO) {
        return "Employee registered successfully";
    }
}
