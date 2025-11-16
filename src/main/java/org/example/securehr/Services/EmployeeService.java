package org.example.securehr.Services;

import jakarta.persistence.Cacheable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.example.securehr.DTOs.Employee.EmployeeRequestDTO;
import org.example.securehr.DTOs.Employee.EmployeeResponseDTO;
import org.example.securehr.Models.Employees.Employee;
import org.example.securehr.Models.Users.User;
import org.example.securehr.Repositories.EmployeeRepository;
import org.example.securehr.Repositories.UserRepository;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
@Cacheable
public class EmployeeService {
    private final EmployeeRepository employeeRepo;
    private final UserService userService;

    public EmployeeService(EmployeeRepository employeeRepo, UserService userService) {
        this.employeeRepo = employeeRepo;
        this.userService = userService;
    }

    private EmployeeResponseDTO employee(Employee employee, User user){
        return new EmployeeResponseDTO(employee.getName(), employee.getPosition(), employee.getDepartment(), employee.getHireDate(), user.getUsername());
    }

    public EmployeeResponseDTO registerEmployee (HttpServletRequest request, EmployeeRequestDTO employeeDTO) {
        User actor = userService.getActor(request);

        Employee employee = new Employee();

        employee.setName(employeeDTO.getName());
        employee.setPosition(employeeDTO.getPosition());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setHireDate(Date.valueOf(LocalDate.now()));
        employee.setCreatedBy(actor);

        employeeRepo.save(employee);

        return employee(employee,actor);
    }
}
