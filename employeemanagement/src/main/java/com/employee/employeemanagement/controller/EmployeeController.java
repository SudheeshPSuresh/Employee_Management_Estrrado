package com.employee.employeemanagement.controller;

import com.employee.employeemanagement.entity.Employee;
import com.employee.employeemanagement.entity.Role;
import com.employee.employeemanagement.service.EmployeeService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class EmployeeController {

    private  final EmployeeService employeeService;

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getEmployee(){
        return  ResponseEntity.ok().body(employeeService.getEmployee());
    }

    @PostMapping("/employee/save")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employees){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/employee/save").toUriString());
        return ResponseEntity.created(uri).body(employeeService.saveEmployee(employees));
    }
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(employeeService.saveRole(role));
    }

    @PostMapping("/role/employee-role")
    public ResponseEntity<?> addRoleToEmployee(@RequestBody RoleToEmployeeForm form){
        employeeService.addRoleToEmployee(form.getEmployeeId(),form.getRoleName());
        return ResponseEntity.ok().build();
    }
}
@Data
class RoleToEmployeeForm{
    private String employeeId;
    private String roleName;
}
