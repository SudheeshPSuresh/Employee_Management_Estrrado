package com.employee.managament.empmemt.service;


import com.employee.managament.empmemt.dto.Designation;
import com.employee.managament.empmemt.dto.Employee;
import com.employee.managament.empmemt.dto.JwtRequest;
import com.employee.managament.empmemt.dto.Role;
import com.employee.managament.empmemt.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpService {

    @Autowired
    protected EmpRepository empRepository;

    //testing data
    private List<Employee> employees = Arrays.asList(
            new Employee(1, "ES123", "sudheesh", Designation.SystemEngineer, Role.Admin,"abc@123"),
            new Employee(2, "ES124", "Rahul", Designation.SoftwareEngineer, Role.User, "abc@123")
    );

    //add the datas to database
    public List<Employee> createEmployee() {
        return empRepository.saveAll(employees);
    }

    //get all employees
    public List<Employee> getAllEmployees() {
        return empRepository.findAll();
    }

    // get one employee based on id
    public Optional<Employee> getOneEmployee(Integer id) {
        return empRepository.findById(id);
    }

    //Save employees
    public List<Employee> saveEmployees(List<Employee> employees) {
        return empRepository.saveAll(employees);
    }

    //update employee using employee number
    public ResponseEntity updateEmployees(String empNo, Employee employee) {
        Employee tempEmployee1 = empRepository.getEmployeeByEmpNo(empNo);
        tempEmployee1.setEmpName(employee.getEmpName());
        tempEmployee1.setDesignation(employee.getDesignation());
        tempEmployee1.setRole(employee.getRole());
        tempEmployee1.setPassword(employee.getPassword());
        return ResponseEntity.ok(empRepository.save(tempEmployee1));
    }

    //Delete employees
    public ResponseEntity deleteEmployee(String empNo) {
        Employee tempEmployee1 = empRepository.getEmployeeByEmpNo(empNo);
        empRepository.delete(tempEmployee1);
        return ResponseEntity.ok(tempEmployee1 + "Deleted");
    }

    public boolean isValidUser(JwtRequest request)
    {
       return empRepository.findAll().stream().filter(employee -> {
            return employee.getEmpName().equals(request.getUsername()) && employee.getPassword().equals(request.getPassword());
        }).collect(Collectors.toList()).size()>0;
    }

}
