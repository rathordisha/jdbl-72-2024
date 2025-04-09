package com.example.L10_JPA_And_Transactions.service;

import com.example.L10_JPA_And_Transactions.entity.Employee;
import com.example.L10_JPA_And_Transactions.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public List<Employee> getAllEmp(){
        return employeeRepo.findAll();
    }

    public Employee createEmp(Employee employee){
        employee=employeeRepo.save(employee);
        return employee;
    }

//    public Employee createEmp(EmployeeDetailRequest employee){
//        employee=employeeRepo.save(employee);
//        return employee;
//    }

    public Employee getAllEmpByEmail(String email) {
        return employeeRepo.findByEmail(email);
    }

}
