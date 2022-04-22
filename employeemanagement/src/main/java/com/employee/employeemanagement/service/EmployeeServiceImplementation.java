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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class EmployeeServiceImplementation implements EmployeeService, UserDetailsService {


    private final EmployeeRepo employeeRepo;
    private final RoleRepo roleRepo;

    @Override
    public Employee saveEmployee(Employee employee) {
        log.info("new employee {}to the database", employee.getEmployeeName());
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
    public Employee getEmployee(String employeeId) {
        log.info("fetch  employee{}",employeeId);
        return employeeRepo.findByEmployeeId(employeeId);
    }

    @Override
    public List<Employee> getEmployee() {
        log.info("fetch all employee");
        return employeeRepo.findAll();
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
