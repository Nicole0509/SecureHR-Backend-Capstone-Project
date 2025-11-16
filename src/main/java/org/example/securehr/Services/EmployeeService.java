package org.example.securehr.Services;

import jakarta.persistence.Cacheable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.example.securehr.DTOs.Employee.EmployeeRequestDTO;
import org.example.securehr.DTOs.Employee.EmployeeResponseDTO;
import org.example.securehr.Exceptions.ResourceNotFound;
import org.example.securehr.Models.Employees.Employee;
import org.example.securehr.Models.Users.User;
import org.example.securehr.Repositories.EmployeeRepository;
import org.example.securehr.Repositories.UserRepository;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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

    private EmployeeResponseDTO employee(Employee employee){
        return new EmployeeResponseDTO(employee.getName(), employee.getPosition(), employee.getDepartment(), employee.getHireDate(), employee.getCreatedBy().getUsername());
    }

    public EmployeeResponseDTO registerEmployee (HttpServletRequest request, EmployeeRequestDTO employeeDTO) {

        Employee employee = new Employee();

        employee.setName(employeeDTO.getName());
        employee.setPosition(employeeDTO.getPosition());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setHireDate(Date.valueOf(LocalDate.now()));
        employee.setCreatedBy(userService.getActor(request));

        employeeRepo.save(employee);

        return employee(employee);
    }

    public EmployeeResponseDTO getEmployeeById (Long id) {
        return employeeRepo.findById(id)
                .map(this::employee)
                .orElseThrow(() -> new ResourceNotFound("Employee with id: " + id + " not found"));
    }

    public Page<EmployeeResponseDTO> getAllEmployees( int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> postsPage = employeeRepo.findAll(pageable);

        return postsPage.map(this::employee);
    }

    public EmployeeResponseDTO updateEmployee (HttpServletRequest request, Long id, EmployeeRequestDTO employeeDTO) {
        return employeeRepo.findById(id)
                .map(employee ->  {
                    employee.setName(employeeDTO.getName());
                    employee.setPosition(employeeDTO.getPosition());
                    employee.setDepartment(employeeDTO.getDepartment());

                    employeeRepo.save(employee);

                    return employee(employee);
                }).orElseThrow(() -> new ResourceNotFound("Employee with id: " + id + " was not found"));
    }

    public String deleteEmployee (HttpServletRequest request, Long id) {
        if(!employeeRepo.existsById(id)){
            throw new ResourceNotFound("User with id '" + id + "' was not found");
        }

        employeeRepo.deleteById(id);

        return "Employee with id: " + id + " was deleted successfully";
    }
}
