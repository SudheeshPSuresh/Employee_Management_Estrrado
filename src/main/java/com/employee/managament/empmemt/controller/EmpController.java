package com.employee.managament.empmemt.controller;

import com.employee.managament.empmemt.config.JwtTokenUtil;
import com.employee.managament.empmemt.dto.Employee;
import com.employee.managament.empmemt.dto.JwtRequest;
import com.employee.managament.empmemt.dto.JwtResponse;
import com.employee.managament.empmemt.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api/")
@Slf4j
public class EmpController {

    @Autowired
    protected EmpService empService;
    @Autowired AuthenticationManager authManager;
    @Autowired JwtTokenUtil jwtUtil;



    @GetMapping("/emp-login")
    public ResponseEntity login(@RequestParam(value = "username") String userName,@RequestParam(value = "password") String password )
    {
        JwtRequest request = new JwtRequest(userName,password);
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword())
        );

        Employee emp = (Employee) authentication.getPrincipal();
        String accessToken = jwtUtil.generateAccessToken(emp);
        JwtResponse response = new JwtResponse(emp.getEmpName(),emp.getRole().toString(), accessToken);

        return ResponseEntity.ok().body(response);
    }


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

    //Request update the employee details
    @PutMapping("/update-emp/{empNo}")
    ResponseEntity updateEmployee(@PathVariable String empNo,@RequestBody Employee employee ){
        empService.updateEmployees(empNo,employee);
        return ResponseEntity.ok("Saved");
    }

    //Delete employee
    @DeleteMapping("/delete/{empNo}")
    public ResponseEntity deleteEmployee(@PathVariable String empNo){
        empService.deleteEmployee(empNo);
        return  ResponseEntity.ok("delted");
    }

}
