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
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class EmployeeController {

    private  final EmployeeService employeeService;

    @GetMapping("/employee")
    public ResponseEntity getEmployee(){
        return  ResponseEntity.ok().body(employeeService.getEmployee());
    }
    @GetMapping("/getOneEmp/{id}")
    public ResponseEntity  getOneEmployee(@PathVariable("id") String id) {
        return ResponseEntity.ok(employeeService.getOneEmployee(id));
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

    //Request update the employee details
    @PutMapping("/update-emp/{empNo}")
    public  ResponseEntity updateEmployee(@PathVariable String employeeId,@RequestBody Employee employee ){
        employeeService.updateEmployees(employeeId, employee);
        return ResponseEntity.ok("Saved");
    }

    //Delete employee
    @DeleteMapping("/delete/{empNo}")
    public ResponseEntity deleteEmployee(@PathVariable String employeeId){
        employeeService.deleteEmployee(employeeId);
        return  ResponseEntity.ok("delted");
    }
}
@Data
class RoleToEmployeeForm{
    private String employeeId;
    private String roleName;
}
