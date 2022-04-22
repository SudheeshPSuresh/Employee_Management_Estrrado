package com.employee.managament.empmemt.controller;

import com.employee.managament.empmemt.dto.Employee;
import com.employee.managament.empmemt.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class EmpController {

    @Autowired
    protected EmpService empService;

    //request for adding the data to the database
    @PostConstruct
    public List<Employee> createEmployee() {
        return empService.createEmployee();
    }

    //Request get all  employees
    @GetMapping("/getemp")
    public ResponseEntity getEmployees() {
        return ResponseEntity.ok(empService.getAllEmployees());
    }

    // Request to get one employee
    @GetMapping("/getOneEmp/{id}")
    public ResponseEntity  getOneEmployee(@PathVariable("id") Integer id) {
         return ResponseEntity.ok(empService.getOneEmployee(id));
    }

    //Request to add employee to the database
    @PostMapping("/addEmployee")
    public ResponseEntity saveEmployees(@RequestBody List<Employee> employees) {
        empService.saveEmployees(employees);
        return ResponseEntity.ok("Saved");
    }

}
