package com.employee.employeemanagement.service;

import com.employee.employeemanagement.entity.Employee;
import com.employee.employeemanagement.entity.Role;
import com.employee.employeemanagement.repo.EmployeeRepo;
import com.employee.employeemanagement.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class EmployeeServiceImplementation implements EmployeeService, UserDetailsService {


    private final EmployeeRepo employeeRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Employee saveEmployee(Employee employee) {
        log.info("new employee {}to the database", employee.getEmployeeName());
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepo.save(employee);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role {} to the database",role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToEmployee(String employeeId, String roleName) {
        log.info("Adding new role {} to Employee {} ",roleName,employeeId);
        Employee employee = employeeRepo.findByEmployeeId(employeeId);
        Role role= roleRepo.findByName(roleName);
        employee.getRole().add(role);
    }

    @Override
    public Employee getEmployee() {
      return (Employee) employeeRepo.findAll();
    }

    @Override
    public Employee updateEmployees(String employeeId, Employee employee) {
        Employee tempEmployees = employeeRepo.findByEmployeeId(employeeId);
        tempEmployees.setEmployeeName(employee.getEmployeeName());
        tempEmployees.setPassword(employee.getPassword());
        tempEmployees.setDesignation(employee.getDesignation());
        tempEmployees.setRole(employee.getRole());
        return  employeeRepo.save(tempEmployees);
    }

    @Override
    public void deleteEmployee(String employeeId) {

        Employee tempEmployees = employeeRepo.findByEmployeeId(employeeId);
        employeeRepo.deleteById(tempEmployees);

    }

    @Override
    public Employee getOneEmployee(String employeeId ) {
        log.info("fetch  employee{}",employeeId);
        return employeeRepo.findByEmployeeId(employeeId);
    }

    @Override
    public UserDetails loadUserByUsername(String employeeName) throws UsernameNotFoundException {
        Employee employee = employeeRepo.findByEmployeeName(employeeName);
        if (employee == null){
            log.error("Employee details not found");
            throw  new UsernameNotFoundException("Employee not found");
        }else {
            log.info("Employee found in the database: {}",employeeName);
        }
        Collection<SimpleGrantedAuthority> authorities =new ArrayList<>();
        employee.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(employee.getEmployeeName(),employee.getPassword(),authorities);
    }
}
