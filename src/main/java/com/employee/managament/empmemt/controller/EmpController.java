package com.employee.managament.empmemt.controller;

import com.employee.managament.empmemt.dto.Employee;
import com.employee.managament.empmemt.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class EmpController {

    @Autowired
    protected EmpService empService;

    //request for adding the data to the database
    @PostConstruct
    public List<Employee> createEmployee(){
        return empService.createEmployee();
    }

    //Request get all  employees
    @GetMapping("/getemp")
    public  List<Employee> getEmployees(){
        return  empService.getAllEmployees();
    }

    @GetMapping("/getOneEmp/{id}")
    public Optional<Employee> getOneEmployee(@PathVariable("id") Integer id ){
        return empService.getOneEmployee(id);
    }
}
