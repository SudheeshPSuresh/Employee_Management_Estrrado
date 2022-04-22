package com.employee.managament.empmemt.service;


import com.employee.managament.empmemt.dto.Designation;
import com.employee.managament.empmemt.dto.Employee;
import com.employee.managament.empmemt.dto.Role;
import com.employee.managament.empmemt.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class EmpService {

    @Autowired
    protected EmpRepository empRepository;

    //testing data
    private List<Employee> employees = Arrays.asList(
            new Employee(1, "ES123", "sudheesh", Designation.SystemEngineer, Role.Admin),
            new Employee(2,"ES124", "Rahul", Designation.SoftwareEngineer,Role.User )
    );
    //add the datas to database
    public List<Employee> createEmployee(){
        return empRepository.saveAll(employees);
    }
    //get all employees
    public List<Employee> getAllEmployees(){
        return empRepository.findAll();
    }
    // get one employee based on id
    public Optional<Employee> getOneEmployee(Integer id){
        return empRepository.findById(id);
    }
}
