package com.employee.managament.empmemt.repository;

import com.employee.managament.empmemt.dto.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpRepository extends JpaRepository<Employee,Integer> {

        Employee getEmployeeByEmpNo(String empNo);
        Employee getEmployeeByEmpName(String empName);
}
