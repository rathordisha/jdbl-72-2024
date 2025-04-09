package com.example.L10_JPA_And_Transactions.controller;

import com.example.L10_JPA_And_Transactions.entity.Employee;
import com.example.L10_JPA_And_Transactions.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//http://localhost:8084/jpa-app/all

@RestController
@RequestMapping("/jpa-app")
public class EmoloyeeController {

    @Autowired
    private EmpService appService;

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployee(){
        List<Employee> employees = appService.getAllEmp();
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/post-new-emp")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        employee= appService.createEmp(employee);
        return ResponseEntity.ok(employee);
    }

//    @PostMapping("/post-new-emp")
//    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDetailRequest employee){
//        employee= appService.createEmp(employee);
//        return ResponseEntity.ok(employee);
//    }

    //to get employee by email
    @GetMapping("/allEmpByEmail")
    public ResponseEntity<Employee> getAllEmployeeByEmail(@RequestParam String email ){
        Employee employee = (Employee) appService.getAllEmpByEmail(email);
        return ResponseEntity.ok(employee);
    }

}//end of controller
