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
import java.util.Optional;

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
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public Optional<Employee> getOneEmployee(String id) {
        return Optional.ofNullable(employeeRepo.findByEmployeeId(id));
    }

    @Override
    public void saveEmployeeDetails(List<Employee> employees) {

    }

    @Override
    public Employee updateEmployees(String empNo, Employee employee) {
        Employee tempEmployee = employeeRepo.findByEmployeeId(empNo);
        tempEmployee.setEmployeeName(employee.getEmployeeName());
        tempEmployee.setDesignation(employee.getDesignation());
        tempEmployee.setRole(employee.getRole());
        tempEmployee.setPassword(employee.getPassword());
        return employeeRepo.save(tempEmployee);
    }

    @Override
    public void deleteEmployee(String empNo) {
        Employee tempEmployee = employeeRepo.findByEmployeeId(empNo);
        employeeRepo.deleteByEmployeeId(empNo);
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
