package com.employee.employeemanagement.service;


import com.employee.employeemanagement.entity.Employee;
import com.employee.employeemanagement.entity.Role;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    Role saveRole(Role role);
    void addRoleToEmployee (String employeeId,String roleName);
    public List<Employee> getAllEmployees();
    public Optional<Employee> getOneEmployee(String id);

    void saveEmployeeDetails(List<Employee> employees);

    public Employee updateEmployees(String empNo, Employee employee) ;

    public void deleteEmployee(String empNo);
}
