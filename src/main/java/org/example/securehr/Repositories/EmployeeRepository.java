package org.example.securehr.Repositories;

import org.example.securehr.Models.Employees.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
