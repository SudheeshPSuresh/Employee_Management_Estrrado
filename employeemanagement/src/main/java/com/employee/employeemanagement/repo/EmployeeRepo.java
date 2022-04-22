package com.employee.employeemanagement.repo;


import com.employee.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    Employee findByEmployeeId(String EmployeeId);

    Employee findByEmployeeName(String employeeName);
}
