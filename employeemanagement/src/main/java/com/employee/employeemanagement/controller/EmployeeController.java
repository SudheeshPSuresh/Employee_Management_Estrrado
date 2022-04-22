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
    //Request get all  employees
    @GetMapping("/employee/getAllEmployee")
    public ResponseEntity getEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    // Request to get one employee
    @GetMapping("/employee/getOneEmp/{id}")
    public ResponseEntity  getOneEmployee(@PathVariable("id") String id) {
        return ResponseEntity.ok(employeeService.getOneEmployee(id));
    }

    //Request to add employee to the database
    @PostMapping("/employee/addEmployee")
    public ResponseEntity saveEmployees(@RequestBody Employee employees) {
        employeeService.saveEmployeeDetails((List<Employee>) employees);
        return ResponseEntity.ok("Saved");
    }

    //Request update the employee details
    @PutMapping("/employee/update-emp/{empNo}")
    ResponseEntity updateEmployee(@PathVariable String empNo,@RequestBody Employee employee ){
        employeeService.updateEmployees(empNo,employee);
        return ResponseEntity.ok("Saved");
    }

    //Delete employee
    @DeleteMapping("/employee/delete/{empNo}")
    public ResponseEntity deleteEmployee(@PathVariable String empNo){
        employeeService.deleteEmployee(empNo);
        return  ResponseEntity.ok("delted");
    }
}
@Data
class RoleToEmployeeForm{
    private String employeeId;
    private String roleName;
}
