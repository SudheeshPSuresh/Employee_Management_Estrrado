package com.employee.employeemanagement.service;


import com.employee.employeemanagement.entity.Employee;
import com.employee.employeemanagement.entity.Role;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    Role saveRole(Role role);
    void addRoleToEmployee (String employeeId,String roleName);
    Employee getEmployee();

    Employee updateEmployees(String employeeId, Employee employee);

    void deleteEmployee(String employeeId);

    Employee getOneEmployee(String employeeId);
}
