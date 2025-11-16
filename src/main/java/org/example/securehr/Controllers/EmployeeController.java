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
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@Tag(name = "Employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
            description = "This endpoint shows a list of all employees to both the USER and ADMIN.",
            summary = "Get A list of all employees",
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
            }
    )
    @GetMapping
    public Page<EmployeeResponseDTO> getAllEmployees(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "createdBy") String sortBy,
                                                     @RequestParam(defaultValue = "desc") String direction){
        return employeeService.getAllEmployees(page, size, sortBy, direction);
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
    public EmployeeResponseDTO getEmployeeById (@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @Operation(
            description = "This endpoint allows a USER to patch An Employee's  credentials. The rest of the data is conserved as is in the DB.",
            summary = "USER Patches An Employee's credentials",
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
    @PatchMapping("/{id}")
    public EmployeeResponseDTO updateEmployee (HttpServletRequest request, @PathVariable Long id, @Valid @RequestBody EmployeeRequestDTO employeeDTO) {
        return  employeeService.updateEmployee(request, id, employeeDTO);
    }

    @Operation(
            description = "This endpoint allows ADMIN to delete an employees credentials. USER can not access it.",
            summary = "ADMIN Deletes An Employee's Credentials",
            responses = {
                    @ApiResponse(
                            description = "Success/No Content",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400"
                    ),
            }
    )
    @DeleteMapping("/{id}")
    public String deleteEmployee (HttpServletRequest request, @PathVariable Long id) {
        return employeeService.deleteEmployee(request, id);
    }
}
