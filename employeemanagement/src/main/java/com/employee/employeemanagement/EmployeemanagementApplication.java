package com.employee.employeemanagement;

import com.employee.employeemanagement.entity.Employee;
import com.employee.employeemanagement.entity.Role;
import com.employee.employeemanagement.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class EmployeemanagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeemanagementApplication.class, args);
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner run(EmployeeService employeeService){
		return args -> {
			employeeService.saveRole(new Role(null,"ADMIN"));
			employeeService.saveRole(new Role(null,"DEVELOPER"));

			employeeService.saveEmployee(new Employee(null,"ES123","Sudheesh PS","SoftwareEngineer","asd@123",new ArrayList<>()));
			employeeService.saveEmployee(new Employee(null,"ES124","Rahul","SystemAdmin","asd@123",new ArrayList<>()));

			employeeService.addRoleToEmployee("ES123","ADMIN");
			employeeService.addRoleToEmployee("ES124","DEVELOPER");
		};
	}
}


