package com.example.L10_JPA_And_Transactions.repo;

import com.example.L10_JPA_And_Transactions.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {


    Employee findByEmail(String email);
}
